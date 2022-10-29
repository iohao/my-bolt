/*
 * # iohao.com . 渔民小镇
 * Copyright (C) 2021 - 2022 double joker （262610965@qq.com） . All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.my.bolt;

import com.alipay.remoting.rpc.RpcServer;
import com.my.bolt.client.MyBoltClient;
import lombok.experimental.UtilityClass;

import java.util.concurrent.atomic.LongAdder;

/**
 * @author 渔民小镇
 * @date 2022-10-27
 */
@UtilityClass
public class MyBoltHelper {
    public int timeoutMillis = 5 * 1000;

    public String externalBoltClientAddress;

    public String internalBoltClientAddress;
    public MyBoltClient internalBoltClient;

    public RpcServer rpcServer;
    /** true 开启请求流程信息的日志 */
    public boolean openLog = false;
    /** 接收到的次数统计 */
    public LongAdder countRequest = new LongAdder();
}
