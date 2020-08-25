package com.validator.warden.match;


import com.validator.warden.annotation.Matcher;
import com.validator.warden.match.factory.Builder;

/**
 * 拦截null数据，对应{@link Matcher#notNull()}
 *
 * @author DandyLuo
 */
public class NotNullMatch extends AbstractBlackWhiteMatch implements Builder<NotNullMatch, String> {

    private Boolean notNull;

    @Override
    public NotNullMatch build(final String obj) {
        if (!"".equals(obj)) {
            this.notNull = Boolean.parseBoolean(obj);
        }
        return this;
    }

    @Override
    public boolean match(final Object object, final String name, final Object value) {
        if (this.notNull) {
            if(null != value) {
                this.setBlackMsg("属性 {0} 的值为null", name);
                return true;
            } else{
                this.setWhiteMsg("属性 {0} 的值为null", name);
                return false;
            }
        } else {
            if(null == value) {
                this.setBlackMsg("属性 {0} 的值不为null", name);
                return true;
            } else{
                this.setWhiteMsg("属性 {0} 的值非空", name);
                return false;
            }
        }
    }

    @Override
    public boolean isEmpty() {
        return (null == this.notNull);
    }
}
