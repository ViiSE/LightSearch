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
package lightsearch.server.cmd;

/**
 * Команда клиента LightSearch Server.
 * <p>
 * Общение между клиентами и LightSearch Server происходит через шаблон проектирования 'Команда'. Клиенты отправляют
 * команды и соответствующие этим командам параметры. LightSearch Server считывает название команды, вызывает ее
 * обработчик, и отправляет результат клиенту.
 * <p>
 * У любой команды в LightSearch Server есть название. Без названия команда не сможет выполниться. Если клиент прислал
 * команду без названия, то LightSearch Server отключит этого клиента от текущего сеанса.
 * <p>
 * Для создания новой команды необходимо реализовывать этот интерфейс.
 * @author ViiSE
 * @see lightsearch.server.cmd.admin.AdminCommand
 * @see lightsearch.server.cmd.client.ClientCommand
 * @see lightsearch.server.cmd.system.SystemCommand
 * @since 1.0.0
 */
public interface Command {
    String command();
}
