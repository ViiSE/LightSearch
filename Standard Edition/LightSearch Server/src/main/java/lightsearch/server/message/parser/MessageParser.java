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
package lightsearch.server.message.parser;

import lightsearch.server.exception.MessageParserException;

/**
 * Парсит сообщения клиентов LightSearch Server'а в необходимый объект.
 * <p>
 * Используется в обработчиках клиентов и при идентификации клиента.
 * <p>
 * Если произошла ошибка при принятии сообщения, то генерируется исключение
 * {@link lightsearch.server.exception.MessageParserException}.
 * @author ViiSE
 * @see lightsearch.server.handler.Handler
 * @see lightsearch.server.identifier.ConnectionIdentifier
 * @since 1.0
 */
public interface MessageParser {
    Object parse(String rawMessage) throws MessageParserException;
}
