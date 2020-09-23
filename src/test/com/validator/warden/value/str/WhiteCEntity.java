package com.validator.warden.value.str;

import java.util.List;


import com.validator.warden.annotation.Check;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author DandyLuo
 */
@Data
@Accessors(chain = true)
public class WhiteCEntity {

    @Check
    private List<CEntity> cEntities;
    @Check
    private BEntity bEntity;
}
