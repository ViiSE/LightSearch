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
import lightsearch.server.cmd.changer.ServerStateChanger;
import lightsearch.server.cmd.result.CommandResult;
import lightsearch.server.data.LightSearchServerDTO;
import lightsearch.server.data.LightSearchServerSettingsDAO;
import lightsearch.server.log.LogMessageTypeEnum;
import lightsearch.server.message.result.ResultTypeMessageEnum;
import lightsearch.server.producer.cmd.admin.CommandResultAdminCreatorProducer;
import lightsearch.server.timer.TimersIDEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author ViiSE
 */
@Component("timeoutServerProcessorAdmin")
@Scope("prototype")
public class TimeoutServerProcessor implements ProcessorAdmin {

    private final LightSearchChecker checker;
    private final String currentDirectory;
    private final LightSearchServerSettingsDAO settingsDAO;
    private final ServerStateChanger changer;
    private final TimersIDEnum timerId;

    @Autowired
    private CommandResultAdminCreatorProducer producer;

    @Autowired
    public TimeoutServerProcessor(LightSearchServerDTO serverDTO, LightSearchChecker checker,
            ServerStateChanger changer, TimersIDEnum timerId) {
        this.checker = checker;
        this.currentDirectory = serverDTO.currentDirectory();
        this.settingsDAO = serverDTO.settingsDAO();
        this.changer = changer;
        this.timerId = timerId;
    }

    @Override
    synchronized public CommandResult apply(AdminCommand admCommand) {
        if(!checker.isNull(admCommand.name(), admCommand.serverTime())) {
            boolean isNull = true;

            String serverTime = admCommand.serverTime();
            int serverReboot = Integer.parseInt(serverTime);

            try(FileOutputStream fout = new FileOutputStream(currentDirectory + "settings")) {
                fout.write((serverReboot + ";" + settingsDAO.clientTimeoutValue()).getBytes());

                settingsDAO.setServerRebootValue(serverReboot);

                if(settingsDAO.serverRebootValue() != 0)
                    isNull = false;

                if(!isNull) 
                    changer.executeRebootTimer(timerId);
                else
                    changer.destroyRebootTimer(timerId);
                
                return commandResult(admCommand.name(), LogMessageTypeEnum.INFO, ResultTypeMessageEnum.TRUE,
                        "Server timeout is set to " + serverReboot + " h.",
                        admCommand.name() + " set lightsearch.server timeout to " + serverReboot + " h.");
            }
            catch(IOException ex) {
                return commandResult(admCommand.name(), LogMessageTypeEnum.ERROR, ResultTypeMessageEnum.FALSE,
                        "Server timeout is not set. Try again.",
                        admCommand.name() + ": lightsearch.server timeout is not set. Exception: " + ex.getMessage());
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
