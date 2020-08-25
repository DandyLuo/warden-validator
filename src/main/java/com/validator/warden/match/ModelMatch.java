package com.validator.warden.match;

import com.validator.warden.annotation.Matcher;
import com.validator.warden.match.domain.FieldModel;
import com.validator.warden.match.factory.Builder;

/**
 * 指定的类型判断，对应{@link Matcher#model()}
 *
 * @author DandyLuo
 */
public class ModelMatch extends AbstractBlackWhiteMatch implements Builder<ModelMatch, FieldModel> {

    private FieldModel fieldModel;

    @Override
    public boolean match(final Object object, final String nam, final Object value) {
        if (value instanceof String) {
            if (this.fieldModel.match((String) value)) {
                this.setBlackMsg("属性 {0} 的值 {1} 命中不允许的类型 [FieldModel-{2}]", nam, value, this.fieldModel.getName());
                return true;
            } else {
                this.setWhiteMsg("属性 {0} 的值 {1} 没有命中只允许类型 [FieldModel-{2}]", nam, value, this.fieldModel.name());
            }
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        return null == this.fieldModel;
    }

    @Override
    public ModelMatch build(final FieldModel obj) {
        if (obj.equals(FieldModel.DEFAULT)) {
            return null;
        }
        this.fieldModel = obj;
        return this;
    }
}
