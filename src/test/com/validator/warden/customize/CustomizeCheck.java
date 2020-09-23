package com.validator.warden.customize;

import java.util.Arrays;
import java.util.List;

import com.validator.warden.core.domain.WdContext;


/**
 * @author DandyLuo
 */
public class CustomizeCheck {

    /**
     * 年龄是否合法
     */
    public boolean ageValid(final Integer age) {
        if(null == age){
            return false;
        }
        if (age >= 0 && age < 200) {
            return true;
        }

        return false;
    }

    /**
     * 名称是否合法
     */
    private boolean nameValid(final String name) {
        if(null == name){
            return false;
        }
        final List<String> blackList = Arrays.asList("women", "haode");
        if (blackList.contains(name)) {
            return false;
        }
        return true;
    }

    /**
     * 地址匹配
     */
    private boolean addressInvalid(final String address){
        if(null == address){
            return true;
        }
        final List<String> blackList = Arrays.asList("beijing", "hangzhou");
        if (blackList.contains(address)) {
            return true;
        }
        return false;
    }

    /**
     * 能够传递核查的对象，对于对象中的一些属性可以进行系统内部的配置
     *
     * mRatio + nRatio < 1.0
     */
    private boolean ratioJudge(final CustomizeEntity customizeEntity, final Float nRatio) {
        if(null == nRatio || null == customizeEntity){
            return false;
        }
        return nRatio + customizeEntity.getMRatio() < 10.0f;
    }

    /**
     * 两个函数
     */
    private boolean twoParam(final String funName, final WdContext context) {
        if (funName.equals("hello")){
            context.append("匹配上字段'hello'");
           return true;
        }
        context.append("没有匹配上字段'hello'");
        return false;
    }

    /**
     * 三个函数
     */
    private boolean threeParam(final String temK, final WdContext context) {
        if (temK.equals("hello") || temK.equals("word")){
            context.append("匹配上字段'hello'和'word'");
            return true;
        }
        context.append("没有匹配上字段'hello'和'word'");
        return false;
    }

    private boolean fieldErrMsgMatch(final String fieldErrMsg, final WdContext mkContext) {
        if (fieldErrMsg.contains("mock")) {
            mkContext.setLastErrMsg("当前的值命中黑名单");
            return true;
        } else {
            mkContext.setLastErrMsg("当前的值不符合需求");
            return false;
        }
    }

    private boolean fieldErrMsgMatch2(final String fieldErrMsg, final WdContext mkContext) {
        if (fieldErrMsg.contains("mock")) {
            mkContext.setLastErrMsg("当前的值命中黑名单");
            return true;
        } else {
            mkContext.setLastErrMsg("当前的值不符合需求");
            return false;
        }
    }
}
