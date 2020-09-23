package com.validator.warden.range.number;

import com.validator.warden.annotation.Matcher;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author DandyLuo
 */
@Data
@Accessors(chain = true)
public class RangeEntity2 {

    @Matcher(range = "(0,100]")
    private Integer age3;

    @Matcher(range = "[0, 100)")
    private Integer age4;

    @Matcher(range = "(0, 100)")
    private Integer age5;
}
