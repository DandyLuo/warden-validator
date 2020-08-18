package com.validator.warden.core;

import com.validator.warden.exception.CheckException;

/**
 * @author luoruihua
 */
public interface Validator {

    /**
     * 自定义的复杂类型校验，待核查类型校验不校验，核查失败抛异常
     *
     *
     * @param object 待核查对象
     * @throws CheckException 核查失败异常
     * @since 1.0.0
     */
    public void validate(Object object) throws CheckException;
}
