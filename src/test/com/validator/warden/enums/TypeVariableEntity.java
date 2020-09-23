package com.validator.warden.enums;

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
public class TypeVariableEntity<T> {

    private Integer pageNo;
    @Matcher(range = "[0, 100]", value = "null")
    private Integer pageSize;

    @Check
    private T data;

    @Check
    private List<T> dataList;
}
