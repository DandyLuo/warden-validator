package com.validator.warden.condition


import com.validator.warden.WardenValidator
import com.validator.warden.core.domain.CheckResult
import org.junit.Assert
import spock.lang.Specification

/**
 * @author DandyLuo
 */
@SuppressWarnings("all")
class ConditionTest extends Specification {

    /**
     * 测试基本表达式
     * @return
     */
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

    /**
     * 测试java表达式
     * @return
     */
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

    /**
     * 测试java数学函数表达式
     * @return
     */
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

    /**
     * 测试复杂逻辑
     * @return
     */
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

    /**
     * 集合的配置处理
     * @return
     */
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

    /**
     * 至少一个为非空
     * @return
     */
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
