package com.validator.warden.util;

import java.util.Collection;
import java.util.Map;

/**
 * 集合判空
 *
 * @author DandyLuo
 */
public class CollectionUtil {

    public static boolean isEmpty(final Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isEmpty(final Map<?, ?> map) {
        return map == null || map.isEmpty();
    }
}
