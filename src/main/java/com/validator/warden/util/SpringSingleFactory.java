package com.validator.warden.util;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.validator.warden.spring.WdSpringBeanContext;

/**
 * 单例工厂
 *
 * @author DandyLuo
 * @since 2019/4/14 上午12:58
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
