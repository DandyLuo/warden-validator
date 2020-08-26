package com.validator.warden.core.domain;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author DandyLuo
 */
@Data
@Accessors(chain = true)
public class CheckResult {
    private boolean success;
    private String msg;

    public CheckResult() {

    }

    public static CheckResult ok() {
        return new CheckResult().setSuccess(true).setMsg("ok");
    }
}
