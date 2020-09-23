package com.validator.warden.value.str;

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
public class
CEntity {

    @Matcher({"a", "b"})
    private String name;
    @Check
    private List<BEntity> bEntities;
}
