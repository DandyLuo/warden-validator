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

import java.lang.reflect.Field;

import com.validator.warden.annotation.Matcher;
import com.validator.warden.exception.WdException;


/**
 * 拦截null和空字符，指定的类型判断{@link Matcher#notBlank()}
 *
 * @author DandyLuo
 */
public class NotBlankMatch extends AbstractBlackWhiteMatch {

    private Boolean notBlank;

    public static NotBlankMatch build(final Field field, final String notBlankStr) {
        final NotBlankMatch notBlankMatch = new NotBlankMatch();
        if (!"".equals(notBlankStr)) {
            notBlankMatch.notBlank = Boolean.parseBoolean(notBlankStr);
        } else {
            return notBlankMatch;
        }

        if (!CharSequence.class.isAssignableFrom(field.getType())) {
            throw new WdException("类型不匹配：匹配器属性【notBlank】不能修饰类型" + field.getType().getCanonicalName());
        }

        return notBlankMatch;
    }

    @Override
    public boolean match(final Object object, final String name, final Object value) {
        if (null == value) {
            if (this.notBlank) {
                this.setWhiteMsg("属性 {0} 的值为null", name);
                return false;
            } else {
                this.setBlackMsg("属性 {0} 的值为null", name);
                return true;
            }
        }
        if (!(value instanceof CharSequence)) {
            this.setWhiteMsg("属性 {0} 的值不是字符类型", name);
            return false;
        }

        if (this.notBlank) {
            if (!"".equals(value)) {
                this.setBlackMsg("属性 {0} 的值为非空字符", name);
            } else {
                this.setBlackMsg("属性 {0} 的值为空字符", name);
            }
            return true;
        } else {
            if ("".equals(value)) {
                this.setBlackMsg("属性 {0} 的值为空字符", name);
                return true;
            } else {
                this.setWhiteMsg("属性 {0} 的值不为空", name);
                return false;
            }
        }
    }

    @Override
    public boolean isEmpty() {
        return (null == this.notBlank);
    }
}
