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
package lightsearch.server.cmd.system;

import org.json.simple.JSONObject;

/**
 * Реализация интерфейса {@link lightsearch.server.cmd.system.SystemCommand}, в которой команда приходит от системного
 * бота в формате JSON.
 * <p>
 * Из сообщения, которое пришло от системного бота, LightSearch Server достает все необходимые данные команд, согласно
 * протоколу.
 * <p>
 * Для получения данных системного бота из сообщения в формате JSON используется библиотека
 * <a href="https://github.com/fangyidong/json-simple">JSON.simple</a>.
 * @author ViiSE
 * @since 2.0.0
 */
public class SystemCommandDefaultJSONImpl implements SystemCommand {

    private final JSONObject systemInfoObj;

    private final String COMMAND = SystemCommandContentEnum.COMMAND.stringValue();
    
    public SystemCommandDefaultJSONImpl(Object systemInfoObj) {
        this.systemInfoObj = (JSONObject)systemInfoObj;
    }

    @Override
    public String command() {
        try {
            return systemInfoObj.get(COMMAND).toString();
        } catch(NullPointerException ignore) {
            return null;
        }
    }
    
}
