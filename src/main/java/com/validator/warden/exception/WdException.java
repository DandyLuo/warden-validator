package com.validator.warden.exception;

/**
 * @author zhouzhenyong
 * @since 2019-08-12 22:09
 */
public class WdException extends RuntimeException {

    private static final String PRE = "核查异常：";

    public WdException(String message) {
        super(PRE + message);
    }

    public WdException(String message, Throwable e) {
        super(PRE + message, e);
    }

    public WdException(Throwable e) {
        super(e);
    }
}
