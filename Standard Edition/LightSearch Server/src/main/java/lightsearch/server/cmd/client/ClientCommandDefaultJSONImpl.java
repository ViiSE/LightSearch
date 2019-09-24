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
package lightsearch.server.cmd.client;

import org.json.simple.JSONObject;

/**
 * Реализация интерфейса {@link lightsearch.server.cmd.client.ClientCommand}, в которой команда приходит от клиента в
 * формате JSON.
 * <p>
 * Из сообщения, которое пришло от клиента, LightSearch Server достает все необходимые данные команд, согласно протоколу.
 * <p>
 * Для получения данных клиента из сообщения в формате JSON используется библиотека
 * <a href="https://github.com/fangyidong/json-simple">JSON.simple</a>.
 * @author ViiSE
 * @since 1.0.0
 */
public class ClientCommandDefaultJSONImpl implements ClientCommand {

    private final JSONObject clientInfoObj;

    private final String COMMAND    = ClientCommandContentEnum.COMMAND.stringValue();
    private final String IMEI       = ClientCommandContentEnum.IMEI.stringValue();
    private final String IP         = ClientCommandContentEnum.IP.stringValue();
    private final String OS         = ClientCommandContentEnum.OS.stringValue();
    private final String MODEL      = ClientCommandContentEnum.MODEL.stringValue();
    private final String USERNAME   = ClientCommandContentEnum.USERNAME.stringValue();
    private final String PASSWORD   = ClientCommandContentEnum.PASSWORD.stringValue();
    private final String BARCODE    = ClientCommandContentEnum.BARCODE.stringValue();
    private final String SKLAD      = ClientCommandContentEnum.SKLAD.stringValue();
    private final String TK         = ClientCommandContentEnum.TK.stringValue();
    private final String DATA       = ClientCommandContentEnum.DATA.stringValue();
    private final String DELIVERY   = ClientCommandContentEnum.DELIVERY.stringValue();
    private final String USER_IDENT = ClientCommandContentEnum.USER_IDENT.stringValue();
    private final String CARD_CODE  = ClientCommandContentEnum.CARD_CODE.stringValue();
    
    public ClientCommandDefaultJSONImpl(Object clientInfoObj) {
        this.clientInfoObj = (JSONObject)clientInfoObj;
    }

    @Override
    public String command() {
        try {
            return clientInfoObj.get(COMMAND).toString();
        } catch(NullPointerException ignore) {
            return null;
        }
    }

    @Override
    public String IMEI() {
        try {
            return clientInfoObj.get(IMEI).toString();
        } catch(NullPointerException ignore) {
            return null;
        }
    }

    @Override
    public String ip() {
        try {
            return clientInfoObj.get(IP).toString();
        } catch(NullPointerException ignore) {
            return null;
        }
    }

    @Override
    public String os() {
        try {
            return clientInfoObj.get(OS).toString();
        } catch(NullPointerException ignore) {
            return null;
        }
    }

    @Override
    public String model() {
        try {
            return clientInfoObj.get(MODEL).toString();
        } catch(NullPointerException ignore) {
            return null;
        }
    }

    @Override
    public String username() {
        try {
            return clientInfoObj.get(USERNAME).toString();
        } catch(NullPointerException ignore) {
            return null;
        }
    }

    @Override
    public String barcode() {
        try {
            return clientInfoObj.get(BARCODE).toString();
        } catch(NullPointerException ignore) {
            return null;
        }
    }

    @Override
    public String sklad() {
        try {
            return clientInfoObj.get(SKLAD).toString();
        } catch(NullPointerException ignore) {
            return null;
        }
    }

    @Override
    public String TK() {
        try {
            return clientInfoObj.get(TK).toString();
        } catch(NullPointerException ignore) {
            return null;
        }
    }

    @Override
    public String password() {
        try {
            return clientInfoObj.get(PASSWORD).toString();
        } catch(NullPointerException ignore) {
            return null;
        }
    }

    @Override
    public String data() {
        try {
            return clientInfoObj.get(DATA).toString();
        } catch(NullPointerException ignore) {
            return null;
        }
    }

    @Override
    public String delivery() {
        try {
            return clientInfoObj.get(DELIVERY).toString();
        } catch(NullPointerException ignore) {
            return null;
        }
    }

    @Override
    public String userIdentifier() {
        try {
            return clientInfoObj.get(USER_IDENT).toString();
        } catch(NullPointerException ignore) {
            return null;
        }
    }
    
    @Override
    public String cardCode() {
        try {
            return clientInfoObj.get(CARD_CODE).toString();
        } catch(NullPointerException ignore) {
            return null;
        }
    }
    
}
