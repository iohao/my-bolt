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
import com.alipay.remoting.exception.RemotingException;
import com.alipay.remoting.rpc.RpcServer;
import com.alipay.remoting.rpc.protocol.AsyncUserProcessor;
import com.my.bolt.MyBoltHelper;
import com.my.bolt.message.MyResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 渔民小镇
 * @date 2022-10-28
 */
@Slf4j
public class BoltServerMyResponseProcessor extends AsyncUserProcessor<MyResponse> {
    @Override
    public void handleRequest(BizContext bizCtx, AsyncContext asyncCtx, MyResponse myResponse) {

        if (MyBoltHelper.openLog) {
            log.info("步骤-4 ---- 请求到达 boltServer -- {}", myResponse);
        }

        try {

            RpcServer rpcServer = MyBoltHelper.rpcServer;
            rpcServer.oneway(MyBoltHelper.externalBoltClientAddress, myResponse);
        } catch (RemotingException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public String interest() {
        return MyResponse.class.getName();
    }
}
