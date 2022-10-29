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
package com.my.bolt.processor;

import com.alipay.remoting.AsyncContext;
import com.alipay.remoting.BizContext;
import com.alipay.remoting.rpc.protocol.AsyncUserProcessor;
import com.my.bolt.ExternalBoltClient;
import com.my.bolt.InternalBoltClient;
import com.my.bolt.MyBoltHelper;
import com.my.bolt.message.RegisterRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 渔民小镇
 * @date 2022-10-28
 */
@Slf4j
public class BoltServerRegisterRequestProcessor extends AsyncUserProcessor<RegisterRequest> {
    @Override
    public void handleRequest(BizContext bizCtx, AsyncContext asyncCtx, RegisterRequest request) {
        String remoteAddress = bizCtx.getRemoteAddress();

        String boltClientName = request.getBoltClientName();

        if (ExternalBoltClient.name.equals(boltClientName)) {
            MyBoltHelper.externalBoltClientAddress = remoteAddress;
        }

        if (InternalBoltClient.name.equals(boltClientName)) {
            MyBoltHelper.internalBoltClientAddress = remoteAddress;
        }

        log.info("注册到 bolt server {} - {}", boltClientName, remoteAddress);
    }

    @Override
    public String interest() {
        return RegisterRequest.class.getName();
    }
}
