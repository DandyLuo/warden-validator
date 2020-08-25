package com.validator.warden.match;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.validator.warden.annotation.Matcher;
import com.validator.warden.exception.WdCheckException;

/**
 * 属性实际运行时候的类型匹配，对应{@link Matcher#type()}
 *
 * @author DandyLuo
 */
public class TypeMatch extends AbstractBlackWhiteMatch {

    private final List<Class<?>> clsList = new ArrayList<>();

    public static TypeMatch build(final Field field, final Class<?>[] tClasses) {
        if (null == tClasses || Arrays.asList(tClasses).isEmpty()) {
            return null;
        }
        final Class<?> fieldType = field.getType();
        final TypeMatch matcher = new TypeMatch();

        for (final Class<?> tClass : tClasses) {
            if (!fieldType.isAssignableFrom(tClass)) {
                throw new WdCheckException("类型不匹配：Class：" + tClass + " 无法转换为 " + fieldType.getName());
            }
        }
        matcher.clsList.addAll(Arrays.asList(tClasses));
        return matcher;
    }

    /**
     * 匹配到任何一个就认为匹配上
     *
     * @param object 属性所在的数据对象
     * @param name 属性名
     * @param value 待匹配的属性对应的值
     * @return true：匹配上，false：没有匹配上
     */
    @Override
    public boolean match(final Object object, final String name, final Object value) {
        if (null == value) {
            this.setWhiteMsg("属性 {0} 的值为空", name);
            return false;
        }
        if (this.clsList.stream().anyMatch(cls -> cls.isAssignableFrom(value.getClass()))) {
            this.setBlackMsg("属性 {0} 的值 {1} 命中禁用类型 {2} ", name, value, this.clsList);
            return true;
        } else {
            this.setWhiteMsg("属性 {0} 的值 {1} 没有命中可用类型 {2} ", name, value, this.clsList);
            return false;
        }
    }

    @Override
    public boolean isEmpty() {
        return this.clsList.isEmpty();
    }
}
