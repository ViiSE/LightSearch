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
import static ru.viise.lightsearch.cmd.ClientCommandEnum.CONNECT;
import static test.TestMessage.catchMessage;

public class AuthorizationProcessorTest implements Function<String, String> {

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
            if(!command.equals(CONNECT.stringValue()))
                return errorMessage("Wrong command name!", IMEI);

            String username = (String) jMessage.get(ClientCommandContentEnum.USERNAME.stringValue());
            assertThat(username).isNotNull();

            String ident = (String) jMessage.get(ClientCommandContentEnum.USER_IDENT.stringValue());
            assertThat(ident).isNotNull();
            if(!ident.matches("[0-9]+"))
                return errorMessage("Wrong ident!", IMEI);

            return successMessage(IMEI, ident);
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
    private String successMessage(String IMEI, String userIdent) {
        JSONObject jAnswer = new JSONObject();
        jAnswer.put(ClientCommandContentEnum.IMEI.stringValue(), IMEI);
        jAnswer.put(ClientCommandContentEnum.IS_DONE.stringValue(), "true");
        jAnswer.put(ClientCommandContentEnum.MESSAGE.stringValue(), "Connection established!");
        jAnswer.put(ClientCommandContentEnum.USER_IDENT.stringValue(), userIdent);

        JSONArray jArrayTK = new JSONArray();
        jArrayTK.add("TK 1");
        jArrayTK.add("TK 2");

        JSONArray jArraySklad = new JSONArray();
        jArraySklad.add("Sklad 1");
        jArraySklad.add("Sklad 2");
        jArraySklad.add("Sklad 3");

        jAnswer.put(ClientCommandContentEnum.TK_LIST.stringValue(), jArrayTK);
        jAnswer.put(ClientCommandContentEnum.SKLAD_LIST.stringValue(), jArraySklad);

        return jAnswer.toJSONString();
    }
}
