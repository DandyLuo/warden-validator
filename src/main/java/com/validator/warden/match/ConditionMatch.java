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
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiPredicate;
import java.util.regex.Pattern;

import com.validator.warden.annotation.Matcher;
import com.validator.warden.express.ExpressParser;
import com.validator.warden.util.Maps;

/**
 * 正则表达式判断，对应{@link Matcher#condition()}
 *
 * @author DandyLuo
 */
public class ConditionMatch extends AbstractBlackWhiteMatch {

    private static final String ROOT = "#root";
    private static final String CURRENT = "#current";
    /**
     * 表达式中的变量和实际的变量名字的对应
     */
    private final Map<String, String> fieldNameMap = new HashMap<>(6);
    /**
     * 判决对象
     */
    private BiPredicate<Object, Object> predicate;
    /**
     * 表达式解析对象
     */
    private ExpressParser parser;
    /**
     * 表达式
     */
    private String express;
    private Field currentField;

    @Override
    public boolean match(final Object object, final String name, final Object value) {
        if (this.predicate.test(object, value)) {
            this.setBlackMsg("属性 {0} 的值 {1} 命中禁用条件 {2} ", name, value, this.replaceSystem(this.express));
            return true;
        } else {
            this.setWhiteMsg("属性 {0} 的值 {1} 不符合条件 {2} ", name, value, this.replaceSystem(this.express));
            return false;
        }
    }

    @Override
    public boolean isEmpty() {
        return null == this.predicate;
    }

    public static ConditionMatch build(final Field field, final String obj) {
        if (null == obj || "".equals(obj)) {
            return null;
        }

        final ConditionMatch matcher = new ConditionMatch();
        matcher.express = obj;
        matcher.currentField = field;
        matcher.parser = new ExpressParser();
        matcher.predicate = (root, current) -> {
            matcher.parser.addBinding(matcher.parseConditionExpress(matcher.express, root, matcher.currentField, current));
            return matcher.parser.parse("import static java.lang.Math.*\n", matcher.rmvfix(matcher.express));
        };
        return matcher;
    }

    /**
     * 将条件表达式中的占位符进行替换为具体的数据
     *
     * @param express 表达式
     * @param root 当前属性所在的对象
     * @param currentField 当前属性
     * @param current 当前属性的值
     * @return 返回对应的替换的数据映射
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    private Maps parseConditionExpress(final String express, final Object root, final Field currentField, final Object current) {
        final Maps maps = Maps.of();
        final String regex = "(#root)\\.(\\w+)";
        final java.util.regex.Matcher m = Pattern.compile(regex).matcher(express);
        while (m.find()) {
            final String fieldFullName = m.group();
            final Object fieldValue = this.getFieldValue(fieldFullName, root);
            final String rmvFieldName = this.rmvfix(fieldFullName);
            maps.put(rmvFieldName, fieldValue);
            this.fieldNameMap.put(fieldFullName, rmvFieldName);
        }

        if (express.contains(CURRENT)) {
            maps.put(this.rmvfix(CURRENT), current);
            this.fieldNameMap.put(CURRENT, currentField.getName());
        }

        return maps;
    }

    private Object getFieldValue(final String fullFieldName, final Object root) {
        if (!fullFieldName.contains(ROOT)) {
            return null;
        }
        final String filedName = fullFieldName.substring(ROOT.length() + 1);
        try {
            final Field field = root.getClass().getDeclaredField(filedName);
            field.setAccessible(true);
            return field.get(root);
        } catch (final NoSuchFieldException | IllegalAccessException ignored) {

        }
        return null;
    }

    /**
     * 移除字符串中的#号和root
     * @param str 待转换的字符
     * @return #root 到 root
     */
    @SuppressWarnings("all")
    private String rmvfix(String str){
        if(str.contains("#root.")){
            str = str.replace("#root.", "");
        }

        if(str.contains("#")){
            return str.replace("#", "");
        }
        return str;
    }

    /**
     * 替换系统内置的变量，比如："{@code #root.age + #current < 100}  返回 {@code age + testInteger < 100}
     * @param str 输入的字符
     * @return 替换后的字符串表达式
     */
    private String replaceSystem(final String str) {
        final AtomicReference<String> temStr = new AtomicReference<>(str);
        this.fieldNameMap.forEach((key, value) -> temStr.set(temStr.get().replace(key, value)));
        return temStr.get();
    }
}
