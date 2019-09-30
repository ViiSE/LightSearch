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

package test.server.processor;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.function.Function;

import ru.viise.lightsearch.cmd.ClientCommandContentEnum;
import ru.viise.lightsearch.exception.MessageParserException;
import ru.viise.lightsearch.message.parser.MessageParserInit;

import static com.google.common.truth.Truth.assertThat;
import static ru.viise.lightsearch.cmd.ClientCommandEnum.SEARCH;
import static test.TestMessage.catchMessage;

public class SearchProcessorTest implements Function<String, String> {

    @Override
    public String apply(String rawMessage) {
        try {
            JSONObject jMessage = (JSONObject) MessageParserInit.messageParser().parse(rawMessage);

            String IMEI = (String) jMessage.get(ClientCommandContentEnum.IMEI.stringValue());
            assertThat(IMEI).isNotNull();
            if(!IMEI.matches("[0-9]+"))
                return errorMessage("Wrong IMEI!", IMEI);

            String command = (String) jMessage.get(ClientCommandContentEnum.COMMAND.stringValue());
            assertThat(command).isNotNull();
            if(!command.equals(SEARCH.stringValue()))
                return errorMessage("Wrong command name!", IMEI);

            String barcode = (String) jMessage.get(ClientCommandContentEnum.BARCODE.stringValue());
            assertThat(barcode).isNotNull();
            if(!barcode.matches("[0-9]+"))
                return errorMessage("Wrong barcode!", barcode);

            String sklad = (String) jMessage.get(ClientCommandContentEnum.SKLAD.stringValue());
            assertThat(sklad).isNotNull();

            String TK = (String) jMessage.get(ClientCommandContentEnum.TK.stringValue());
            assertThat(TK).isNotNull();

            return successMessage(IMEI);
        } catch(MessageParserException ex) {
            catchMessage(ex);
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    private String errorMessage(String message, String IMEI) {
        JSONObject jAnswer = new JSONObject();
        jAnswer.put(ClientCommandContentEnum.IMEI.stringValue(), IMEI);
        jAnswer.put(ClientCommandContentEnum.IS_DONE.stringValue(), "False");
        jAnswer.put(ClientCommandContentEnum.MESSAGE.stringValue(), message);

        return jAnswer.toJSONString();
    }

    @SuppressWarnings("unchecked")
    private String successMessage(String IMEI) {
        JSONObject jAnswer = new JSONObject();
        jAnswer.put(ClientCommandContentEnum.IMEI.stringValue(), IMEI);
        jAnswer.put(ClientCommandContentEnum.IS_DONE.stringValue(), "True");

        JSONArray jData = new JSONArray();

        JSONObject sklad1Item1 = new JSONObject();
        sklad1Item1.put(ClientCommandContentEnum.SUBDIVISION.stringValue(), "Sklad 1");
        sklad1Item1.put(ClientCommandContentEnum.ID.stringValue(), "111111");
        sklad1Item1.put(ClientCommandContentEnum.NAME.stringValue(), "Item 1");
        sklad1Item1.put(ClientCommandContentEnum.PRICE.stringValue(), "300");
        sklad1Item1.put(ClientCommandContentEnum.AMOUNT.stringValue(), "10");
        sklad1Item1.put(ClientCommandContentEnum.UNIT.stringValue(), "pcs.");

        JSONObject sklad1Item2 = new JSONObject();
        sklad1Item2.put(ClientCommandContentEnum.SUBDIVISION.stringValue(), "Sklad 2");
        sklad1Item2.put(ClientCommandContentEnum.ID.stringValue(), "222222");
        sklad1Item2.put(ClientCommandContentEnum.NAME.stringValue(), "Item 2");
        sklad1Item2.put(ClientCommandContentEnum.PRICE.stringValue(), "100");
        sklad1Item2.put(ClientCommandContentEnum.AMOUNT.stringValue(), "20");
        sklad1Item2.put(ClientCommandContentEnum.UNIT.stringValue(), "pcs.");

        jData.add(sklad1Item1);
        jData.add(sklad1Item2);

        jAnswer.put(ClientCommandContentEnum.DATA.stringValue(), jData);

        return jAnswer.toJSONString();
    }
}
