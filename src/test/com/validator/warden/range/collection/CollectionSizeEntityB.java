package com.validator.warden.range.collection;

import com.validator.warden.annotation.Matcher;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author DandyLuo
 */
@Data
@Accessors(chain = true)
public class CollectionSizeEntityB {

    @Matcher(range = "(10, 30)")
    private Integer bField;
}
