package com.validator.warden.condition;

import com.validator.warden.annotation.Matcher;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author DandyLuo
 */
@Data
@Accessors(chain = true)
public class ConditionEntity6 {

    @Matcher(condition = "#current == null && #root.f2 == null && #root.f3 == null", accept = false, errMsg = "至少一个为非空")
    private Long f1;
    private String f2;
    private Integer f3;
}
