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
import static ru.viise.lightsearch.cmd.ClientCommandEnum.CONFIRM_SOFT_CHECK_PRODUCTS;
import static test.TestMessage.catchMessage;

public class ConfirmSoftCheckProductsProcessorTest implements Function<String, String> {

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
            if(!command.equals(CONFIRM_SOFT_CHECK_PRODUCTS.stringValue()))
                return errorMessage("Wrong command name!", IMEI);

            String ident = (String) jMessage.get(ClientCommandContentEnum.USER_IDENT.stringValue());
            assertThat(ident).isNotNull();
            if(!ident.matches("[0-9]+"))
                return errorMessage("Wrong ident!", IMEI);

            String cardCode = (String) jMessage.get(ClientCommandContentEnum.CARD_CODE.stringValue());
            assertThat(cardCode).isNotNull();
            if(!cardCode.matches("[0-9]+"))
                return errorMessage("Wrong userIdent!", IMEI);


            Object data = jMessage.get(ClientCommandContentEnum.DATA.stringValue());
            assertThat(data).isNotNull();

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
        jAnswer.put(ClientCommandContentEnum.IS_DONE.stringValue(), "false");
        jAnswer.put(ClientCommandContentEnum.MESSAGE.stringValue(), message);

        return jAnswer.toJSONString();
    }

    @SuppressWarnings("unchecked")
    private String successMessage(String IMEI) {
        JSONObject jAnswer = new JSONObject();
        jAnswer.put(ClientCommandContentEnum.IMEI.stringValue(), IMEI);
        jAnswer.put(ClientCommandContentEnum.IS_DONE.stringValue(), "true");

        JSONArray jData = new JSONArray();
        jAnswer.put(ClientCommandContentEnum.DATA.stringValue(), jData);

        return jAnswer.toJSONString();
    }
}
