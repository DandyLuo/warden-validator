package com.validator.warden.range.collection

import com.validator.warden.WardenValidator
import com.validator.warden.core.domain.CheckResult
import org.junit.Assert
import spock.lang.Specification

/**
 * @author DandyLuo
 */
class CollectionRangeTest extends Specification {

    /**
     * 集合类型的测试1
     * @return
     */
    def collectionTest1() {
        given:
        CollectionSizeEntityB b1 = new CollectionSizeEntityB().setBField(b11)
        CollectionSizeEntityB b2 = new CollectionSizeEntityB().setBField(b12)
        List<CollectionSizeEntityB> bList = Arrays.asList(b1, b2);
        CollectionSizeEntityA range = new CollectionSizeEntityA().setBList(bList)

        expect:
        CheckResult checkResult = WardenValidator.validate(range)
        Boolean act = checkResult.isSuccess();
        Assert.assertEquals(result, act)
        if (!act) {
            println WardenValidator.errMsgChain;
        }

        where:
        b11 | b12 | result
        5   | 20  | false
        10  | 20  | false
        20  | 20  | true
        30  | 20  | false
        null  | 20  | false
    }

    /**
     * 集合类型的测试2
     * @return
     */
    def collectionTest2() {
        given:
        CollectionSizeEntityB b1 = new CollectionSizeEntityB().setBField(b11)
        CollectionSizeEntityB b2 = new CollectionSizeEntityB().setBField(b12)
        CollectionSizeEntityB b3 = new CollectionSizeEntityB().setBField(b13)
        List<CollectionSizeEntityB> bList = Arrays.asList(b1, b2, b3);
        CollectionSizeEntityA range = new CollectionSizeEntityA().setBList(bList)

        expect:
        CheckResult checkResult = WardenValidator.validate(range)
        Boolean act = checkResult.isSuccess();
        Assert.assertEquals(result, act)
        if (!act) {
            println WardenValidator.errMsgChain;
        }

        where:
        b11 | b12 | b13 | result
        20   | 20  | 20  | false
    }

    /**
     * 集合类型的测试3
     * @return
     */
    def collectionTest3() {
        given:
        CollectionSizeEntityB b1 = new CollectionSizeEntityB().setBField(b11)
        CollectionSizeEntityB b2 = new CollectionSizeEntityB().setBField(b12)
        List<CollectionSizeEntityB> bList = Arrays.asList(b1, b2);
        CollectionSizeEntityA range = new CollectionSizeEntityA().setBList(bList)

        expect:
        CheckResult checkResult = WardenValidator.validate(range)
        Boolean act = checkResult.isSuccess();
        Assert.assertEquals(result, act)
        if (!act) {
            println WardenValidator.errMsgChain;
        }

        where:
        b11 | b12 | b13 | result
        5   | 20  | 20  | false
    }
}
