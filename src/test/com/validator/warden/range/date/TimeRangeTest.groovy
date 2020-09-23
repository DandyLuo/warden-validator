package com.validator.warden.range.date

import com.validator.warden.WardenValidator
import com.validator.warden.core.domain.CheckResult
import org.junit.Assert
import spock.lang.Specification

import java.time.LocalDateTime
import java.time.ZoneId

/**
 * @author DandyLuo
 */
class TimeRangeTest extends Specification {

    /**
     * 测试时间范围test1
     * @return
     */
    def dateRangTest1() {
        given:
        RangeTimeEntity1 range = new RangeTimeEntity1().setDate(date).setTime(time).setLength(length);

        expect:
        CheckResult checkResult = WardenValidator.validate("test1", range)
        Boolean act = checkResult.isSuccess();
        Assert.assertEquals(result, act)
        if (!act) {
            println WardenValidator.errMsgChain;
        }

        where:
        date                             | time                                       | length | result
        getDate(2019, 7, 14, 00, 00, 00) | getDate(2019, 8, 4, 00, 00, 00).getTime()  | 150    | true
        getDate(2019, 7, 24, 00, 00, 00) | getDate(2019, 8, 4, 00, 00, 00).getTime()  | 150    | false
        getDate(2019, 7, 14, 00, 00, 00) | getDate(2019, 8, 14, 00, 00, 00).getTime() | 150    | false
        getDate(2019, 7, 14, 00, 00, 00) | getDate(2019, 8, 4, 00, 00, 00).getTime()  | 400    | false
        null | getDate(2019, 8, 4, 00, 00, 00).getTime()  | 400    | false
    }

    /**
     * 测试时间范围test2
     * @return
     */
    def dateRangTest2() {
        given:
        RangeTimeEntity1 range = new RangeTimeEntity1().setDate(date).setTime(time)

        expect:
        CheckResult checkResult = WardenValidator.validate("test2", range)
        Boolean act = checkResult.isSuccess();
        Assert.assertEquals(result, act)
        if (!act) {
            println WardenValidator.errMsgChain;
        }

        where:
        date                             | time                                       | result
        getDate(2099, 8, 20, 00, 00, 00) | getDate(2099, 8, 10, 00, 00, 00).getTime() | true
        getDate(2009, 8, 24, 00, 00, 00) | getDate(2099, 8, 10, 00, 00, 00).getTime() | false
        getDate(2099, 8, 20, 00, 00, 00) | getDate(2009, 8, 14, 00, 00, 00).getTime() | false
        getDate(2009, 8, 20, 00, 00, 00) | getDate(2009, 8, 14, 00, 00, 00).getTime() | false
    }

    /**
     * 测试时间范围test3
     * @return
     */
    def dateRangTest3() {
        given:
        RangeTimeEntity1 range = new RangeTimeEntity1().setDate(date).setTime(time)

        expect:
        CheckResult checkResult = WardenValidator.validate("test3", range)
        Boolean act = checkResult.isSuccess();
        Assert.assertEquals(result, act)
        if (!act) {
            println WardenValidator.errMsgChain;
        }

        where:
        date                             | time                                       | result
        getDate(2008, 8, 20, 00, 00, 00) | getDate(2009, 7, 10, 00, 00, 00).getTime() | true
        getDate(2099, 8, 24, 00, 00, 00) | getDate(2009, 8, 10, 00, 00, 00).getTime() | false
        getDate(2009, 8, 20, 00, 00, 00) | getDate(2099, 8, 14, 00, 00, 00).getTime() | false
        getDate(2099, 8, 20, 00, 00, 00) | getDate(2099, 8, 14, 00, 00, 00).getTime() | false
    }

    /**
     * 测试时间范围test4 future
     * @return
     */
    def dateRangTest4() {
        given:
        RangeTimeEntity1 range = new RangeTimeEntity1().setDate(date).setTime(time)

        expect:
        CheckResult checkResult = WardenValidator.validate("test4", range)
        Boolean act = checkResult.isSuccess();
        Assert.assertEquals(result, act)
        if (!act) {
            println WardenValidator.errMsgChain;
        }

        where:
        date                             | time                                       | result
        getDate(2099, 8, 20, 00, 00, 00) | getDate(2099, 9, 10, 00, 00, 00).getTime() | true
        getDate(2009, 8, 4, 12, 56, 00)  | getDate(2099, 8, 14, 11, 56, 00).getTime() | false
        getDate(2099, 8, 4, 11, 56, 00)  | getDate(2009, 8, 4, 11, 56, 00).getTime()  | false
        getDate(2009, 8, 24, 00, 00, 00) | getDate(2009, 7, 10, 00, 00, 00).getTime() | false
    }

    /**
     * 测试时间范围test5 future
     * @return
     */
    def dateRangTest5() {
        given:
        RangeTimeEntity1 range = new RangeTimeEntity1().setTime(time)

        expect:
        CheckResult checkResult = WardenValidator.validate("test5", range)
        Boolean act = checkResult.isSuccess();
        Assert.assertEquals(result, act)
        if (!act) {
            println WardenValidator.errMsgChain;
        }

        where:
        time  | result
        200   | true
        100   | true
        99    | false
        30001 | false
    }

    /**
     * 测试时间范围test6 ('xxx', now)
     * @return
     */
    def dateRangTest6() {
        given:
        RangeTimeEntity1 range = new RangeTimeEntity1().setTime(time)

        expect:
        CheckResult checkResult = WardenValidator.validate("test6", range)
        Boolean act = checkResult.isSuccess();
        Assert.assertEquals(result, act)
        if (!act) {
            println WardenValidator.errMsgChain;
        }

        where:
        time                                       | result
        getDate(2019, 7, 20, 00, 00, 00).getTime() | true
        getDate(2019, 7, 24, 2, 7, 00).getTime()   | true
        getDate(2119, 9, 4, 12, 06, 00).getTime()  | false
    }

    /**
     * 测试时间范围test7 (null, now)
     * @return
     */
    def dateRangTest7() {
        given:
        RangeTimeEntity1 range = new RangeTimeEntity1().setTime(time)

        expect:
        CheckResult checkResult = WardenValidator.validate("test7", range)
        Boolean act = checkResult.isSuccess();
        Assert.assertEquals(result, act)
        if (!act) {
            println WardenValidator.errMsgChain;
        }

        where:
        time                                       | result
        getDate(2019, 7, 20, 00, 00, 00).getTime() | true
        getDate(2019, 7, 24, 2, 7, 00).getTime()   | true
        getDate(2119, 9, 4, 12, 06, 00).getTime()  | false
    }

    /**
     * 测试时间范围test8 ('null', 'now')
     * @return
     */
    def dateRangTest8() {
        given:
        RangeTimeEntity1 range = new RangeTimeEntity1().setTime(time)

        expect:
        CheckResult checkResult = WardenValidator.validate("test8", range)
        Boolean act = checkResult.isSuccess();
        Assert.assertEquals(result, act)
        if (!act) {
            println WardenValidator.errMsgChain;
        }

        where:
        time                                       | result
        getDate(2019, 7, 20, 00, 00, 00).getTime() | true
        getDate(2019, 7, 24, 2, 7, 00).getTime()   | true
        getDate(2119, 9, 4, 12, 06, 00).getTime()  | false
    }

    /**
     * 测试时间范围test9 ('null', 'now')
     * @return
     */
    def dateRangTest9() {
        given:
        RangeTimeEntity1 range = new RangeTimeEntity1().setTime(time)

        expect:
        CheckResult checkResult = WardenValidator.validate("test9", range)
        Boolean act = checkResult.isSuccess();
        Assert.assertEquals(result, act)
        if (!act) {
            println WardenValidator.errMsgChain;
        }

        where:
        time                                       | result
        getDate(2019, 8, 01, 00, 00, 00).getTime() | true
        getDate(2019, 7, 31, 2, 7, 00).getTime()   | false
        getDate(2019, 8, 01, 00, 00, 00).getTime()  | true
    }



    def getDate(def year, def month, def day, def hour, def minute, def second) {
        return Date.from(LocalDateTime.of(year, month, day, hour, minute, second).atZone(ZoneId.systemDefault()).toInstant())
    }
}
