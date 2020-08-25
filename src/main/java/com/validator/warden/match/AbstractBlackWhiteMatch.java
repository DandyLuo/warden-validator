package com.validator.warden.match;

import java.text.MessageFormat;

/**
 * @author DandyLuo
 */
public abstract class AbstractBlackWhiteMatch implements Match {

    private String blackMsg;
    private String whiteMsg;

    void setBlackMsg(final String pattern, final Object... arguments){
        this.blackMsg = MessageFormat.format(pattern, arguments);
    }

    void setWhiteMsg(final String pattern, final Object... arguments){
        this.whiteMsg = MessageFormat.format(pattern, arguments);
    }

    @Override
    public boolean isNotEmpty(){
        return !this.isEmpty();
    }

    @Override
    public String getBlackMsg(){
        return this.blackMsg;
    }

    @Override
    public String getWhiteMsg(){
        return this.whiteMsg;
    }
}
