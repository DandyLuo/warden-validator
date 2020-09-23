package com.validator.warden.type;

import com.validator.warden.annotation.Matcher;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author luoruihua
 * @since 2020/9/23
 */
@Data
@Accessors(chain = true)
public class TypeEntity {

    /**
     * 没有必要设置type
     */
    @Matcher(type = Integer.class)
    private Integer data;

    @Matcher(type = String.class)
    private CharSequence name;

    @Matcher(type = {Integer.class, Float.class})
    private Object obj;

    @Matcher(type = Number.class)
    private Object num;
}
