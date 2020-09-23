package com.validator.warden.enums;

import com.validator.warden.annotation.Matcher;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author DandyLuo
 */
@Data
@Accessors(chain = true)
public class ChildDataEntity extends DataEntity {

    @Matcher(value = {"a", "b"})
    private String nameChild;
}
