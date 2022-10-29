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

import com.alipay.remoting.NamedThreadFactory;
import com.my.bolt.server.BoltServer;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author 渔民小镇
 * @date 2022-10-27
 */
@Slf4j
public class MainApplication {
    public static void main(String[] args) throws InterruptedException {
        // 启动 BoltServer
        BoltServer boltServer = new BoltServer();
        run(boltServer::startup);

        // 启动 internal bolt client
        InternalBoltClient internalBoltClient = new InternalBoltClient();
        run(internalBoltClient::startup);

        // 启动 external bolt client
        ExternalBoltClient externalBoltClient = new ExternalBoltClient();
        run(externalBoltClient::startup);

        // 睡眠，等待 bolt 启动完成
        TimeUnit.SECONDS.sleep(1);



        // 设置为 true，可以看见请求的信息流向流程
        MyBoltHelper.openLog = true;
        // 请求次数设置
        int loop = 1;
        /*
         * 开始步骤-1 ---- external 向 internal 发送请求
         */
        externalBoltClient.requestInternalBoltClient(loop);

        // 统计打印
        extractedPrint();
    }

    private static void extractedPrint() {
        ThreadFactory threadFactory = new NamedThreadFactory("d");
        int corePoolSize = 1;

        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(corePoolSize, threadFactory);
        scheduledExecutorService.scheduleAtFixedRate(() -> {

            LongAdder countRequest = MyBoltHelper.countRequest;
            log.info("接收到的次数统计 {}", countRequest);

        }, 2, 5, TimeUnit.SECONDS);
    }

    private static void run(Runnable runnable) {
        new Thread(runnable).start();
    }
}
