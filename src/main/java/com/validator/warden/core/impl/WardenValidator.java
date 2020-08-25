package com.validator.warden.core.impl;


import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.validator.warden.annotation.Check;
import com.validator.warden.annotation.Matcher;
import com.validator.warden.annotation.Matchers;
import com.validator.warden.core.delegate.CheckDelegate;
import com.validator.warden.core.Validator;
import com.validator.warden.core.domain.CheckResult;
import com.validator.warden.core.domain.CheckResultMsg;
import com.validator.warden.core.domain.WdConstant;
import com.validator.warden.core.domain.WdContext;
import com.validator.warden.match.manager.MatchManager;
import com.validator.warden.util.ClassUtil;
import com.validator.warden.util.CollectionUtil;
import com.validator.warden.util.ObjectUtil;

/**
 * 校验实现类
 * @author DandyLuo
 * @since 1.0.0
 */
public class WardenValidator implements Validator {
    /**
     * 对象属性值白名单：key为group名字，value为属性的匹配器
     */
    private static Map<String, MatchManager> whiteGroupMap;
    /**
     * 对象属性值黑名单：key为group名字，value为属性的匹配器
     */
    private static Map<String, MatchManager> blackGroupMap;
    /**
     * 对象属性核查映射：key为规范化的类名，value为属性名
     */
    private static Map<String, Set<String>> objectFieldCheckMap;
    private static CheckDelegate delegate;
    /**
     * 核查上下文
     */
    private static WdContext context;

    static {
        init();
    }

    private static void init() {
        whiteGroupMap = new ConcurrentHashMap<>(2);
        blackGroupMap = new ConcurrentHashMap<>(2);
        objectFieldCheckMap = new ConcurrentHashMap<>(16);
        context = new WdContext();
        delegate = new CheckDelegate(context);
    }

    @Override
    public CheckResult validate(final Object object){
        if (delegate.isEmpty(object)) {
            return CheckResult.ok();
        }

        // 待核查类型不核查，直接返回核查成功
        if (ClassUtil.isCheckedType(object.getClass())) {
            return CheckResult.ok();
        } else {
            return new CheckResult()
                    .setResult(this.check(WdConstant.DEFAULT_GROUP, object, ClassUtil.allFieldsOfClass(ClassUtil.peel(object)), this.getObjFieldMap(object), this.getWhiteMap(), this.getBlackMap()))
                    .setMsg(CheckResultMsg.checkFail);
        }
    }

    @Override
    public CheckResult validate(final String group, final Object object, final String... fieldSet) {
        final String groupDelegate = (null == group || "".equals(group)) ? WdConstant.DEFAULT_GROUP : group;
        if (delegate.isEmpty(object)) {
            return CheckResult.ok();
        }

        // 待核查类型不核查，直接返回核查成功
        if (ClassUtil.isCheckedType(object.getClass())) {
            return CheckResult.ok();
        } else {
            return new CheckResult()
                    .setResult(this.check(groupDelegate, object, this.getFieldToCheck(ClassUtil.peel(object), new HashSet<>(Arrays.asList(fieldSet))), this.getObjFieldMap(object), this.getWhiteMap(), this.getBlackMap()))
                    .setMsg(CheckResultMsg.checkFail);
        }
    }

    /**
     * 用于索引列表和黑白名单列表核查
     *
     * @param group          分组
     * @param object         待核查的对象
     * @param fieldSet       待核查的属性
     * @param objectFieldMap 对象的属性映射表，key为类的canonicalName，value为当前类的属性的集合
     * @param whiteSet       属性的白名单映射表，key为类的canonicalName，value为map，其中key为属性的名字，value为属性的可用值
     * @param blackSet       属性的黑名单映射表，key为类的canonicalName，value为map，其中key为属性的名字，value为属性的禁用值
     * @return 核查结果 true：核查成功；false：核查失败
     */
    private boolean check(final String group, final Object object, final Set<Field> fieldSet, final Map<String, Set<String>> objectFieldMap, final Map<String, MatchManager> whiteSet, final Map<String, MatchManager> blackSet) {
        delegate.setGroup(group);
        try {
            return delegate.available(object, fieldSet, objectFieldMap, whiteSet, blackSet);
        } finally {
            // 防止threadLocal对应的group没有释放
            delegate.clearGroup();
        }
    }

    private Map<String, Set<String>> getObjFieldMap(final Object object) {
        if (null == object) {
            return Collections.emptyMap();
        }

        // 若对象已经创建属性索引树，则直接返回
        if (objectFieldCheckMap.containsKey(object.getClass().getCanonicalName())) {
            return objectFieldCheckMap;
        }

        // 若当前对象没有对象属性索引树，则进行创建
        this.createObjectFieldMap(object);

        return objectFieldCheckMap;
    }

    /**
     * 根据对象的类型进行建立对象和属性映射树
     *
     * @param objectOrigin 待处理的对象
     */
    private void createObjectFieldMap(final Object objectOrigin) {
        if (null == objectOrigin) {
            return;
        }
        final Map.Entry<Object, Class<?>> objectAndClass = ObjectUtil.parseObject(objectOrigin);
        if (null == objectAndClass) {
            return;
        }
        final Object object = objectAndClass.getKey();
        final Class<?> cls = objectAndClass.getValue();
        final String clsCanonicalName = cls.getCanonicalName();
        // 若已经解析，则不再解析
        if (objectFieldCheckMap.containsKey(clsCanonicalName)) {
            return;
        }

        final Set<Field> fieldSet = ClassUtil.allFieldsOfClass(cls);
        if (!CollectionUtil.isEmpty(fieldSet)) {
            // 待核查类型用于获取注解的属性
            fieldSet.forEach(f -> {
                final Matcher[] matcherList = f.getAnnotationsByType(Matcher.class);
                for(final Matcher matcher: matcherList) {
                    if (null != matcher && !matcher.disable()) {
                        this.addObjectFieldMap(clsCanonicalName, f.getName());
                        if (matcher.accept()) {
                            this.addWhiteValueMap(whiteGroupMap, clsCanonicalName, f, matcher);
                        } else {
                            this.addWhiteValueMap(blackGroupMap, clsCanonicalName, f, matcher);
                        }
                    }
                }

                final Matchers matchers = f.getAnnotation(Matchers.class);
                if (null != matchers) {
                    Stream.of(matchers.value()).forEach(w -> {
                        this.addObjectFieldMap(clsCanonicalName, f.getName());
                        if (w.accept()) {
                            this.addWhiteValueMap(whiteGroupMap, clsCanonicalName, f, w);
                        } else {
                            this.addWhiteValueMap(blackGroupMap, clsCanonicalName, f, w);
                        }
                    });
                }
            });

            // 非待核查类型拆分开进行迭代分析
            fieldSet.stream().filter(f -> !ClassUtil.isCheckedType(f.getType())).forEach(f -> {
                // 该属性对应的类型是否添加了注解 Check
                if (f.isAnnotationPresent(Check.class)) {
                    this.addObjectFieldMap(clsCanonicalName, f.getName());
                    Object fieldData = null;
                    try {
                        f.setAccessible(true);
                        fieldData = f.get(object);
                        this.createObjectFieldMap(fieldData);
                    } catch (final IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
    }

    private void addObjectFieldMap(final String objectClsName, final String fieldName) {
        objectFieldCheckMap.compute(objectClsName, (k, v) -> {
            if (null == v) {
                final Set<String> fieldSet = new HashSet<>();
                fieldSet.add(fieldName);
                return fieldSet;
            } else {
                v.add(fieldName);
                return v;
            }
        });
    }

    private void addWhiteValueMap(final Map<String, MatchManager> groupMather, final String clsCanonicalName, final Field field, final Matcher matcher) {
        Arrays.asList(matcher.group()).forEach(g -> groupMather.compute(g, (k, v) -> {
            if (null == v) {
                return new MatchManager().addWhite(clsCanonicalName, field, matcher, context);
            } else {
                v.addWhite(clsCanonicalName, field, matcher, context);
                return v;
            }
        }));
    }

    private Map<String, MatchManager> getWhiteMap() {
        return whiteGroupMap;
    }

    private Map<String, MatchManager> getBlackMap() {
        return blackGroupMap;
    }

    /**
     * 将要核查的属性转换为Field类型
     *
     * @param tClass      目标类型
     * @param fieldStrSet 调用方想要调用的属性的字符串名字集合
     * @return 属性的Field类型集合
     */
    private Set<Field> getFieldToCheck(Class tClass, final Set<String> fieldStrSet) {
        return ClassUtil.allFieldsOfClass(tClass).stream().filter(f -> fieldStrSet.contains(f.getName())).collect(Collectors.toSet());
    }
}
