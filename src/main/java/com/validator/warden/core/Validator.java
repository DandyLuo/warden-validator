package com.validator.warden.core;

import com.validator.warden.core.domain.CheckResult;
import com.validator.warden.core.domain.WdConstant;

/**
 * 定义校验器的行为
 * @author DandyLuo
 * @since 1.0.0
 */
public interface Validator {

    /**
     * 自定义的复杂类型校验，待核查类型校验不校验，核查失败抛异常
     *
     *
     * @param object 待核查对象
     * @return 检查结果
     * @since 1.0.0
     */
    public CheckResult validate(Object object);

    /**
     * 针对对象的某些属性进行核查
     *
     *
     * @param group    分组，为空则采用默认，为"_default_"，详{@link WdConstant#DEFAULT_GROUP}
     * @param object   待核查对象
     * @param fieldSet 待核查对象的多个属性名字
     * @return 检查结果
     * @since 1.0.0
     */
    public CheckResult validate(String group, Object object, String... fieldSet);
}
