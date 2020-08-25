package com.validator.warden.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author DandyLuo
 */
@Slf4j
public final class Maps<K, V> implements Serializable {

    private static final Integer KV_NUM = 2;
    private static final long serialVersionUID = -1370094455993996484L;

    private Maps() {}

    @Getter
    private final Map<K, V> dataMap = new HashMap<>();

    /**
     * key-value-key-value...这种格式初始化map
     *
     * @param kvs key-value-key-value这种kv入参
     * @return 构造的Maps结构
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static Maps of(final Object... kvs) {
        if (kvs.length % KV_NUM != 0) {
            log.error("Maps.of的参数需要是key-value-key-value...这种格式");
            return new Maps();
        }

        final Maps maps = new Maps();
        for (int i = 0; i < kvs.length; i += KV_NUM) {
            if (null == kvs[i]) {
                log.error("map的key不可以为null");
                return maps;
            }
            maps.put(kvs[i], kvs[i + 1]);
        }
        return maps;
    }

    public Maps<K, V> put(final Map<K, V> map) {
        this.dataMap.putAll(map);
        return this;
    }

    public Maps<K, V> put(final K key, final V value) {
        this.dataMap.put(key, value);
        return this;
    }

    public Maps<K, V> add(final Map<K, V> map) {
        this.dataMap.putAll(map);
        return this;
    }

    public Maps<K, V> add(final K key, final V value) {
        this.dataMap.put(key, value);
        return this;
    }

    public Map<K, V> build() {
        return this.dataMap;
    }

    @Override
    public String toString(){
        return this.dataMap.toString();
    }
}
