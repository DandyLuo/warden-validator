package com.validator.warden.customize

import com.validator.warden.WardenValidator
import com.validator.warden.core.domain.CheckResult
import com.validator.warden.core.domain.WdConstant
import org.junit.Assert
import spock.lang.Specification

/**
 * @author DandyLuo
 */
class CustomizeTest extends Specification {

    /**
     * 测试外部判断的调用
     */
    def customizeTest() {
        given:
        CustomizeEntity entity = new CustomizeEntity().setName(name).setAge(age).setAddress(address)

        expect:
        CheckResult checkResult = WardenValidator.validate(WdConstant.DEFAULT_GROUP, entity, "name", "age", "address");
        Boolean act = checkResult.isSuccess();
        Assert.assertEquals(result, act)
        if (!act) {
            println WardenValidator.errMsgChain;
        }

        where:
        name    | age  | address    | result
        "women" | 12   | "hangzhou" | false
        "haode" | 13   | "tianjin"  | false
        "b"     | -1   | "tianjin"  | false
        "b"     | 200  | "tianjin"  | false
        "c"     | 12   | "hangzhou" | false
        "c"     | 12   | "beijing"  | false
        "c"     | 12   | "tianjin"  | true
        "d"     | null | "tianjin"  | false
        null    | 32   | "tianjin"  | false
    }

    /**
     * 测试包含其他字段的校验
     */
    def includingOtherFiledTest() {
        given:
        CustomizeEntity entity = new CustomizeEntity().setMRatio(mRatio).setNRatio(nRatio)

        expect:
        CheckResult checkResult = WardenValidator.validate(WdConstant.DEFAULT_GROUP, entity, "mRatio")
        Boolean act = checkResult.isSuccess();
        Assert.assertEquals(result, act)
        if (!act) {
            println WardenValidator.errMsgChain;
        }

        where:
        mRatio | nRatio | result
        1f     | 1f     | true
        10f    | 1f     | false
    }

    /**
     *  测试上下文两个参数
     */
    def twoParamTest(){
        given:
        CustomizeEntity entity = new CustomizeEntity().setTwoPa(twoPa)

        expect:
        CheckResult checkResult = WardenValidator.validate(WdConstant.DEFAULT_GROUP, entity, "twoPa")
        Boolean act = checkResult.isSuccess();
        Assert.assertEquals(result, act)
        if (!act) {
            println WardenValidator.errMsgChain;
        }

        where:
        twoPa   | result
        "hello" | true
        "jo"    | false
        "ok"    | false
    }

    /**
     * 测试上下文三个参数
     * @return
     */
    def threeParamTest() {
        given:
        CustomizeEntity entity = new CustomizeEntity().setThreePa(threePa)

        expect:
        CheckResult checkResult = WardenValidator.validate(WdConstant.DEFAULT_GROUP, entity, "threePa")
        Boolean act = checkResult.isSuccess();
        Assert.assertEquals(result, act)
        if (!act) {
            println WardenValidator.errMsgChain;
        }

        where:
        threePa | result
        "hello" | true
        "word"  | true
        "ok"    | false
        "haode" | false
    }

    /**
     * 不匹配情况下的错误日志
     * @return
     */
    def errMsgTest() {
        given:
        CustomizeEntity entity = new CustomizeEntity().setFieldErrMsg(fieldErrMsg)

        expect:
        CheckResult checkResult = WardenValidator.validate(WdConstant.DEFAULT_GROUP, entity, "fieldErrMsg")
        Boolean act = checkResult.isSuccess();
        Assert.assertEquals(result, act)
        if (!act) {
            println WardenValidator.errMsgChain;
        }

        where:
        fieldErrMsg | result
        "mock1"     | true
        "mock2"     | true
        "mock3"     | true
        "asdf"      | false
    }

    /**
     * 不匹配情况下的错误日志2
     * @return
     */
    def errMsgTest2() {
        given:
        CustomizeEntity entity = new CustomizeEntity().setFieldErrMsg2(fieldErrMsg)

        expect:
        CheckResult checkResult = WardenValidator.validate(WdConstant.DEFAULT_GROUP, entity, "fieldErrMsg2")
        Boolean act = checkResult.isSuccess();
        Assert.assertEquals(result, act)
        if (!act) {
            println WardenValidator.errMsgChain;
        }

        where:
        fieldErrMsg | result
        "mock1"     | true
        "mock2"     | true
        "mock3"     | true
        "asdf"      | false
    }
}
