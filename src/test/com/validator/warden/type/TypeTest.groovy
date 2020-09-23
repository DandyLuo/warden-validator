package com.validator.warden.type

import com.validator.warden.WardenValidator
import com.validator.warden.core.domain.CheckResult
import com.validator.warden.core.domain.WdConstant
import org.junit.Assert
import spock.lang.Specification

/**
 * @author luoruihua* @since 2020/9/23
 */
class TypeTest extends Specification {

    def primaryTypeTest() {
        given:
        TypeEntity entity = new TypeEntity().setData(intData)

        expect:
        CheckResult actResult = WardenValidator.validate(WdConstant.DEFAULT_GROUP, entity, "data")
        boolean act = actResult.isSuccess();
        if (!act) {
            println WardenValidator.getErrMsgChain()
        }
        Assert.assertEquals(result, act)

        where:
        intData | result
        12      | true
        null    | false
    }

    def stringTest() {
        given:
        TypeEntity entity = new TypeEntity().setName(name)

        expect:
        CheckResult actResult = WardenValidator.validate(WdConstant.DEFAULT_GROUP, entity, "name")
        boolean act = actResult.isSuccess();
        if (!act) {
            println WardenValidator.getErrMsgChain()
        }
        Assert.assertEquals(result, act)

        where:
        name   | result
        'asd'  | true
        null | false
    }

    def objTest() {
        given:
        TypeEntity entity = new TypeEntity().setObj(obj)

        expect:
        CheckResult actResult = WardenValidator.validate(WdConstant.DEFAULT_GROUP, entity, "obj")
        boolean act = actResult.isSuccess();
        if (!act) {
            println WardenValidator.getErrMsgChain()
        }
        Assert.assertEquals(result, act)

        where:
        obj    | result
        'c'    | false
        "abad" | false
        1232   | true
        1232l  | false
        1232f  | true
        12.0f  | true
        -12    | true
    }
}
