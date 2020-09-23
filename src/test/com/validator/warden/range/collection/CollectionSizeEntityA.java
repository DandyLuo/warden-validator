package com.validator.warden.range.collection;


import java.util.List;

import com.validator.warden.annotation.Check;
import com.validator.warden.annotation.Matcher;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author DandyLuo
 */
@Data
@Accessors(chain = true)
public class CollectionSizeEntityA {

    private String name;

    @Check
    @Matcher(range = "(0, 2]")
    private List<CollectionSizeEntityB> bList;
}
