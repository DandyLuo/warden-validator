package com.validator.warden.value.str;

import com.validator.warden.annotation.Matcher;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author DandyLuo
 */
@Data
@Accessors(chain = true)
public class AEntity {
    @Matcher({"a", "b", "c", "null"})
    private String name;
    @Matcher(value = {"null"}, accept = false)
    private Integer age;
    private String address;
}
