package com.validator.warden.match.manager;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.validator.warden.annotation.Matcher;
import com.validator.warden.core.domain.WdContext;
import com.validator.warden.util.Maps;


/**
 * 匹配器管理器
 *
 * @author DandyLuo
 */
public final class MatchManager {

    /**
     * 存储对象和属性以及属性对应的匹配器的映射，key为类的全路径，二级key为类的属性名字，二级value为属性的多个判断核查器
     */
    private final Map<String, Map<String, List<FieldMatchManager>>> targetFieldMap;

    public MatchManager() {
        this.targetFieldMap = new ConcurrentHashMap<>(16);
    }

    @SuppressWarnings("unchecked")
    public MatchManager addWhite(final String clsCanonicalName, final Field field, final Matcher validValue, final WdContext context) {
        this.targetFieldMap.compute(clsCanonicalName, (k, v) -> {
            if (null == v) {
                final List<FieldMatchManager> fieldMatchManagerList = new ArrayList<>();
                fieldMatchManagerList.add(FieldMatchManager.buildFromValid(field, validValue, context));
                return Maps.of(field.getName(), fieldMatchManagerList).build();
            } else {
                v.compute(field.getName(), (k1, v1) -> {
                    if (null == v1) {
                        final List<FieldMatchManager> fieldMatchManagerList = new ArrayList<>();
                        fieldMatchManagerList.add(FieldMatchManager.buildFromValid(field, validValue, context));
                        return fieldMatchManagerList;
                    } else {
                        v1.add(FieldMatchManager.buildFromValid(field, validValue, context));
                        return v1;
                    }
                });
                return v;
            }
        });
        return this;
    }

    public Map<String, List<FieldMatchManager>> getJudge(final String targetClassName) {
        return this.targetFieldMap.get(targetClassName);
    }
}
