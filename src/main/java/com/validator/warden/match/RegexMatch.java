package com.validator.warden.match;

import java.util.regex.Pattern;

import com.validator.warden.annotation.Matcher;
import com.validator.warden.match.factory.Builder;

/**
 * 正则表达式判断，对应{@link Matcher#regex()}
 *
 * @author DandyLuo
 */
public class RegexMatch extends AbstractBlackWhiteMatch implements Builder<RegexMatch, String> {

    private Pattern pattern;

    @Override
    public boolean match(final Object object, final String name, final Object value) {
        if (value instanceof String) {
            if (this.pattern.matcher((String) value).matches()) {
                this.setBlackMsg("属性 {0} 的值 {1} 命中禁用的正则表达式 {2} ", name, value, this.pattern.pattern());
                return true;
            } else {
                this.setWhiteMsg("属性 {0} 的值 {1} 没命中只允许的正则表达式 {2} ", name, value, this.pattern.pattern());
            }
        } else {
            this.setWhiteMsg("属性 {0} 的值 {1} 不是String类型", name, value);
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        return (null == this.pattern);
    }

    @Override
    public RegexMatch build(final String obj) {
        if (null == obj || "".equals(obj)){
            return null;
        }
        this.pattern = Pattern.compile(obj);
        return this;
    }
}
