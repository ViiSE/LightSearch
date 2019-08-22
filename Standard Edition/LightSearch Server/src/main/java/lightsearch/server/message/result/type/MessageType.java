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
 * Тип клиентского сообщения LightSearchServer.
 * <p>
 * В LightSearch Server существует два типа клиента: клиент и администратор. Клиент - пользователь, который подключается
 * к LightSearch Server и получает информацию предметной области. Администратор - пользователь, который подключается к
 * LightSearch Server через программу LightSearch Admin Panel для его администрирования. Более подробную информацию об
 * LightSearch Admin Panel читайте в соответствующей
 * <a href="https://github.com/ViiSE/LightSearch/tree/master/Standard%20Edition/LightSearch%20Admin%20Panel/javadoc">документации</a>.
 * <p>
 * Используется в обработчике клиентов LightSearch Server и в формировании сообщения клиенту.
 * @author ViiSE
 * @see lightsearch.server.message.result.MessageCreator
 * @see lightsearch.server.cmd.client.processor
 * @see lightsearch.server.cmd.admin.processor
 * @since 1.0
 */
public interface MessageType {
    String createFormattedMessage(String ident, String isDone, Object message);
}
