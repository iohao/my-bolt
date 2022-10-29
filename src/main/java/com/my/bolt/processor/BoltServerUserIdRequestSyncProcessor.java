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

import com.alipay.remoting.BizContext;
import com.alipay.remoting.exception.RemotingException;
import com.alipay.remoting.rpc.RpcServer;
import com.alipay.remoting.rpc.protocol.SyncUserProcessor;
import com.my.bolt.MyBoltHelper;
import com.my.bolt.message.UserIdRequest;
import com.my.bolt.message.UserIdResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 渔民小镇
 * @date 2022-10-27
 */
@Slf4j
public class BoltServerUserIdRequestSyncProcessor extends SyncUserProcessor<UserIdRequest> {

    @Override
    public Object handleRequest(BizContext bizCtx, UserIdRequest userIdRequest) {


        if (MyBoltHelper.openLog) {
            log.info("步骤-2 ---- 请求到达 boltServer -- {}", userIdRequest);
        }

        try {
            RpcServer rpcServer = MyBoltHelper.rpcServer;

            UserIdResponse userIdResponse = (UserIdResponse) rpcServer.invokeSync(
                    MyBoltHelper.externalBoltClientAddress,
                    userIdRequest,
                    MyBoltHelper.timeoutMillis);

            if (MyBoltHelper.openLog) {
                log.info("步骤-3 ---- 请求到达 boltServer -- {}", userIdResponse);
            }

            return userIdResponse;
        } catch (RemotingException | InterruptedException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public String interest() {
        return UserIdRequest.class.getName();
    }
}
