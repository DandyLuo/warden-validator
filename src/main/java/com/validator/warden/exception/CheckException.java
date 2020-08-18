package com.validator.warden.exception;

/**
 * @author luoruihua
 */
public class CheckException extends WardenException {

    private static final String PRE = "匹配失败:";
    private static final long serialVersionUID = 8689831092469064497L;

    public CheckException(final String message) {
        super(PRE + message);
    }
}
