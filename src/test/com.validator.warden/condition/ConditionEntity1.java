package com.validator.warden.condition;

import com.validator.warden.annotation.Matcher;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author DandyLuo
 */
@Data
@Accessors(chain = true)
public class ConditionEntity1 {

    @Matcher(condition = "#current + #root.num2 > 100")
    private Integer num1;

    @Matcher(condition = "#current < 20")
    private Integer num2;

    @Matcher(condition = "(++#current) >31")
    private Integer num3;
}
