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
