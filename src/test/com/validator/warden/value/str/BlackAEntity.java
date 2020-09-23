package com.validator.warden.value.str;

import com.validator.warden.annotation.Matcher;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author DandyLuo
 */
@Data
@Accessors(chain = true)
public class BlackAEntity {

    @Matcher(value = {"a","b","c","null"}, accept = false)
    private String name;
    private Integer age;
}
