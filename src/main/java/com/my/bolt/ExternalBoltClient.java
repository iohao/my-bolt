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
import com.my.bolt.message.MyRequest;
import com.my.bolt.message.RegisterRequest;
import com.my.bolt.processor.ExternalClientMyResponseProcessor;
import com.my.bolt.processor.ExternalClientUserIdRequestProcessor;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.LongAdder;

/**
 * @author 渔民小镇
 * @date 2022-10-27
 */
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExternalBoltClient {
    public static String name = "ExternalBoltClient";
    MyBoltClient myBoltClient = new MyBoltClient();
    LongAdder no = new LongAdder();

    public void startup() {
        // 注册处理器
        myBoltClient.registerUserProcessor(new ExternalClientMyResponseProcessor());
        myBoltClient.registerUserProcessor(new ExternalClientUserIdRequestProcessor());

        // 启动
        myBoltClient.startup();

        this.registerToBoltServer();
    }

    public void requestInternalBoltClient(int loop) {
        /*
         * 步骤 1
         * external 向 internal 发送请求
         *
         * 请求由 internal 的 InternalMyRequestClientProcessor 处理器来处理
         */

        for (int i = 0; i < loop; i++) {

            this.no.increment();

            MyRequest myRequest = new MyRequest();
            myRequest.setNo(this.no.longValue());

            if (MyBoltHelper.openLog) {
                log.info("");
                log.info("开始步骤-1 ---- external 向 internal 发送请求 --- {}", myRequest);
            }

            this.myBoltClient.oneway(myRequest);
        }

    }

    private void registerToBoltServer() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setBoltClientName(name);
        this.myBoltClient.oneway(registerRequest);
    }
}
