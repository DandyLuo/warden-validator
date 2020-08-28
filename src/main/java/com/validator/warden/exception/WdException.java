package com.validator.warden.exception;

/**
 * @author DandyLuo
 */
public class WdException extends RuntimeException {

    private static final String PRE = "核查异常：";
    private static final long serialVersionUID = -6288726404418467941L;

    public WdException(final String message) {
        super(PRE + message);
    }

    public WdException(final String message, final Throwable e) {
        super(PRE + message, e);
    }

    public WdException(final Throwable e) {
        super(e);
    }
}
