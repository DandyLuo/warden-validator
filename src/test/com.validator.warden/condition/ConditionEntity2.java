package com.validator.warden.condition;

import com.validator.warden.annotation.Matcher;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author DandyLuo
 */
@Data
@Accessors(chain = true)
public class ConditionEntity2 {

    @Matcher(condition = "#root.judge")
    private Integer age;

    private Boolean judge;
}
