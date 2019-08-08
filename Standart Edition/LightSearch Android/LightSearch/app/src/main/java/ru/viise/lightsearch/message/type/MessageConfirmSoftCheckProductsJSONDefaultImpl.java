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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import ru.viise.lightsearch.cmd.ClientCommandContentEnum;
import ru.viise.lightsearch.cmd.ClientCommandEnum;
import ru.viise.lightsearch.data.CommandConfirmSoftCheckRecordsDTO;
import ru.viise.lightsearch.data.SoftCheckRecord;

public class MessageConfirmSoftCheckProductsJSONDefaultImpl implements MessageConfirmSoftCheckProducts {

    private final String CONFIRM_SOFT_CHECK_PRODUCTS = ClientCommandEnum.CONFIRM_SOFT_CHECK_PRODUCTS.stringValue();
    private final String COMMAND                     = ClientCommandContentEnum.COMMAND.stringValue();
    private final String IMEI_FIELD                  = ClientCommandContentEnum.IMEI.stringValue();
    private final String USER_IDENT                  = ClientCommandContentEnum.USER_IDENT.stringValue();
    private final String CARD_CODE                   = ClientCommandContentEnum.CARD_CODE.stringValue();
    private final String DATA                        = ClientCommandContentEnum.DATA.stringValue();
    private final String ID                          = ClientCommandContentEnum.ID.stringValue();
    private final String AMOUNT                      = ClientCommandContentEnum.AMOUNT.stringValue();

    private final String IMEI;

    private final CommandConfirmSoftCheckRecordsDTO cmdConSCRecDTO;

    public MessageConfirmSoftCheckProductsJSONDefaultImpl(String IMEI, CommandConfirmSoftCheckRecordsDTO cmdConSCRecDTO) {
        this.IMEI = IMEI;
        this.cmdConSCRecDTO = cmdConSCRecDTO;
    }

    @SuppressWarnings("unchecked")
    @Override
    public String message() {
        JSONArray jData = new JSONArray();
        for(SoftCheckRecord record: cmdConSCRecDTO.softCheckRecords()) {
            JSONObject jRecord = new JSONObject();
            jRecord.put(ID, record.barcode());
            jRecord.put(AMOUNT, String.valueOf(record.currentAmount()));
            jData.add(jRecord);
        }

        JSONObject msgCSCPObj = new JSONObject();
        msgCSCPObj.put(COMMAND, CONFIRM_SOFT_CHECK_PRODUCTS);
        msgCSCPObj.put(IMEI_FIELD, IMEI);
        msgCSCPObj.put(USER_IDENT, cmdConSCRecDTO.userIdent());
        msgCSCPObj.put(CARD_CODE, cmdConSCRecDTO.cardCode());
        msgCSCPObj.put(DATA, jData);
        return msgCSCPObj.toJSONString();
    }
}
