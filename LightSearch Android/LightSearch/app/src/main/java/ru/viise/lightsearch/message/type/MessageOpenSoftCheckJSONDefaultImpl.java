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
import ru.viise.lightsearch.data.CommandOpenSoftCheckDTO;

public class MessageOpenSoftCheckJSONDefaultImpl implements MessageOpenSoftCheck {

    private final String OPEN_SOFT_CHECK = ClientCommandEnum.OPEN_SOFT_CHECK.stringValue();
    private final String COMMAND         = ClientCommandContentEnum.COMMAND.stringValue();
    private final String IMEI_FIELD      = ClientCommandContentEnum.IMEI.stringValue();
    private final String USER_IDENT      = ClientCommandContentEnum.USER_IDENT.stringValue();
    private final String CARD_CODE       = ClientCommandContentEnum.CARD_CODE.stringValue();

    private final String IMEI;
    private final CommandOpenSoftCheckDTO cmdOSCDTO;

    public MessageOpenSoftCheckJSONDefaultImpl(String IMEI, CommandOpenSoftCheckDTO cmdOSCDTO) {
        this.IMEI = IMEI;
        this.cmdOSCDTO = cmdOSCDTO;
    }

    @SuppressWarnings("unchecked")
    @Override
    public String message() {
            JSONObject msgOSCObj = new JSONObject();
            msgOSCObj.put(COMMAND, OPEN_SOFT_CHECK);
            msgOSCObj.put(IMEI_FIELD, IMEI);
            msgOSCObj.put(USER_IDENT, cmdOSCDTO.userIdentifier());
            msgOSCObj.put(CARD_CODE, cmdOSCDTO.cardCode());
        return msgOSCObj.toJSONString();
    }
}
