package com.validator.warden.value.num

import com.validator.warden.WardenValidator
import com.validator.warden.core.domain.CheckResult
import org.junit.Assert
import spock.lang.Specification

/**
 * @author DandyLuo
 */
class IntegerValueTest extends Specification {

    def "integer类型的判断"(){
        given:
        NumberEntity entity = new NumberEntity()
        entity.setAge(age)

        expect:
        CheckResult act = WardenValidator.validate(entity)
        boolean actResult = act.isSuccess()
        if (!actResult) {
            println WardenValidator.getErrMsgChain()
        }
        Assert.assertEquals(result, actResult)

        where:
        age | result
        1    | true
        2    | true
        null | true
        3    | false
    }
}
