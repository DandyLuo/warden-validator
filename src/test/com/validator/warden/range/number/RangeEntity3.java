package com.validator.warden.range.number;

import com.validator.warden.annotation.Matcher;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author DandyLuo
 */
@Data
@Accessors(chain = true)
public class RangeEntity3 {

    @Matcher(range = "[0.00,3.00]")
    private Float height;

    @Matcher(range = "[10,10000]", accept = false)
    private Double money;

}
