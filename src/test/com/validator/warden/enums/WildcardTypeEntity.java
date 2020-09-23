package com.validator.warden.enums;

import java.util.Map;

import com.validator.warden.annotation.Check;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author DandyLuo
 */
@Data
@Accessors(chain = true)
public class WildcardTypeEntity {

    private String wildName;

    @Check
    private Map<String, ? extends DataEntity> dataMap;
}
