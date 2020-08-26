package com.validator.warden.common;

import com.validator.warden.annotation.Matcher;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author DandyLuo
 */
@Data
@Accessors(chain = true)
public class TestEntity {

    @Matcher(value = {"nihao", "ok"}, accept = false)
    private String name;
    @Matcher(range = "[12, 32]")
    private Integer age;
    @Matcher({"beijing", "shanghai"})
    private String address;
}
