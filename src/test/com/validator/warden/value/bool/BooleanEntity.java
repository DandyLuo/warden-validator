package com.validator.warden.value.bool;

import com.validator.warden.annotation.Matcher;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author DandyLuo
 */
@Data
@Accessors(chain = true)
public class BooleanEntity {

    @Matcher({"true", "null"})
    private Boolean flag;
}
