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
import ru.viise.lightsearch.data.CommandSearchDTO;

public class MessageSearchJSONDefaultImpl implements MessageSearch {

    private final String SEARCH     = ClientCommandEnum.SEARCH.stringValue();
    private final String COMMAND    = ClientCommandContentEnum.COMMAND.stringValue();
    private final String IMEI_FIELD = ClientCommandContentEnum.IMEI.stringValue();
    private final String BARCODE    = ClientCommandContentEnum.BARCODE.stringValue();
    private final String SKLAD      = ClientCommandContentEnum.SKLAD.stringValue();
    private final String TK         = ClientCommandContentEnum.TK.stringValue();

    private final String IMEI;
    private final CommandSearchDTO cmdSearchDTO;

    public MessageSearchJSONDefaultImpl(String IMEI, CommandSearchDTO cmdSearchDTO) {
        this.IMEI = IMEI;
        this.cmdSearchDTO = cmdSearchDTO;
    }

    @SuppressWarnings("unchecked")
    @Override
    public String message() {
        JSONObject msgSearchObj = new JSONObject();
        msgSearchObj.put(COMMAND, SEARCH);
        msgSearchObj.put(IMEI_FIELD, IMEI);
        msgSearchObj.put(BARCODE, cmdSearchDTO.barcode());
        msgSearchObj.put(SKLAD, cmdSearchDTO.sklad());
        msgSearchObj.put(TK, cmdSearchDTO.TK());
        return msgSearchObj.toJSONString();
    }
}
