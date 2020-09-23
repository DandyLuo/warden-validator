package com.validator.warden.notnull;

import com.validator.warden.annotation.Matcher;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author DandyLuo
 */
@Data
@Accessors(chain = true)
public class NotNullEntity2 {

    @Matcher(notNull = "false")
    private String name;

    @Matcher(notNull = "false")
    private Integer age;
}
