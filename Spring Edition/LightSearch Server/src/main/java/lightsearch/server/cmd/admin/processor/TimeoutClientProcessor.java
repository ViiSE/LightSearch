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
import lightsearch.server.data.LightSearchServerSettingsDAO;
import lightsearch.server.log.LogMessageTypeEnum;
import lightsearch.server.message.result.ResultTypeMessageEnum;
import lightsearch.server.producer.cmd.admin.CommandResultAdminCreatorProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author ViiSE
 */
@Component("timeoutClientProcessorAdmin")
@Scope("prototype")
public class TimeoutClientProcessor implements ProcessorAdmin {

    private final LightSearchChecker checker;
    private final String currentDirectory;
    private final LightSearchServerSettingsDAO settingsDAO;

    @Autowired
    private CommandResultAdminCreatorProducer producer;

    public TimeoutClientProcessor(LightSearchServerDTO serverDTO, LightSearchChecker checker) {
        this.checker = checker;
        this.currentDirectory = serverDTO.currentDirectory();
        this.settingsDAO = serverDTO.settingsDAO();
    }
    
    @Override
    synchronized public CommandResult apply(AdminCommand admCommand) {
        if(!checker.isNull(admCommand.clientTimeout(), admCommand.name())) {
            String clientTime = admCommand.clientTimeout();
            int clientTimeout = Integer.parseInt(clientTime);

            try(FileOutputStream fout = new FileOutputStream(currentDirectory + "settings")) {
                fout.write((settingsDAO.serverRebootValue() + ";" + clientTimeout).getBytes());
                settingsDAO.setClientTimeoutValue(clientTimeout);

                return commandResult(admCommand.name(), LogMessageTypeEnum.INFO, ResultTypeMessageEnum.TRUE,
                        "Client timeout is set to " + clientTimeout + " ms. Need restart lightsearch.server to apply changes.",
                        admCommand.name() + " set client timeout to " + clientTimeout + " ms.");
            }
            catch(IOException ex) {
                return commandResult(admCommand.name(), LogMessageTypeEnum.ERROR, ResultTypeMessageEnum.FALSE,
                        "Client timeout is not set. Try again.",
                        admCommand.name() + ": client timeout is not set. Exception: " + ex.getMessage());
            }
        }
        else
            return commandResult("Unknown", LogMessageTypeEnum.ERROR, ResultTypeMessageEnum.FALSE,
                    "Wrong command format. You are disconnected.", null);
    }

    private CommandResult commandResult(String name, LogMessageTypeEnum type, ResultTypeMessageEnum resultValue, Object message, String logMessage) {
        return producer.getCommandResultAdminCreatorDefaultInstance(name, type, resultValue, message, logMessage)
                .createCommandResult();
    }
}
