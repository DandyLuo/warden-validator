package com.validator.warden.notblank;

import com.validator.warden.annotation.Matcher;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author public class NotBlankEntity2 {
 */
@Data
@Accessors(chain = true)
public class NotBlankEntity2 {

    @Matcher(notBlank = "true")
    private String name;
    /**
     * 有异常
     */
    @Matcher(notBlank = "true")
    private Integer age;
}
