package com.validator.warden.value.str;

import com.validator.warden.annotation.Matcher;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author DandyLuo
 */
@Data
@Accessors(chain = true)
public class WhiteAEntity {
    @Matcher({"a","b","c","null"})
    private String name;
    private String address;
}
