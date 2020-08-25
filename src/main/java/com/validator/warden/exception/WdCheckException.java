package com.validator.warden.exception;

/**
 * @author DandyLuo
 */
public class WdCheckException extends WdException {

    private static final String PRE = "匹配失败:";

    public WdCheckException(String message) {
        super(PRE + message);
    }
}
