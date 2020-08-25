package com.validator.warden.core.domain;

/**
 * @author DandyLuo
 */
public enum CheckResultMsg {

    /**
     * 验证通过
     */
    ok("ok"),
    checkFail("check fail");

    private String msg;

    CheckResultMsg(final String msg) {
        this.msg = msg;
    }
}
