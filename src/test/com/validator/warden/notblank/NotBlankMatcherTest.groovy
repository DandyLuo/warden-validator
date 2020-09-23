package com.validator.warden.notblank

import com.validator.warden.WardenValidator
import com.validator.warden.core.domain.CheckResult
import org.junit.Assert
import spock.lang.Specification

/**
 * @author DandyLuo
 */
class NotBlankMatcherTest extends Specification {

    /**
     * 不null测试
     * @return
     */
    def notBlankTest() {
        given:
        NotBlankEntity entity = new NotBlankEntity().setName(name).setAge(age)

        expect:
        CheckResult checkResult = WardenValidator.validate(entity)
        Boolean act = checkResult.isSuccess();
        Assert.assertEquals(result, act)
        if (!act) {
            println WardenValidator.errMsgChain;
        }

        where:
        name | age  | result
        "a"  | null | true
        null | null | false
        null | 5    | false
        ""   | 3    | true
        "a"  | 3    | true
    }

    /**
     * blank测试
     * @return
     */
    def blankTest() {
        given:
        NotBlankEntity3 entity = new NotBlankEntity3().setName(name).setAge(age)

        expect:
        CheckResult checkResult = WardenValidator.validate(entity)
        Boolean act = checkResult.isSuccess();
        Assert.assertEquals(result, act)
        if (!act) {
            println WardenValidator.errMsgChain;
        }

        where:
        name | age  | result
        "a"  | null | false
        null | null | true
        null | 5    | true
        ""   | 3    | true
        "a"  | 3    | false
    }
}
