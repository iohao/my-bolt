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
package com.my.bolt.client;

import com.alipay.remoting.BoltClient;
import com.alipay.remoting.config.Configs;
import com.alipay.remoting.exception.RemotingException;
import com.alipay.remoting.rpc.RpcClient;
import com.alipay.remoting.rpc.protocol.UserProcessor;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.function.Supplier;

/**
 * @author 渔民小镇
 * @date 2022-10-27
 */
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MyBoltClient {
    BoltClient boltClient;
    String address = "127.0.0.1:50056";
    int timeoutMillis = 5 * 1000;

    List<Supplier<UserProcessor<?>>> processorList;

    public void registerUserProcessor(UserProcessor<?> userProcessor) {
        this.boltClient.registerUserProcessor(userProcessor);
    }

    public MyBoltClient() {
        // 开启 bolt 重连, 通过系统属性来开和关，如果一个进程有多个 RpcClient，则同时生效
        System.setProperty(Configs.CONN_MONITOR_SWITCH, "true");
        System.setProperty(Configs.CONN_RECONNECT_SWITCH, "true");

        this.boltClient = new RpcClient();
    }

    public void startup() {
        this.boltClient.startup();
    }

    public Object invokeSync(Object request)  {
        try {
            return boltClient.invokeSync(address, request, timeoutMillis);
        } catch (RemotingException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void oneway(Object request) {
        try {
            this.boltClient.oneway(address,request);
        } catch (RemotingException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
