/*
 * Copyright 2019 ViiSE.
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

package ru.viise.lightsearch.message.type;

import org.json.simple.JSONObject;

import ru.viise.lightsearch.cmd.ClientCommandContentEnum;
import ru.viise.lightsearch.cmd.ClientCommandEnum;
import ru.viise.lightsearch.data.CommandAuthorizationDTO;

public class MessageAuthorizationJSONDefaultImpl implements MessageAuthorization {

    private final String CONNECT  = ClientCommandEnum.CONNECT.stringValue();
    private final String COMMAND  = ClientCommandContentEnum.COMMAND.stringValue();
    private final String IMEI     = ClientCommandContentEnum.IMEI.stringValue();
    private final String IP       = ClientCommandContentEnum.IP.stringValue();
    private final String OS       = ClientCommandContentEnum.OS.stringValue();
    private final String MODEL    = ClientCommandContentEnum.MODEL.stringValue();
    private final String USERNAME = ClientCommandContentEnum.USERNAME.stringValue();
    private final String PASSWORD = ClientCommandContentEnum.PASSWORD.stringValue();

    private final CommandAuthorizationDTO cmdAuthDTO;

    public MessageAuthorizationJSONDefaultImpl(CommandAuthorizationDTO cmdAuthDTO) {
        this.cmdAuthDTO = cmdAuthDTO;
    }

    @Override
    public String message() {
        JSONObject msgAuthObj = new JSONObject();
        msgAuthObj.put(COMMAND, CONNECT);
        msgAuthObj.put(IMEI, cmdAuthDTO.IMEI());
        msgAuthObj.put(IP, cmdAuthDTO.ip());
        msgAuthObj.put(OS, cmdAuthDTO.os());
        msgAuthObj.put(MODEL, cmdAuthDTO.model());
        msgAuthObj.put(USERNAME, cmdAuthDTO.username());
        msgAuthObj.put(PASSWORD, cmdAuthDTO.password());
        return msgAuthObj.toJSONString();
    }
}
