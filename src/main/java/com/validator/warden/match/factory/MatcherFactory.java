package com.validator.warden.match.factory;


/**
 * 匹配器工厂，用于根据对应的匹配器类型和入参，创建指定的匹配器
 *
 * @author DandyLuo
 */
public class MatcherFactory {

    public static <T, K> T build(final Class<? extends Builder<T, K>> tClass, final K params){
        if (null == params){
           return null;
        }
        try {
            return tClass.newInstance().build(params);
        } catch (final InstantiationException | IllegalAccessException ignored) {

        }
        return null;
    }
}
