package com.validator.warden.notnull

import com.validator.warden.WardenValidator
import com.validator.warden.core.domain.CheckResult
import org.junit.Assert
import spock.lang.Specification

/**
 * @author DandyLuo
 */
class NotNullMatcherTest extends Specification {

    /**
     * 不null测试
     * @return
     */
    def notNullTest() {
        given:
        NotNullEntity entity = new NotNullEntity().setName(name).setAge(age)

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
        null | null | false
        null | 5    | false
        "a"  | 3    | true
    }

    /**
     * null测试
     * @return
     */
    def nullTest() {
        given:
        NotNullEntity2 entity = new NotNullEntity2().setName(name).setAge(age)

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
        null | 5    | false
        "a"  | 3    | false
    }
}
