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
package com.validator.warden.core.domain;

import java.text.MessageFormat;
import java.util.List;

import com.validator.warden.util.CollectionUtil;

/**
 * @author DandyLuo
 */
public class WdContext {

    /**
     * 错误信息链
     */
    private final ThreadLocal<StringBuilder> errMsgChain;
    /**
     * 最后的那个错误信息
     */
    private final ThreadLocal<String> theLastErrMsg;

    public WdContext() {
        this.errMsgChain = new ThreadLocal<>();
        this.theLastErrMsg = new ThreadLocal<>();
    }

    public String getErrMsgChain() {
        return this.errMsgChain.get().toString();
    }

    public void append(final String errMsgStr, final Object... keys) {
        if(null == errMsgStr){
            return;
        }
        this.errMsgChain.get().append("-->").append(MessageFormat.format(errMsgStr, keys));
    }

    public void append(final String errMsgStr) {
        if(null == errMsgStr){
            return;
        }
        this.errMsgChain.get().append("-->").append(errMsgStr);
    }

    public void append(final List<String> errMsgList) {
        if (CollectionUtil.isEmpty(errMsgList)) {
            return;
        }

        this.append(String.join("，而且", errMsgList));
    }

    public void setLastErrMsg(final String errMsg) {
        if(null == errMsg){
            return;
        }
        this.theLastErrMsg.set(errMsg);
    }

    public String getLastErrMsg() {
        return this.theLastErrMsg.get();
    }

    public void clear() {
        this.errMsgChain.remove();
        this.theLastErrMsg.remove();
        this.errMsgChain.set(new StringBuilder().append("数据校验失败："));
    }
}
