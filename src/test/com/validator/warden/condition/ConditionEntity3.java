package com.validator.warden.condition;

import com.validator.warden.annotation.Matcher;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author DandyLuo
 */
@Data
@Accessors(chain = true)
public class ConditionEntity3 {

    @Matcher(condition = "min(#current, #root.num2) > #root.num3")
    private Integer num1;
    private Integer num2;
    private Integer num3;
}
