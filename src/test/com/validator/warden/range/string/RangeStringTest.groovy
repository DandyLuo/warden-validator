package com.validator.warden.range.string

import com.validator.warden.WardenValidator
import com.validator.warden.core.domain.CheckResult
import org.junit.Assert
import spock.lang.Specification

/**
 * @author DandyLuo
 */
class RangeStringTest extends Specification {

    /**
     * 长度测试
     * @return
     */
    def lengthTest() {
        given:
        StringRangeEntity1 range = new StringRangeEntity1().setData(data)

        expect:
        CheckResult checkResult = WardenValidator.validate(range)
        Boolean act = checkResult.isSuccess();
        Assert.assertEquals(result, act)
        if (!act) {
            println WardenValidator.errMsgChain;
        }

        where:
        data   | result
        "a"    | true
        'a'    | true
        "ab"   | true
        "abc"  | true
        "abcd" | false
    }
}
