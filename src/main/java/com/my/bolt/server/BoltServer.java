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
package com.my.bolt.server;

import com.alipay.remoting.rpc.RpcServer;
import com.my.bolt.MyBoltHelper;
import com.my.bolt.processor.BoltServerMyRequestProcessor;
import com.my.bolt.processor.BoltServerMyResponseProcessor;
import com.my.bolt.processor.BoltServerRegisterRequestProcessor;
import com.my.bolt.processor.BoltServerUserIdRequestSyncProcessor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 渔民小镇
 * @date 2022-10-27
 */
@Slf4j
public class BoltServer {

    RpcServer rpcServer;

    int port = 50056;

    public BoltServer() {
        this.rpcServer = new RpcServer(this.port, true);
    }

    public void startup() {

        this.rpcServer.registerUserProcessor(new BoltServerRegisterRequestProcessor());
        this.rpcServer.registerUserProcessor(new BoltServerMyRequestProcessor());
        this.rpcServer.registerUserProcessor(new BoltServerUserIdRequestSyncProcessor());
        this.rpcServer.registerUserProcessor(new BoltServerMyResponseProcessor());

        // 启动 bolt rpc
        this.rpcServer.startup();

        log.info("启动 bolt-server port: [{}]", this.port);
        MyBoltHelper.rpcServer = this.rpcServer;
    }

    public static void main(String[] args) {
        BoltServer boltServer = new BoltServer();
        boltServer.startup();
    }
}
