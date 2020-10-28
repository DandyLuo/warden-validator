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

/**
 * @author DandyLuo
 */
public interface Match {

    /**
     * 判断是否匹配
     *
     * 只有isEmpty为false时候才会匹配
     *
     * @param object 属性所在的数据对象
     * @param value 待匹配的属性对应的值
     * @param name 属性名
     * @return true=匹配成功，false=匹配失败
     */
    boolean match(Object object, String name, Object value);

    /**
     * 判断当前匹配器是否为空
     *
     * @return true=空，false=非空
     */
    boolean isEmpty();

    /**
     * 判断当前匹配器是否为空
     *
     * @return true=非空，false=空
     */
    boolean isNotEmpty();

    /**
     * 白名单匹配不上的信息
     *
     * @return 没有匹配上的信息
     */
    String getWhiteMsg();

    /**
     * 黑名单匹配上的信息
     *
     * @return 匹配上的信息
     */
    String getBlackMsg();
}
