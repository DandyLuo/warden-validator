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
package com.validator.warden.match.domain;

import java.util.regex.Pattern;

import lombok.Getter;

/**
 * 常见的匹配的类型
 *
 * @author DandyLuo
 */
public enum FieldModel {
    /**
     * 默认全部可用
     */
    DEFAULT("全部", "^.*$"),
    ID_CARD("身份证号", "^(((([1-6]\\d)\\d{2})\\d{2})(((19|([2-9]\\d))\\d{2})((0[1-9])|(10|11|12))([0-2][1-9]|10|20|3[0-1]))((\\d{2})(\\d)[0-9Xx]))$"),
    PHONE_NUM("手机号", "^(?:\\+?86)?1(?:3\\d{3}|5[^4\\D]\\d{2}|8\\d{3}|7(?:[35678]\\d{2}|4(?:0\\d|1[0-2]|9\\d))|9[189]\\d{2}|66\\d{2})\\d{6}$"),
    FIXED_PHONE("固定电话", "^(([0\\+]\\d{2,3}-)?(0\\d{2,3})-)(\\d{7,8})(-(\\d{3,}))?$"),
    MAIL("邮箱", "^([\\w-_]+(?:\\.[\\w-_]+)*)@[\\w-]+(.[\\w_-]+)+"),
    IP_ADDRESS("IP地址", "^((25[0-5]|2[0-4]\\d|[01]?\\d\\d?)\\.){3}(25[0-5]|2[0-4]\\d|[01]?\\d\\d?)$"),;

    @Getter
    final private String name;
    final private String regex;

    FieldModel(final String name, final String regex) {
        this.name = name;
        this.regex = regex;
    }

    public boolean match(final String content) {
        if (null == content) {
            return false;
        }
        return Pattern.matches(this.regex, content);
    }
}
