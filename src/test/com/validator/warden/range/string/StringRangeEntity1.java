package com.validator.warden.range.string;

import com.validator.warden.annotation.Matcher;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author DandyLuo
 */
@Data
@Accessors(chain = true)
public class StringRangeEntity1 {

    @Matcher(range = "[, 3]")
    private String data;
}
