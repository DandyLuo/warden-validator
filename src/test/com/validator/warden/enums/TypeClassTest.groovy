package com.validator.warden.enums

import com.validator.warden.WardenValidator
import com.validator.warden.core.domain.CheckResult
import org.junit.Assert
import spock.lang.Specification

/**
 * 泛型类型（字符类型）测试
 *
 * @author DandyLuo
 */
class TypeClassTest extends Specification {

    def "泛型类型（字符类型）测试"() {
        given:
        DataEntity dataEntity = new DataEntity().setName(name);
        TypeVariableEntity<DataEntity> entity = new TypeVariableEntity().setPageSize(pageSize).setData(dataEntity);

        expect:
        CheckResult act = WardenValidator.validate(entity)
        boolean actResult = act.isSuccess();
        if (!actResult) {
            println WardenValidator.getErrMsgChain()
        }
        Assert.assertEquals(result, actResult)

        where:
        pageSize | name | result
        0        | "a"  | true
        100      | "b"  | true
        200      | "a"  | false
        100      | "c"  | false
        null      | "a"  | true
    }

    def "泛型类型（字符类型）数组测试"() {
        given:
        List<DataEntity> dataEntityList = new ArrayList<>()
        dataEntityList.add(new DataEntity().setName(name1))
        dataEntityList.add(new DataEntity().setName(name2))

        TypeVariableEntity<DataEntity> entity = new TypeVariableEntity().setDataList(dataEntityList);

        expect:
        CheckResult act = WardenValidator.validate(entity)
        boolean actResult = act.isSuccess();
        if (!actResult) {
            println WardenValidator.getErrMsgChain()
        }
        Assert.assertEquals(result, actResult)

        where:
        name1 | name2 | result
        "a"   | "a"   | true
        "a"   | "b"   | true
        "a"   | "c"   | false
        "b"   | "b"   | true
        "c"   | "c"   | false
        "c"   | "a"   | false
    }

    def "<>符号测试"() {
        given:
        Map<String, DataEntity> dataEntityMap = new HashMap<>();
        dataEntityMap.put("a", new DataEntity().setName(name));
        ParameterizedTypeEntity entity = new ParameterizedTypeEntity().setDataEntityMap(dataEntityMap);

        expect:
        CheckResult act = WardenValidator.validate(entity)
        boolean actResult = act.isSuccess();
        if (!actResult) {
            println WardenValidator.getErrMsgChain()
        }
        Assert.assertEquals(result, actResult)

        where:
        name | result
        "a"  | true
        "b"  | true
        "c"  | false
        "d"  | false
    }

    def "通配符测试"() {
        given:
        Map<String, ChildDataEntity> dataEntityMap = new HashMap<>()
        dataEntityMap.put("a", new ChildDataEntity().setNameChild(name))
        WildcardTypeEntity entity = new WildcardTypeEntity().setDataMap(dataEntityMap)

        expect:
        CheckResult act = WardenValidator.validate(entity)
        boolean actResult = act.isSuccess();
        if (!actResult) {
            println WardenValidator.getErrMsgChain()
        }
        Assert.assertEquals(result, actResult)

        where:
        name | result
        "a"  | true
        "b"  | true
        "c"  | false
        "d"  | false
    }

    def "泛型数组测试"() {
        given:
        DataEntity[] dataEntities = new DataEntity[4];
        dataEntities[0] = new DataEntity().setName(name1)
        dataEntities[1] = new DataEntity().setName(name2)
        GenericArrayTypeEntity<DataEntity> entity = new GenericArrayTypeEntity().setDataArray(dataEntities)

        expect:
        CheckResult act = WardenValidator.validate(entity)
        boolean actResult = act.isSuccess();
        if (!actResult) {
            println WardenValidator.getErrMsgChain()
        }
        Assert.assertEquals(result, actResult)

        where:
        name1 | name2 | result
        "a"   | "a"   | true
        "a"   | "b"   | true
        "b"   | "a"   | true
        "b"   | "b"   | true
        "c"   | "b"   | false
        "c"   | "c"   | false
    }
}
