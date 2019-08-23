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
package lightsearch.server.message;

import lightsearch.server.exception.MessageSenderException;

/**
 * Отправляет сообщения клиентам LightSearch Server'а.
 * <p>
 * Используется в обработчиках клиентов.
 * <p>
 * Если произошла ошибка при отправке сообщения, то генерируется исключение
 * {@link lightsearch.server.exception.MessageSenderException}.
 * @author ViiSE
 * @see lightsearch.server.handler.Handler
 * @since 1.0
 */
public interface MessageSender {
    void sendMessage(String message) throws MessageSenderException;
}
