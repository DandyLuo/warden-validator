package com.validator.warden.exception;

/**
 * @author DandyLuo
 */
public class WdCheckException extends WdException {

    private static final String PRE = "匹配失败:";
    private static final long serialVersionUID = 8564185264490181963L;

    public WdCheckException(final String message) {
        super(PRE + message);
    }
}
