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

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import lightsearch.server.checker.LightSearchChecker;
import lightsearch.server.cmd.admin.AdminCommand;
import lightsearch.server.cmd.result.CommandResult;
import lightsearch.server.data.LightSearchServerDTO;
import lightsearch.server.log.LogMessageTypeEnum;
import lightsearch.server.message.result.ResultTypeMessageEnum;

/**
 * Обработчик команды добавления клиента в черный список.
 * <p>
 * Добавляет клиента LightSearch Server в черный список. В черный список администраторов добавлять нельзя.
 * @author ViiSE
 * @since 1.0
 */
public class AddBlacklistProcessor extends AbstractProcessorAdmin {

    public AddBlacklistProcessor(LightSearchServerDTO serverDTO, LightSearchChecker checker) {
        super(serverDTO, checker);
    }

    @Override
    synchronized public CommandResult apply(AdminCommand admCommand) {
        if(!super.checker.isNull(admCommand.IMEI(), admCommand.name())) {
        
            if(!super.serverDTO.blacklist().contains(admCommand.IMEI())) {
                super.serverDTO.blacklist().add(admCommand.IMEI());
                super.serverDTO.clients().remove(admCommand.IMEI());
                try(FileOutputStream fout = new FileOutputStream(super.serverDTO.currentDirectory() + "blacklist", true); 
                        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fout))) {
                    bw.write(admCommand.IMEI());
                    bw.newLine();
                }
                catch(IOException ex) {
                    super.serverDTO.blacklist().remove(admCommand.IMEI());

                    return super.commandResult(admCommand.name(), LogMessageTypeEnum.ERROR, ResultTypeMessageEnum.FALSE, 
                            "Client " + admCommand.IMEI() + " has not been added to the blacklist. Try again.",
                            admCommand.name() + ": client " + admCommand.IMEI() + " has not been added to the blacklist. Exception: " +
                            ex.getMessage());
                }
                return super.commandResult(admCommand.name(), LogMessageTypeEnum.INFO, ResultTypeMessageEnum.TRUE, 
                        "Client " + admCommand.IMEI() + " has been added to the blacklist.", 
                        admCommand.name() + ": client " + admCommand.IMEI() + " has been added to the blacklist");
            }
            else {
                return super.commandResult(admCommand.name(), LogMessageTypeEnum.ERROR, ResultTypeMessageEnum.FALSE,
                        "Client " + admCommand.IMEI() + " already in the blacklist.",
                        admCommand.name() + ": client " + admCommand.IMEI() + " already in the blacklist");
            }
        } else
            return super.commandResult("Unknown", LogMessageTypeEnum.ERROR, ResultTypeMessageEnum.FALSE, 
                    "Wrong command format. You are disconnected.", null);
    }
}
