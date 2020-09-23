package com.validator.warden.customize;

import com.validator.warden.annotation.Matcher;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author DandyLuo
 */
@Data
@Accessors(chain = true)
public class CustomizeEntity {

    @Matcher(customize = "com.validator.warden.customize.CustomizeCheck#ageValid")
    private Integer age;

    @Matcher(customize = "com.validator.warden.customize.CustomizeCheck#nameValid")
    private String name;

    @Matcher(customize = "com.validator.warden.customize.CustomizeCheck#addressInvalid", accept = false)
    private String address;

    @Matcher(customize = "com.validator.warden.customize.CustomizeCheck#ratioJudge")
    private Float mRatio;

    private Float nRatio;

    @Matcher(customize = "com.validator.warden.customize.CustomizeCheck#twoParam")
    private String twoPa;

    @Matcher(customize = "com.validator.warden.customize.CustomizeCheck#threeParam")
    private String threePa;

    @Matcher(customize = "com.validator.warden.customize.CustomizeCheck#fieldErrMsgMatch")
    private String fieldErrMsg;

    @Matcher(customize = "com.validator.warden.customize.CustomizeCheck#fieldErrMsgMatch2", errMsg = "#current 数据不符合")
    private String fieldErrMsg2;
}
