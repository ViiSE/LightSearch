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

import lightsearch.server.cmd.Command;

/**
 * Команда администратора LightSearch Server.
 * <p>
 * Содержит все необходимые параметры для всех команд администратора.
 * <p>
 * Применяется как аргумент метода <code>apply()</code> обработчиков команд администратора LightSearch Server.
 * @author ViiSE
 * @see lightsearch.server.handler.admin
 * @see lightsearch.server.cmd.admin.processor
 * @since 1.0
 */
public interface AdminCommand extends Command {
    String name();
    String serverTime();
    String clientTimeout();
    String IMEI();
    String adminName();
    String password();
    String ip();
    String port();
    String dbName();
}
