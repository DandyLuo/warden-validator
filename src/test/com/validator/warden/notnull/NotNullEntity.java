package com.validator.warden.notnull;

import com.validator.warden.annotation.Matcher;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author DandyLuo
 */
@Data
@Accessors(chain = true)
public class NotNullEntity {

    @Matcher(notNull = "true")
    private String name;

    @Matcher(notNull = "true")
    private Integer age;
}
