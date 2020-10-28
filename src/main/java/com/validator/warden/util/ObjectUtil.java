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
package com.validator.warden.util;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import lombok.experimental.UtilityClass;

/**
 * @author DandyLuo
 */
@UtilityClass
public class ObjectUtil {

    private final String NULL_STR = "null";

    /**
     * 将对象的数据，按照cls类型进行转换
     *
     * @param cls  待转换的Class类型
     * @param data 数据
     * @return Class类型对应的对象
     */
    public Object cast(final Class<?> cls, final String data) {
        if (cls.equals(String.class)) {
            // 针对data为null的情况进行转换
            if (NULL_STR.equals(data)) {
                return null;
            }
            return data;
        } else if (Character.class.isAssignableFrom(cls)) {
            return data.toCharArray();
        } else {
            try {
                if (NULL_STR.equals(data)) {
                    return null;
                }
                return cls.getMethod("valueOf", String.class).invoke(null, data);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 解析对象包括通配符部分的结构
     *
     * <p>
     *     该函数会将数据削减
     *
     * @param object 待解析对象
     * @return 解析后的对象和对象的类型：key为解析后的对象值，value为key对应的类型
     */
    public Map.Entry<Object, Class<?>> parseObject(final Object object) {
        if (null == object) {
            return null;
        }
        if (object instanceof Collection) {
            final Collection collection = (Collection) object;
            if (!collection.isEmpty()) {
                final Iterator<?> iterator = collection.iterator();
                if (iterator.hasNext()) {
                    return parseObject(iterator.next());
                }
            }
            return null;
        } else if (object instanceof Map) {
            final Map<?, ?> map = (Map<?, ?>) object;
            return parseObject(map.values());
        } else if (object.getClass().isArray()) {
            return parseObject(Array.get(object, 0));
        } else {
            if (ClassUtil.isCheckedType(object.getClass())) {
                return null;
            }
            return new AbstractMap.SimpleEntry<>(object, object.getClass());
        }
    }
}
