package com.validator.warden.value.str;

import com.validator.warden.annotation.Matcher;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author DandyLuo
 */
@Data
@Accessors(chain = true)
public class StringValueEntity1 {

    /**
     * 只要空字符，则进行拒绝，其他的都不拦截
     */
    @Matcher(value = "", accept = false, errMsg = "值#current是禁止的")
    private String emptyStr;
}
