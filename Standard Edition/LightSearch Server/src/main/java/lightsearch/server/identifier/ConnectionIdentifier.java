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
package lightsearch.server.identifier;

import java.net.Socket;
import lightsearch.server.exception.ConnectionIdentifierException;

/**
 * Идентифицирует соединение, пришедшее на сокет.
 * <p>
 * Применяется для идентификации клиента при создании клиентского сокета и определения соответствующего обработчика.
 * @author ViiSE
 * @see java.net.Socket
 * @see lightsearch.server.handler.HandlerCreator
 * @since 1.0.0
 */
public interface ConnectionIdentifier {
    ConnectionIdentifierResult identifyConnection(Socket clientSocket) throws ConnectionIdentifierException;
}
