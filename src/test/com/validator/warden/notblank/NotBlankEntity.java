package com.validator.warden.notblank;

import com.validator.warden.annotation.Matcher;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author DandyLuo
 */
@Data
@Accessors(chain = true)
public class NotBlankEntity {

    @Matcher(notBlank = "true")
    private String name;
    private Integer age;
}
