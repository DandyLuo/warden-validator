package com.validator.warden.match.factory;

/**
 * @author DandyLuo
 */
public interface Builder<T, K> {

    /**
     * 构造器模式，用于构造对应的结构数据
     *
     * @param obj 待构造需要的数据
     * @return 构造出来的数据
     */
    T build(K obj);
}
