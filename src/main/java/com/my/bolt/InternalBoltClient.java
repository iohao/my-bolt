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

import com.my.bolt.client.MyBoltClient;
import com.my.bolt.processor.InternalClientMyRequestProcessor;
import com.my.bolt.message.RegisterRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 渔民小镇
 * @date 2022-10-27
 */
@Slf4j
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InternalBoltClient {
    public static String name = "InternalBoltClient";

    MyBoltClient myBoltClient = new MyBoltClient();

    public void startup() {
        // 注册处理器
        myBoltClient.registerUserProcessor(new InternalClientMyRequestProcessor());

        // 启动
        myBoltClient.startup();

        log.info("启动 InternalBoltClient");

        MyBoltHelper.internalBoltClient = this.myBoltClient;
        this.registerToBoltServer();
    }

    private void registerToBoltServer() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setBoltClientName(name);
        this.myBoltClient.oneway(registerRequest);
    }
}
