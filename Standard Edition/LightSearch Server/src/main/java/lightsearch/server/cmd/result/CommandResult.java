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
package lightsearch.server.cmd.result;

import lightsearch.server.log.LogMessageTypeEnum;

/**
 * Результат команды клиентов LightSearch Server.
 * Все клиенты LightSearch Server получают ответ от LightSearch Server в виде результата проделанной команды. Это
 * сообщение возвращает метод {@link #message()}. Для сообщения в лог LightSearch Server применяется метод
 * {@link #logMessage()}, и для определения типа сообщения лога - {@link #logMessage()}.
 * @author ViiSE
 * @see lightsearch.server.log.LogMessageTypeEnum
 * @see lightsearch.server.handler.admin
 * @see lightsearch.server.handler.client
 * @see lightsearch.server.handler.system
 * @since 1.0
 */
public interface CommandResult {
    String message();
    LogMessageTypeEnum type();
    String logMessage();
}
