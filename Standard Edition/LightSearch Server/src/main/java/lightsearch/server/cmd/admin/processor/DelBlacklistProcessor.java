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

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Обработчик команды удаления клиента LightSearch Server из черного списка.
 * <p>
 * Удаляет клиента LightSearch Server из черного списка.
 * @author ViiSE
 * @since 1.0.0
 */
public class DelBlacklistProcessor extends AbstractProcessorAdmin {

    public DelBlacklistProcessor(LightSearchServerDTO serverDTO, LightSearchChecker checker) {
        super(serverDTO, checker);
    }

    @Override
    synchronized public CommandResult apply(AdminCommand admCommand) {
        if(!super.checker.isNull(admCommand.name(), admCommand.IMEI())) {
            if(serverDTO.blacklist().contains(admCommand.IMEI())) {
                serverDTO.blacklist().remove(admCommand.IMEI());
                try(FileOutputStream fout = new FileOutputStream(serverDTO.currentDirectory() + "blacklist"); BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fout))) {
                    for (String client : serverDTO.blacklist()) {
                        bw.write(client);
                        bw.newLine();
                    }

                    return super.commandResult(admCommand.name(), LogMessageTypeEnum.INFO, ResultTypeMessageEnum.TRUE,
                            "Client " + admCommand.IMEI() + " has been removed from the blacklist.",
                            admCommand.name() + ": client " + admCommand.IMEI() + " has been removed from the blacklist");
                }
                catch(IOException ex) {
                    return super.commandResult(admCommand.name(), LogMessageTypeEnum.ERROR, ResultTypeMessageEnum.FALSE,
                            "Client " + admCommand.IMEI() + " has not been removed from the blacklist. Try again.", 
                            admCommand.name() + ": client " + admCommand.IMEI() + " has not been removed from the blacklist. Exception: " + ex.getMessage());
                }
            }
            else {
                return super.commandResult(admCommand.name(), LogMessageTypeEnum.ERROR, ResultTypeMessageEnum.FALSE,
                        "Client " + admCommand.IMEI() + " not in the blacklist.",
                        admCommand.name() + ": client " + admCommand.IMEI() + " not in the blacklist.");
            }
        }
        else
            return super.commandResult("Unknown", LogMessageTypeEnum.ERROR, ResultTypeMessageEnum.FALSE, 
                    "Wrong command format. You are disconnected.", null);
    }
    
}
