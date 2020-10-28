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

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.validator.warden.spring.WdSpringBeanContext;

/**
 * 单例工厂
 *
 * @author DandyLuo
 */
public final class SpringSingleFactory {

    private static final Map<String, Object> DATA_MAP = new ConcurrentHashMap<>();

    private SpringSingleFactory() {}

    @SuppressWarnings("unchecked")
    public static <T> T getSingle(final Class<T> tClass) {
        if (null == tClass) {
            return null;
        }

        try {
            final T result = WdSpringBeanContext.getBean(tClass);
            if (null != result) {
                return result;
            }
        } catch (final Exception ignored) {}

        return (T) DATA_MAP.computeIfAbsent(tClass.getCanonicalName(), k -> {
            try {
                final Constructor<?> constructor = tClass.getDeclaredConstructor();
                constructor.setAccessible(true);
                return constructor.newInstance();
            } catch (final Exception ignored) {}
            return null;
        });
    }
}
