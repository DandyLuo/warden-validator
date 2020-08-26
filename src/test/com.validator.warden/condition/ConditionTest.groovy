package com.validator.warden.common

import com.validator.warden.condition.ConditionEntity1
import com.validator.warden.condition.ConditionEntity2
import com.validator.warden.condition.ConditionEntity3
import com.validator.warden.condition.ConditionEntity4
import com.validator.warden.condition.ConditionEntity5
import com.validator.warden.condition.ConditionEntity6
import com.validator.warden.core.domain.CheckResult
import org.junit.Assert
import spock.lang.Specification
import com.validator.warden.core.WardenValidator

/**
 * @author DandyLuo
 */
@SuppressWarnings("all")
class ConditionTest extends Specification {

    def baseExpressionTest() {
        given:
        ConditionEntity1 entity = new ConditionEntity1().setNum1(num1).setNum2(num2).setNum3(num3)

        expect:
        CheckResult checkResult = WardenValidator.validate(entity);
        def act = checkResult.isSuccess();
        Assert.assertEquals(result, act)
        if (!act) {
            println WardenValidator.errMsgChain
        }

        where:
        num1 | num2 | num3 | result
        91   | 10   | 31   | true
        90   | 10   | 31   | false
        81   | 20   | 31   | false
        91   | 10   | 30   | false
    }

    def javaExpressionTest() {
        given:
        ConditionEntity2 entity = new ConditionEntity2().setAge(age).setJudge(judge)

        expect:
        CheckResult checkResult = WardenValidator.validate(entity);
        def act = checkResult.isSuccess();
        Assert.assertEquals(result, act)
        if (!act) {
            println WardenValidator.errMsgChain
        }

        where:
        age | judge | result
        12  | true  | true
        12  | false | false
    }

    def javaMathematicalFuncationExpressionTest() {
        given:
        ConditionEntity3 entity = new ConditionEntity3().setNum1(num1).setNum2(num2).setNum3(num3)

        expect:
        CheckResult checkResult = WardenValidator.validate(entity);
        def act = checkResult.isSuccess();
        Assert.assertEquals(result, act)
        if (!act) {
            println WardenValidator.errMsgChain
        }

        where:
        num1 | num2 | num3 | result
        31   | 30   | 29   | true
        31   | 30   | 30   | false
        31   | 30   | 20   | true
        31   | 30   | 31   | false
    }

    def complexCaseTest() {
        given:
        ConditionEntity4 entity = new ConditionEntity4().setType(type).setName(name)

        expect:
        CheckResult checkResult = WardenValidator.validate(entity);
        def act = checkResult.isSuccess();
        Assert.assertEquals(result, act)
        if (!act) {
            println WardenValidator.errMsgChain
        }

        where:
        type | name | result
        0    | "a"  | true
        0    | "b"  | true
        1    | "b"  | true
        2    | "b"  | true
        2    | "c"  | false
        3    | "b"  | false
    }


    def collectionTest() {
        given:
        def nameList
        if(null != names){
            nameList = Arrays.asList(names.split(","));
        }
        ConditionEntity5 entity = new ConditionEntity5().setHandleType(type).setNameList(nameList)

        expect:
        CheckResult checkResult = WardenValidator.validate(entity);
        def act = checkResult.isSuccess();
        Assert.assertEquals(result, act)
        if (!act) {
            println WardenValidator.errMsgChain
        }

        where:
        type | names   | result
        0    | null    | true
        0    | "a,b,c" | false
        1    | "a,b,c" | true
        1    | null    | false
    }

    def atLeastOneNonNull() {
        given:
        ConditionEntity6 entity = new ConditionEntity6().setF1(f1).setF2(f2).setF3(f3)

        expect:
        CheckResult checkResult = WardenValidator.validate(entity);
        def act = checkResult.isSuccess();
        Assert.assertEquals(result, act)
        if (!act) {
            println WardenValidator.errMsgChain
        }

        where:
        f1   | f2   | f3   | result
        1L   | "a"  | 2    | true
        null | "a"  | 2    | true
        null | null | 2    | true
        null | null | null | false
    }
}
