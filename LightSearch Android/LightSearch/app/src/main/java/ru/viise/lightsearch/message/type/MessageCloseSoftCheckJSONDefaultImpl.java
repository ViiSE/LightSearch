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
import ru.viise.lightsearch.data.CommandCloseSoftCheckDTO;

public class MessageCloseSoftCheckJSONDefaultImpl implements MessageCloseSoftCheck {

    private final String CLOSE_SOFT_CHECK = ClientCommandEnum.CLOSE_SOFT_CHECK.stringValue();
    private final String COMMAND          = ClientCommandContentEnum.COMMAND.stringValue();
    private final String IMEI_FIELD       = ClientCommandContentEnum.IMEI.stringValue();
    private final String USER_IDENT       = ClientCommandContentEnum.USER_IDENT.stringValue();
    private final String CARD_CODE        = ClientCommandContentEnum.CARD_CODE.stringValue();
    private final String DELIVERY         = ClientCommandContentEnum.DELIVERY.stringValue();

    // FIXME: 20.05.2019 Уточнить, нужно ли передавать время.
    private final String DATE_TIME = "date_time";

    private final String IMEI;
    private final String delivery;
    private final CommandCloseSoftCheckDTO cmdCSCDTO;

    public MessageCloseSoftCheckJSONDefaultImpl(String IMEI, CommandCloseSoftCheckDTO cmdCSCDTO) {
        this.IMEI = IMEI;
        this.cmdCSCDTO = cmdCSCDTO;
        this.delivery = this.cmdCSCDTO.deliveryType().stringCommandValue();
    }

    @Override
    public String message() {
        JSONObject msgOSCObj = new JSONObject();
        msgOSCObj.put(COMMAND, CLOSE_SOFT_CHECK);
        msgOSCObj.put(IMEI_FIELD, IMEI);
        msgOSCObj.put(USER_IDENT, cmdCSCDTO.userIdentifier());
        msgOSCObj.put(CARD_CODE, cmdCSCDTO.cardCode());
        msgOSCObj.put(DELIVERY, delivery);
        msgOSCObj.put(DATE_TIME, "\"сегодня\"");
        return msgOSCObj.toJSONString();
    }
}
