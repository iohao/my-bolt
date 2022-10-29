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
package com.my.bolt.message;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

/**
 * @author 渔民小镇
 * @date 2022-10-27
 */
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserIdRequest implements Serializable {
    private static final long serialVersionUID = -7385687951893601229L;
    /** userId */
    long userId;
    /** 一般指用户的 channelId （来源于对外服的 user channel） */
    String userChannelId;
}
