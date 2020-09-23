package com.validator.warden.value.str;

import com.validator.warden.annotation.Check;
import com.validator.warden.annotation.Matcher;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author DandyLuo
 */
@Data
@Accessors(chain = true)
public class BEntity {

    @Matcher({"a","b"})
    private String name;
    @Check
    private AEntity aEntity;
}
