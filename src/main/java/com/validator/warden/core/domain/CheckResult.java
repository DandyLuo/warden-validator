package com.validator.warden.core.domain;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author DandyLuo
 */
@Data
@Accessors(chain = true)
public class CheckResult {
    private boolean result;
    private Enum<CheckResultMsg> msg;

    public CheckResult() {

    }

    public static CheckResult ok() {
        return new CheckResult().setResult(true).setMsg(CheckResultMsg.checkFail);
    }
}
