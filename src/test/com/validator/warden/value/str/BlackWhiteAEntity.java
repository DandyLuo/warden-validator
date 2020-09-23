package com.validator.warden.value.str;

import com.validator.warden.annotation.Matcher;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author DandyLuo
 */
@Data
@Accessors(chain = true)
public class BlackWhiteAEntity {

    @Matcher({"a","b"})
    private String name;
    @Matcher(value = {"1","2"}, accept = false)
    private Integer age;
}
