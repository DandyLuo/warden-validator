/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.validator.warden.match.manager;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.validator.warden.annotation.Matcher;
import com.validator.warden.core.domain.WdContext;
import com.validator.warden.match.ConditionMatch;
import com.validator.warden.match.CustomizeMatch;
import com.validator.warden.match.EnumTypeMatch;
import com.validator.warden.match.Match;
import com.validator.warden.match.ModelMatch;
import com.validator.warden.match.NotBlankMatch;
import com.validator.warden.match.NotNullMatch;
import com.validator.warden.match.RangeMatch;
import com.validator.warden.match.RegexMatch;
import com.validator.warden.match.TypeMatch;
import com.validator.warden.match.ValueMath;
import com.validator.warden.match.factory.MatcherFactory;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 属性匹配管理器
 *
 * @author DandyLuo
 */
@Getter
@Setter
@Accessors(chain = true)
public class FieldMatchManager {

    /**
     * 属性名字
     */
    private String name;

    /**
     * 匹配器列表
     */
    private List<Match> matchList = new ArrayList<>();

    /**
     * 拦截后的自定义描述
     */
    private String errMsg;

    /**
     * 属性核查禁用标示，对应{@link com.validator.warden.annotation.Matcher#disable()}
     */
    private Boolean disable;

    /**
     * 属性匹配匹配器
     *
     * @param object 待校验的属性的对象
     * @param value 待校验的数据，就是属性的值
     * @param context 核查上下文
     * @param whiteOrBlack 黑白名单标示：true:白名单，false:黑名单
     * @return true：匹配任何一个匹配器返回true，false：所有匹配器都没有匹配上
     */
    public Boolean match(final Object object, final Object value, final WdContext context, final Boolean whiteOrBlack) {
        final List<String> errMsgList = new ArrayList<>();
        for (final Match m : this.matchList) {
            if (null == m || m.isEmpty()) {
                continue;
            }

            if (m.match(object, this.name, value)) {
                if (!whiteOrBlack) {
                    context.append(m.getBlackMsg());
                    this.setLastErrMsg(context, m.getBlackMsg(), value);
                } else {
                    context.clear();
                }
                return true;
            } else {
                if(whiteOrBlack) {
                    errMsgList.add(m.getWhiteMsg());
                    this.setLastErrMsg(context, m.getWhiteMsg(), value);
                }
            }
        }

        if (whiteOrBlack) {
            context.append(errMsgList);
        }

        return false;
    }

    private void setLastErrMsg(final WdContext context, final String sysErrMsg, final Object value) {
        if (null != sysErrMsg) {
            if (!"".equals(this.errMsg)) {
                if (null == context.getLastErrMsg()) {
                    if(null != value) {
                        context.setLastErrMsg(this.errMsg.replaceAll("#current", value.toString()));
                    } else {
                        context.setLastErrMsg(this.errMsg.replaceAll("#current", "null"));
                    }
                }
            } else {
                if (null == context.getLastErrMsg()) {
                    context.setLastErrMsg(sysErrMsg);
                }
            }
        }
    }

    /**
     * 判断是否有匹配器不空，如果有任何一个匹配器不空，则可以启动属性判决
     *
     * @return true：条件为空，false：条件不空
     */
    public Boolean isEmpty() {
        if (this.disable) {
            return true;
        }

        return this.matchList.stream().allMatch(Match::isEmpty);
    }

    @SuppressWarnings("all")
    public static FieldMatchManager buildFromValid(Field field, Matcher validCheck, WdContext context) {
        return new FieldMatchManager().setName(field.getName())
            .addMatcher(TypeMatch.build(field, validCheck.type()))
            .addMatcher(ValueMath.build(field, validCheck.value()))
            .addMatcher(MatcherFactory.build(ModelMatch.class, validCheck.model()))
            .addMatcher(MatcherFactory.build(EnumTypeMatch.class, validCheck.enumType()))
            .addMatcher(MatcherFactory.build(RangeMatch.class, validCheck.range()))
            .addMatcher(ConditionMatch.build(field, validCheck.condition()))
            .addMatcher(MatcherFactory.build(RegexMatch.class, validCheck.regex()))
            .addMatcher(CustomizeMatch.build(validCheck.customize(), context))
            .addMatcher(NotBlankMatch.build(field, validCheck.notBlank()))
            .addMatcher(MatcherFactory.build(NotNullMatch.class, validCheck.notNull()))
            .setErrMsg(validCheck.errMsg())
            .setDisable(validCheck.disable());
    }

    private FieldMatchManager addMatcher(final Match match) {
        if (null != match) {
            this.matchList.add(match);
        }
        return this;
    }
}
