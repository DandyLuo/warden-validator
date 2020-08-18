package com.validator.warden.exception;

/**
 * @author luoruihua
 */
public class WardenException extends RuntimeException {

    private static final String PRE = "核查异常：";
    private static final long serialVersionUID = -3533024956316499793L;

    public WardenException(final String message) {
        super(PRE + message);
    }

    public WardenException(final String message, final Throwable e) {
        super(PRE + message, e);
    }

    public WardenException(final Throwable e) {
        super(e);
    }
}
