package com.validator.warden.core.delegate;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.validator.warden.annotation.Check;
import com.validator.warden.core.domain.WdContext;
import com.validator.warden.match.manager.FieldMatchManager;
import com.validator.warden.match.manager.MatchManager;
import com.validator.warden.util.ClassUtil;
import com.validator.warden.util.CollectionUtil;

/**
 * @author DandyLuo
 */
public final class CheckDelegate {

    private final ThreadLocal<String> localGroup;
    private final WdContext context;

    public CheckDelegate(final WdContext context) {
        this.localGroup = new ThreadLocal<>();
        this.context = context;
    }

    public void setGroup(final String group) {
        this.context.clear();
        this.localGroup.set(group);
    }

    /**
     * 清理threadLocal保存的group
     */
    public void clearGroup() {
        this.localGroup.remove();
    }

    /**
     * 判断自定义结构的数据值是否是可用的，这里判断逻辑是通过黑名单和白名单
     *
     * @param object 为集合、Map和自定义结构，其中待核查类型，为另外一个重载函数
     * @param fieldSet 待核查的属性的集合
     * @param objectFieldMap 自定义对象属性的核查映射，key为类的名字，value为类中对应的属性名字
     * @param whiteSet 对象属性集合的可用值列表
     * @param blackSet 对象属性集合的不可用值列表
     * @return false：如果对象中有某个属性不可用 true：所有属性都可用
     */
    public boolean available(final Object object, final Set<Field> fieldSet, final Map<String, Set<String>> objectFieldMap, final Map<String, MatchManager> whiteSet, Map<String, MatchManager> blackSet) {
        if (null == object) {
            // 对于对象中的其他属性不核查
            return true;
        }

        final Class<?> cls = object.getClass();
        if (ClassUtil.isCheckedType(cls)) {
            // 底层基本校验类型，则放过
            return true;
        } else if (Collection.class.isAssignableFrom(cls)) {
            // 集合类型，则剥离集合，获取泛型的类型再进行判断
            final Collection<?> collection = (Collection<?>) object;
            if (!CollectionUtil.isEmpty(collection)) {
                return collection.stream().allMatch(c -> this.available(c, fieldSet, objectFieldMap, whiteSet, blackSet));
            } else {
                // 为空则忽略
                return true;
            }
        } else if (Map.class.isAssignableFrom(cls)) {
            // Map 结构中的数据的判断，目前只判断value中的值
            final Map<?, ?> map = (Map<?, ?>) object;
            if (!CollectionUtil.isEmpty(map)) {
                // 检查所有不合法属性
                final boolean allMatch = map.values().stream().filter(Objects::nonNull).allMatch(v -> this.available(v, fieldSet, objectFieldMap, whiteSet, blackSet));
                if (allMatch) {
                    return true;
                }
                this.context.append("Map的value中有不合法");
                return false;
            } else {
                // 为空则忽略
                return true;
            }
        } else if (cls.isArray()) {
            // 数组类型处理
            boolean available = true;
            final int arrayLength = Array.getLength(object);
            for (int index = 0; index < arrayLength; index++) {
                if (!this.available(Array.get(object, index), fieldSet, objectFieldMap, whiteSet, blackSet)) {
                    available = false;
                    break;
                }
            }

            if (!available) {
                this.context.append("数组值有不合法");
                return false;
            }
            return true;
        } else {
            // 自定义类型的话，则需要核查当前属性是否需要核查，不需要核查则略过
            if (!this.objectNeedCheck(object, objectFieldMap)) {
                return true;
            }

            // 自定义类型，所有匹配成功才算成功，如果对象中任何一个属性不可用，则对象不可用，这里要核查自己的所有属性和继承的父类的public属性
            final List<Field> fieldList = ClassUtil.allFieldsOfClass(object.getClass()).stream().filter(fieldSet::contains).collect(Collectors.toList());
            for (final Field field : fieldList) {
                if (!this.available(object, field, objectFieldMap, whiteSet, blackSet)) {
                    this.context.append("类型 {0} 核查失败", object.getClass().getSimpleName());
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * 根据属性的类型，判断属性的值是否在对应的值列表中
     *
     * @param object 对象
     * @param field 属性
     * @param whiteGroupMather 属性值可用值列表
     * @param blackGroupMather 属性值的不用值列表
     * @param objectFieldMap 对象核查的属性映射
     * @return true 可用， false 不可用
     */
    private boolean available(final Object object, final Field field, final Map<String, Set<String>> objectFieldMap, final Map<String, MatchManager> whiteGroupMather, final Map<String, MatchManager> blackGroupMather) {
        final Class<?> cls = field.getType();

        if (ClassUtil.isCheckedType(cls)) {
            // 待核查类型，则直接校验
            return this.primaryFieldAvailable(object, field, whiteGroupMather, blackGroupMather);
        } else {
            boolean result = true;
            // 不是待核查类型，先判断是否添加了黑白名单注解配置，否则按照复杂类型处理
            if (this.matcherContainField(object, field, whiteGroupMather, blackGroupMather)) {
                result = this.primaryFieldAvailable(object, field, whiteGroupMather, blackGroupMather);
                if (!result) {
                    return false;
                }
            }

            // 包含拆解注解，则要查看拆解后的处理
            if (field.isAnnotationPresent(Check.class)) {
                try {
                    field.setAccessible(true);
                    result = this.available(field.get(object), objectFieldMap, whiteGroupMather, blackGroupMather);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }

            if(!result) {
                this.context.append("类型 {0} 的属性 {1} 核查失败", object.getClass().getSimpleName(), field.getName());
            }
            return result;
        }
    }

    /**
     * 判断黑白名单是否都不包含该属性
     *
     * @param object 对象
     * @param field 属性
     * @param whiteGroupMather 属性值可用值列表
     * @param blackGroupMather 属性值的不用值列表
     * @return true：都不包含该属性，false：有包含该属性
     */
    private boolean matcherContainField(final Object object, final Field field, final Map<String, MatchManager> whiteGroupMather,
        final Map<String, MatchManager> blackGroupMather){
        final boolean blackEmpty = this.fieldCheckIsEmpty(object, field, blackGroupMather);
        final boolean whiteEmpty = this.fieldCheckIsEmpty(object, field, whiteGroupMather);
        // 黑白名单有任何一个不空，则可以进行匹配
        return !whiteEmpty || !blackEmpty;
    }

    private boolean available(final Object object, final Map<String, Set<String>> objectFieldMap, final Map<String, MatchManager> whiteSet, final Map<String, MatchManager> blackSet) {
        if (null == object) {
            return true;
        }

        return this.available(object, ClassUtil.allFieldsOfClass(ClassUtil.peel(object)), objectFieldMap, whiteSet, blackSet);
    }

    /**
     * 判断对象的一个基本属性是否可用
     *
     * @param object 属性的对象
     * @param field 属性
     * @param whiteGroupMather 属性的可用值列表
     * @param blackGroupMather 属性的不可用值列表
     * @return true：可用，false：不可用
     */
    @SuppressWarnings("all")
    private boolean primaryFieldAvailable(Object object, Field field, Map<String, MatchManager> whiteGroupMather, Map<String, MatchManager> blackGroupMather) {
        boolean blackEmpty = fieldCheckIsEmpty(object, field, blackGroupMather);
        boolean whiteEmpty = fieldCheckIsEmpty(object, field, whiteGroupMather);
        // 1.黑白名单都有空，则不核查该参数，可用
        if (whiteEmpty && blackEmpty) {
            return true;
        }

        field.setAccessible(true);
        // 2.黑名单不空，而且匹配到了，则不可用
        if (!blackEmpty && fieldMatch(object, field, blackGroupMather, false)) {
            return false;
        }

        // 3.白名单不空，而且该属性没有匹配到，则不可用
        if (!whiteEmpty && !fieldMatch(object, field, whiteGroupMather, true)) {
            return false;
        }

        return true;
    }

    /**
     * 判断对象是否需要继续核查
     *
     * @param object 待判决对象
     * @param objectFieldMap 对象和属性的映射map
     * @return true 对象需要继续核查 false 对象不需要通过黑白名单核查
     */
    private boolean objectNeedCheck(final Object object, final Map<String, Set<String>> objectFieldMap) {
        if (!CollectionUtil.isEmpty(objectFieldMap)) {
            return objectFieldMap.containsKey(object.getClass().getCanonicalName());
        }
        return false;
    }

    /**
     * 对象的所有判断是否都为空
     *
     * @param object 对象
     * @param field 对象的属性
     * @param groupMather 可用或者不可用的分组匹配器
     * @return true:所有为空，false属性都有
     */
    private boolean fieldCheckIsEmpty(final Object object, final Field field, final Map<String, MatchManager> groupMather) {
        if (this.checkAllMatcherDisable(object, field, groupMather)) {
            return true;
        }

        final Map<String, List<FieldMatchManager>> fieldValueSetMap = groupMather.get(this.localGroup.get()).getJudge(object.getClass().getCanonicalName());
        if (!CollectionUtil.isEmpty(fieldValueSetMap)) {
            return fieldValueSetMap.get(field.getName()).isEmpty();
        }
        return true;
    }

    /**
     * 返回当前属性的所有匹配器是否都禁用
     *
     * @param object 待核查对象
     * @param field 对象的属性
     * @param groupMather 组匹配器
     * @return true 都禁用，false 有可用的
     */
    private boolean checkAllMatcherDisable(final Object object, final Field field, final Map<String, MatchManager> groupMather) {
        final String group = this.localGroup.get();
        if (groupMather.containsKey(group)) {
            final Map<String, List<FieldMatchManager>> fieldValueSetMap = groupMather.get(group)
                .getJudge(object.getClass().getCanonicalName());
            if (!CollectionUtil.isEmpty(fieldValueSetMap)) {
                final List<FieldMatchManager> fieldMatchManagerList = fieldValueSetMap.get(field.getName());
                if (null != fieldMatchManagerList) {
                    for (final FieldMatchManager fieldMatchManager : fieldMatchManagerList) {
                        // 有任何为false的，则认为可用
                        if (!fieldMatchManager.getDisable()) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * 对象的某个属性进行匹配
     *
     * @param object 对象
     * @param field 对象的属性
     * @param groupMather 可用或者不可用数据
     * @param whiteOrBlack true：白名单（匹配则OK），false：黑名单（匹配则不OK）
     * @return true：所有匹配器匹配上（匹配器内部任何一个匹配项匹配上就叫匹配上）则返回true，false：有匹配器都没有匹配上（有一个匹配器内部的匹配项都没有匹配上）则返回false
     */
    private boolean fieldMatch(final Object object, final Field field, final Map<String, MatchManager> groupMather, final Boolean whiteOrBlack) {
        if (this.checkAllMatcherDisable(object, field, groupMather)) {
            return false;
        }

        final String group = this.localGroup.get();
        if (groupMather.containsKey(group)) {
            final Map<String, List<FieldMatchManager>> fieldValueSetMap = groupMather.get(group).getJudge(object.getClass().getCanonicalName());
            if (!CollectionUtil.isEmpty(fieldValueSetMap)) {
                final List<FieldMatchManager> fieldMatchManagerList = fieldValueSetMap.get(field.getName());
                if (null != fieldMatchManagerList) {
                    for (final FieldMatchManager fieldMatchManager : fieldMatchManagerList) {
                        if (fieldMatchManager.getDisable()) {
                            continue;
                        }
                        field.setAccessible(true);
                        final Object data;
                        try {
                            data = field.get(object);
                            // 某个匹配器没有匹配上，则认为没有匹配上
                            if (!fieldMatchManager.match(object, data, this.context, whiteOrBlack)) {
                                return false;
                            }
                        } catch (final IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }
        return true;
    }

    String getErrMsgChain() {
        return this.context.getErrMsgChain();
    }

    String getErrMsg () {
        return this.context.getLastErrMsg();
    }

    public boolean isEmpty(final Object object) {
        if (object instanceof String) {
            final String str = (String) object;
            return "".equals(str) || "null".equals(str) || "undefined".equals(str);
        } else if (object instanceof Map) {
            final Map<?,?> map = (Map<?,?>) object;
            return CollectionUtil.isEmpty(map);
        } else if (object instanceof Collection) {
            final Collection<?> collection = (Collection<?>) object;
            return CollectionUtil.isEmpty(collection);
        } else {
            return object == null;
        }
    }
}
