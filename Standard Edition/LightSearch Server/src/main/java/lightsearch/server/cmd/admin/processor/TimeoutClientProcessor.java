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
package lightsearch.server.cmd.admin.processor;

import lightsearch.server.checker.LightSearchChecker;
import lightsearch.server.cmd.admin.AdminCommand;
import lightsearch.server.cmd.result.CommandResult;
import lightsearch.server.data.LightSearchServerDTO;
import lightsearch.server.log.LogMessageTypeEnum;
import lightsearch.server.message.result.ResultTypeMessageEnum;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Обработчик команды установки тайм-аута клиента со стороны LightSearch Server.
 * <p>
 * Устанавливает для каждого клиента тайм-аут со стороны LightSearch Server. Если клиент подключился раньше, чем это
 * значение было изменено, то клиент останется со старым значением тайм-аута. Для того, чтобы значение было изменено,
 * клиенту необходимо переподключиться. Для новых же клиентов, которые подключились позже чем это значение было изменено,
 * новое значние тайм-аута будет применено немедленно.
 * <p>
 * Значение тайм-аута, равное 0, означает, что тайм-аут будет отключен.
 * @author ViiSE
 * @since 1.0.0
 */
public class TimeoutClientProcessor extends AbstractProcessorAdmin {

    public TimeoutClientProcessor(LightSearchServerDTO serverDTO, LightSearchChecker checker) {
        super(serverDTO, checker);
    }
    
    @Override
    synchronized public CommandResult apply(AdminCommand admCommand) {
        if(!super.checker.isNull(admCommand.clientTimeout(), admCommand.name())) {
            String clientTime = admCommand.clientTimeout();
            int clientTimeout = Integer.parseInt(clientTime);

            try(FileOutputStream fout = new FileOutputStream(serverDTO.currentDirectory() + "settings")) {
                fout.write((serverDTO.settingsDAO().serverRebootValue() + ";" + clientTimeout).getBytes());
                serverDTO.settingsDAO().setClientTimeoutValue(clientTimeout);

                return super.commandResult(admCommand.name(), LogMessageTypeEnum.INFO, ResultTypeMessageEnum.TRUE,
                        "Client timeout is set to " + clientTimeout + " ms. Need restart server to apply changes.",
                        admCommand.name() + " set client timeout to " + clientTimeout + " ms.");
            } catch(IOException ex) {
                return super.commandResult(admCommand.name(), LogMessageTypeEnum.ERROR, ResultTypeMessageEnum.FALSE,
                        "Client timeout is not set. Try again.",
                        admCommand.name() + ": client timeout is not set. Exception: " + ex.getMessage());
            }
        } else
            return super.commandResult("Unknown", LogMessageTypeEnum.ERROR, ResultTypeMessageEnum.FALSE,
                    "Wrong command format. You are disconnected.", null);
    }
}
