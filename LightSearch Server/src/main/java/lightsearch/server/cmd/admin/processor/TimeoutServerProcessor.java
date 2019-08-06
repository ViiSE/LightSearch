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

import java.io.FileOutputStream;
import java.io.IOException;
import lightsearch.server.checker.LightSearchChecker;
import lightsearch.server.cmd.admin.AdminCommand;
import lightsearch.server.cmd.changer.ServerStateChanger;
import lightsearch.server.cmd.result.CommandResult;
import lightsearch.server.data.LightSearchServerDTO;
import lightsearch.server.log.LogMessageTypeEnum;
import lightsearch.server.message.result.ResultTypeMessageEnum;
import lightsearch.server.timer.TimersIDEnum;

/**
 *
 * @author ViiSE
 */
public class TimeoutServerProcessor extends AbstractProcessorAdmin {

    private final ServerStateChanger changer;
    private final TimersIDEnum timerId;
    
    public TimeoutServerProcessor(LightSearchServerDTO serverDTO, LightSearchChecker checker,
            ServerStateChanger changer, TimersIDEnum timerId) {
        super(serverDTO, checker);
        this.changer = changer;
        this.timerId = timerId;
    }

    @Override
    synchronized public CommandResult apply(AdminCommand admCommand) {
        if(!super.checker.isNull(admCommand.name(), admCommand.serverTime())) {
            boolean isNull = true;

            String serverTime = admCommand.serverTime();
            int serverReboot = Integer.parseInt(serverTime);

            try(FileOutputStream fout = new FileOutputStream(serverDTO.currentDirectory() + "settings")) {
                fout.write((serverReboot + ";" + serverDTO.settingsDAO().clientTimeoutValue()).getBytes());

                serverDTO.settingsDAO().setServerRebootValue(serverReboot);

                if(serverDTO.settingsDAO().serverRebootValue() != 0)
                    isNull = false;

                if(!isNull) 
                    changer.executeRebootTimer(timerId);
                else
                    changer.destroyRebootTimer(timerId);
                
                return super.commandResult(admCommand.name(), LogMessageTypeEnum.INFO, ResultTypeMessageEnum.TRUE,
                        "Server timeout is set to " + serverReboot + " h.",
                        admCommand.name() + " set server timeout to " + serverReboot + " h.");
            }
            catch(IOException ex) {
                return super.commandResult(admCommand.name(), LogMessageTypeEnum.ERROR, ResultTypeMessageEnum.FALSE, 
                        "Server timeout is not set. Try again.",
                        admCommand.name() + ": server timeout is not set. Exception: " + ex.getMessage());
            }
        }
        else
            return super.commandResult("Unknown", LogMessageTypeEnum.ERROR, ResultTypeMessageEnum.FALSE, 
                    "Wrong command format. You are disconnected.", null);
    }
}
