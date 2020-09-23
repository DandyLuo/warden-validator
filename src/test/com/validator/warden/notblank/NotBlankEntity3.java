package com.validator.warden.notblank;

import com.validator.warden.annotation.Matcher;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author DandyLuo
 */
@Data
@Accessors(chain = true)
public class NotBlankEntity3 {

    @Matcher(notBlank = "false")
    private String name;
    private Integer age;
}
