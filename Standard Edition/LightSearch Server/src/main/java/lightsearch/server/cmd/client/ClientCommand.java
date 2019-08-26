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

import lightsearch.server.cmd.Command;

/**
 * Команда клиента LightSearch Server.
 * <p>
 * Содержит все необходимые параметры для всех команд клиента.
 * <p>
 * Применяется как аргумент метода <code>apply()</code> обработчиков команд клиента LightSearch Server.
 * @author ViiSE
 * @see lightsearch.server.handler.client
 * @see lightsearch.server.cmd.client.processor
 * @since 1.0
 */
public interface ClientCommand extends Command {
    String IMEI();
    String ip();
    String os();
    String model();
    String username();
    String password();
    String barcode();
    String sklad();
    String TK();
    String data();
    String userIdentifier();
    String delivery();
    String cardCode();
}