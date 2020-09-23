package com.validator.warden.value.bool

import com.validator.warden.WardenValidator
import com.validator.warden.core.domain.CheckResult
import org.junit.Assert
import spock.lang.Specification

/**
 * @author DandyLuo
 */
class BooleanValueTest extends Specification {

    def "boolean类型测试"(){
        given:
        BooleanEntity entity = new BooleanEntity()
        entity.setFlag(flag)

        expect:
        CheckResult act = WardenValidator.validate(entity)
        boolean actResult = act.isSuccess()
        if (!actResult) {
            println WardenValidator.getErrMsgChain()
        }
        Assert.assertEquals(result, actResult)

        where:
        flag  | result
        true  | true
        false | false
        null  | true
    }
}
