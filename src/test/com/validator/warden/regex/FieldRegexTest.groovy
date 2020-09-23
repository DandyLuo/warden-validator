package com.validator.warden.regex

import com.validator.warden.WardenValidator
import com.validator.warden.core.domain.CheckResult
import org.junit.Assert
import spock.lang.Specification

/**
 * @author DandyLuo
 */
class FieldRegexTest extends Specification {

    def regexTest() {
        given:
        RegexEntity entity = new RegexEntity().setRegexValid(valid).setRegexInValid(invalid)

        expect:
        CheckResult actResult = WardenValidator.validate(entity)
        Boolean act = actResult.isSuccess();
        Assert.assertEquals(result, act)
        if (!act) {
            println WardenValidator.errMsgChain;
        }

        where:
        valid       | invalid   | result
        "adfs12312" | "3312312" | false
        "asdf"      | "asf"     | false
        "3312312"   | "sdf"     | true
        "3312312"   | "3312312" | false
        null   | "3312312" | false
    }
}
