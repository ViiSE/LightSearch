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
package lightsearch.server.cmd.admin;

import org.json.simple.JSONObject;

/**
 * Реализация интерфейса {@link lightsearch.server.cmd.admin.AdminCommand}, в котором команда приходит от администратора
 * в формате JSON.
 * <p>
 * Из сообщения, которое пришло от администратора, LightSearch Server достает все необходимые данные команд,
 * согласно протоколу.
 * <p>
 * Для получения данных администратора из сообщения в формате JSON используется библиотека
 * <a href="https://github.com/fangyidong/json-simple">JSON.simple</a>.
 * @author ViiSE
 * @since 1.0
 */
public class AdminCommandDefaultJSONImpl implements AdminCommand {

    private final String COMMAND        = AdminCommandContentEnum.COMMAND.stringValue();
    private final String NAME           = AdminCommandContentEnum.ADMIN_NAME.stringValue();
    private final String SERVER_TIME    = AdminCommandContentEnum.SERVER_TIME.stringValue();
    private final String CLIENT_TIMEOUT = AdminCommandContentEnum.CLIENT_TIMEOUT.stringValue();
    private final String IMEI           = AdminCommandContentEnum.IMEI.stringValue();
    private final String PASSWORD       = AdminCommandContentEnum.PASSWORD.stringValue();
    private final String IP             = AdminCommandContentEnum.IP.stringValue();
    private final String PORT           = AdminCommandContentEnum.PORT.stringValue();
    private final String DB_NAME        = AdminCommandContentEnum.DATABASE_NAME.stringValue();
    private final String ADMIN_NAME     = AdminCommandContentEnum.NEW_ADMIN_NAME.stringValue();
    
    private final JSONObject adminInfoObj;
    
    public AdminCommandDefaultJSONImpl(Object adminInfoObj) {
        this.adminInfoObj = (JSONObject)adminInfoObj;
    }

    @Override
    public String name() {
        try {
            return adminInfoObj.get(NAME).toString();
        }
        catch(NullPointerException ignore) {
            return null;
        }
    }

    @Override
    public String command() {
        try {
            return adminInfoObj.get(COMMAND).toString();
        }
        catch(NullPointerException ignore) {
            return null;
        }
    }

    @Override
    public String serverTime() {
        try {
            return adminInfoObj.get(SERVER_TIME).toString();
        }
        catch(NullPointerException ignore) {
            return null;
        }
    }

    @Override
    public String clientTimeout() {
        try {
            return adminInfoObj.get(CLIENT_TIMEOUT).toString();
        }
        catch(NullPointerException ignore) {
            return null;
        }
    }

    @Override
    public String IMEI() {
        try {
            return adminInfoObj.get(IMEI).toString();
        }
        catch(NullPointerException ignore) {
            return null;
        }
    }

    @Override
    public String password() {
        try {
            return adminInfoObj.get(PASSWORD).toString();
        }
        catch(NullPointerException ignore) {
            return null;
        }
    }

    @Override
    public String ip() {
        try {
            return adminInfoObj.get(IP).toString();
        }
        catch(NullPointerException ignore) {
            return null;
        }
    }

    @Override
    public String port() {
        try {
            return adminInfoObj.get(PORT).toString();
        }
        catch(NullPointerException ignore) {
            return null;
        }
    }

    @Override
    public String dbName() {
        try {
            return adminInfoObj.get(DB_NAME).toString();
        }
        catch(NullPointerException ignore) {
            return null;
        }
    }

    @Override
    public String adminName() {
        try {
            return adminInfoObj.get(ADMIN_NAME).toString();
        }
        catch(NullPointerException ignore) {
            return null;
        }
    }
    
}
