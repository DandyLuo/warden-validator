package com.validator.warden.enums;

import com.validator.warden.annotation.Check;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author DandyLuo
 */
@Data
@Accessors(chain = true)
public class GenericArrayTypeEntity<T> {

    @Check
    private T[] dataArray;

    @Check
    private T[][] dataArrays;
}
