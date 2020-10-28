/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
