package com.validator.warden.match;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import com.validator.warden.annotation.Matcher;
import com.validator.warden.util.ObjectUtil;
import lombok.Setter;

/**
 * 指定的值判断，对应{@link Matcher#value()}
 *
 * @author DandyLuo
 */
@Setter
public class ValueMath extends AbstractBlackWhiteMatch {

    private Set<Object> values;

    @Override
    public boolean match(final Object object, final String fieldName, final Object value) {
        if (this.values.contains(value)) {
            this.setBlackMsg("属性 {0} 的值 {1} 位于禁用值 {2}中", fieldName, value, this.values.toString());
            return true;
        } else {
            this.setWhiteMsg("属性 {0} 的值 {1} 不在只可用列表 {2} 中", fieldName, value, this.values.toString());
            return false;
        }
    }

    @Override
    public boolean isEmpty() {
        return values.isEmpty();
    }

    /**
     * 将设置的数据转换为对应结构类型的数据
     *
     * @param field 对象的属性类型
     * @param values 属性的可用的或者不可用列表String形式
     * @return 值匹配器
     */
    public static ValueMath build(final Field field, final String[] values) {
        if (null == values || 0 == values.length) {
            return null;
        }

        final ValueMath valueMath = new ValueMath();
        valueMath.setValues(Arrays.stream(values).map(i -> {
            if (null != i) {
                return ObjectUtil.cast(field.getType(), i);
            } else {
                return null;
            }
        }).collect(Collectors.toSet()));

        return valueMath;
    }
}
