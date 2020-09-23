package com.validator.warden.condition;

import java.util.List;

import com.validator.warden.annotation.Matcher;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author DandyLuo
 */
@Data
@Accessors(chain = true)
public class ConditionEntity5 {

    private Integer handleType;

    @Matcher(condition = "(#current == null && #root.handleType != 1) || (#current != null && !#current.isEmpty() && #root.handleType == 1)", errMsg = "cantEditColumnList 需要在handleType为1的时候才有值")
    private List<String> nameList;
}
