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

/**
 * Реализация интерфейса {@link lightsearch.server.message.result.type.MessageType}, формирующее сообщение об успехе в
 * формате JSON.
 * <p>
 * Если результат обработки сообщения клиента успешный, то LightSearch Server формирует следующее сообщению клиенту:
 * <pre> <code>
 *     {
 *         "IMEI":"Значение IMEI",
 *         "is_done":"Значение статуса сообщения (в данном случае - "True")",
 *         "message":"Сообщение об успехе"
 *     }
 * </code> </pre>
 * <p>
 * Создание сообщения в формате JSON осуществляется при помощи библиотеки
 * <a href="https://github.com/fangyidong/json-simple">JSON.simple</a>.
 * @author ViiSE
 * @see lightsearch.server.message.result.MessageCreatorDefaultImpl
 * @since 1.0.0
 */
public class MessageTypeClientJSONSuccessDefaultImpl implements MessageType {
    
    @Override
    public String createFormattedMessage(String IMEI, String isDone, Object message) {
        return message.toString();
    }
    
}
