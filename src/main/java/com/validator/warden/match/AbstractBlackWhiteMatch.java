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
