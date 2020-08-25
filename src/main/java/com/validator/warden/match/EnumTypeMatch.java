package com.validator.warden.match;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.validator.warden.annotation.Matcher;
import com.validator.warden.match.factory.Builder;

/**
 * 枚举类型多个判断，对应{@link Matcher#enumType()}
 *
 * @author DandyLuo
 */
@SuppressWarnings("rawtypes")
public class EnumTypeMatch extends AbstractBlackWhiteMatch implements Builder<EnumTypeMatch, Class<? extends Enum>[]> {

    private Class<? extends Enum>[] enumClass;

    @SuppressWarnings("unchecked")
    @Override
    public boolean match(final Object object, final String name, final Object value) {
        if(value instanceof String) {
            final String target = (String) value;
            if (this.enumClass.length > 0) {
                final boolean result = Stream.of(this.enumClass).filter(Class::isEnum).anyMatch(e -> {
                    try {
                        Enum.valueOf(e, target);
                        return true;
                    }catch (final IllegalArgumentException illegalException){
                        return false;
                    }
                });

                if (result) {
                    this.setBlackMsg("属性 {0} 对象 {1} 命中不允许的枚举 {2} 中的类型", name, value, this.getEnumStr(this.enumClass));
                    return true;
                }else{
                    this.setWhiteMsg("属性 {0} 对象 {1} 没有命中枚举 {2} 中的类型", name, value, this.getEnumStr(this.enumClass));
                }
            }
        } else if (value instanceof Number) {
            final Number target = (Number) value;
            boolean matchResult = false;
            if (this.enumClass.length > 0) {
                for (final Class<? extends Enum> eClass : this.enumClass) {
                    final Enum[] enums = eClass.getEnumConstants();
                    for (final Enum e : enums) {
                        if (e.ordinal() == target.intValue()) {
                            matchResult = true;
                        }
                    }
                }

                if (matchResult) {
                    this.setBlackMsg("属性 {0} 枚举下标 {1} 命中不允许的枚举 {2} 中的下标", name, value, this.getEnumStr(this.enumClass));
                    return true;
                }else{
                    this.setWhiteMsg("属性 {0} 枚举下标 {1} 没有命中枚举 {2} 中的下标", name, value, this.getEnumStr(this.enumClass));
                }
            }
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        return (null == this.enumClass) || this.enumClass.length == 0;
    }

    @Override
    public EnumTypeMatch build(final Class<? extends Enum>[] obj) {
        if (Arrays.asList(obj).isEmpty()) {
            return null;
        }
        this.enumClass = obj;
        return this;
    }

    private String getEnumStr(final Class<? extends Enum>[] enumClasses){
        return Stream.of(enumClasses).map(Class::getSimpleName).collect(Collectors.toList()).toString();
    }
}
