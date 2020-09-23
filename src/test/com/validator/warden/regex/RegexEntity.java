package com.validator.warden.regex;

import com.validator.warden.annotation.Matcher;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author DandyLuo
 */
@Data
@Accessors(chain = true)
public class RegexEntity {

    @Matcher(regex = "^\\d+$")
    private String regexValid;

    @Matcher(regex = "^\\d+$", accept = false)
    private String regexInValid;
}
