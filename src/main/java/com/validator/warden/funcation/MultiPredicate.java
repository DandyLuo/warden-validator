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
package com.validator.warden.funcation;

/**
 * 添加三个参数的断言函数，用于对{@link java.util.function.BiPredicate}的断言进行多大的扩充
 *
 * @author DandyLuo
 */
@FunctionalInterface
public interface MultiPredicate<T, K, U> {

    /**
     * 对给定的参数进行测量
     * @param t 第一个参数
     * @param k 第二个参数
     * @param u 第三个参数
     * @return 如果参数匹配则返回 {@code true} 否则返回{@code false}
     */
    boolean test(T t, K k, U u);
}
