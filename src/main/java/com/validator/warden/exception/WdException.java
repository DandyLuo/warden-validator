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
package com.validator.warden.exception;

/**
 * @author DandyLuo
 */
public class WdException extends RuntimeException {

    private static final String PRE = "核查异常：";
    private static final long serialVersionUID = -6288726404418467941L;

    public WdException(final String message) {
        super(PRE + message);
    }

    public WdException(final String message, final Throwable e) {
        super(PRE + message, e);
    }

    public WdException(final Throwable e) {
        super(e);
    }
}
