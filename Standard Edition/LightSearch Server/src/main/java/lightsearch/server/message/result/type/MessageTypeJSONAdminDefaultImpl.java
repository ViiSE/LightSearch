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
package lightsearch.server.message.result.type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import lightsearch.server.cmd.admin.AdminCommandResultEnum;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Реализация интерфейса {@link lightsearch.server.message.result.type.MessageType}, формирующее сообщение
 * администратору в формате JSON.
 * <p>
 * В зависимотсти от присланной команды LightSearch Server формирует разные типы сообщений администратору. Тип сообщения
 * зависит от того, какому классу принадлежит сообщение.
 * Если сообщение - строка ({@link java.lang.String}), то формируется следующее сообщение:
 * <code>
 *     {
 *         "name":"Имя администратора",
 *         "is_done":"Значение статуса сообщения ("True" или "False")",
 *         "message":"Результат проделанной операции"
 *     }
 * </code>
 * <p>
 * Если сообщение - список ({@link java.util.List}), то формируется следующее сообщение:
 * <code>
 *     {
 *         "name":"Имя администратора",
 *         "is_done":"Значение статуса сообщения ("True" или "False")",
 *         "data":["Список", "Элементов"]
 *     }
 * <p>
 * </code>
 * Применяется в команде {@link lightsearch.server.cmd.admin.processor.BlacklistRequestProcessor}.
 * <p>
 * Если сообщение - карта ({@link java.util.Map}), то формируется следующее сообщение:
 * <code>
 *     {
 *         "name":"Имя администратора",
 *         "is_done":"Значение статуса сообщения ("True" или "False")",
 *         "data":[
 *                    "IMEI":"Значение IMEI клиента",
 *                    "username":"Имя клиента"
 *                ]
 *     }
 * <p>
 * Применяется в команде {@link lightsearch.server.cmd.admin.processor.ClientListRequestProcessor}.
 * </code>
 * Создание сообщения в формате JSON осуществляется при помощи библиотеки
 * <a href="https://github.com/fangyidong/json-simple">JSON.simple</a>.
 * @author ViiSE
 * @see lightsearch.server.message.result.MessageCreatorDefaultImpl
 * @since 1.0
 */
public class MessageTypeJSONAdminDefaultImpl implements MessageType {
    
    private final String NAME = AdminCommandResultEnum.NAME.stringValue();
    private final String IS_DONE = AdminCommandResultEnum.IS_DONE.stringValue();
    private final String MESSAGE = AdminCommandResultEnum.MESSAGE.stringValue();
    private final String DATA = AdminCommandResultEnum.DATA.stringValue();
    private final String USERNAME = AdminCommandResultEnum.USERNAME.stringValue();
    private final String IMEI = AdminCommandResultEnum.IMEI.stringValue();
    
    @Override
    public String createFormattedMessage(String name, String isDone, Object message) {
        if(message instanceof String) {
            JSONObject messageJSON = new JSONObject();
            messageJSON.put(NAME, name);
            messageJSON.put(IS_DONE, isDone);
            messageJSON.put(MESSAGE, message);
            return messageJSON.toString();
        } 
        else if(message instanceof List) {
            List<String> data = (ArrayList)message;
            JSONArray dataArray = new JSONArray();
            dataArray.addAll(data);

            JSONObject dataList = new JSONObject();
            dataList.put(NAME, name);
            dataList.put(IS_DONE, isDone);
            dataList.put(DATA, dataArray);

            return dataList.toString();
        }
        else if(message instanceof HashMap) {
            HashMap<String,String> data = (HashMap)message;
            JSONArray clients = new JSONArray();
            data.entrySet().forEach((nameCl) -> {
                    JSONObject item = new JSONObject();
                    item.put(IMEI, nameCl.getKey());
                    item.put(USERNAME, nameCl.getValue());
                    clients.add(item);
                });

            JSONObject clList = new JSONObject();
            clList.put(NAME, name);
            clList.put(IS_DONE, isDone);
            clList.put(DATA, clients);

            return clList.toString();
        }
        return null;
    }
}
