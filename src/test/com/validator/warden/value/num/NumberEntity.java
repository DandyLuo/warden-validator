package com.validator.warden.value.num;

import com.validator.warden.annotation.Matcher;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author DandyLuo
 */
@Data
@Accessors(chain = true)
public class NumberEntity {

    @Matcher({"1", "2", "null"})
    private Integer age;
}
