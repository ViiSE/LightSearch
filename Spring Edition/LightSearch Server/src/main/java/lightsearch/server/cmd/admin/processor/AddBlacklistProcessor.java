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
import lightsearch.server.producer.cmd.admin.CommandResultAdminCreatorProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ViiSE
 */
@Component("addBlacklistProcessorAdmin")
@Scope("prototype")
public class AddBlacklistProcessor implements ProcessorAdmin {

    private final LightSearchChecker checker;
    private final List<String> blacklist;
    private final Map<String, String> clients;
    private final String currentDirectory;

    @Autowired
    private CommandResultAdminCreatorProducer producer;

    public AddBlacklistProcessor(LightSearchServerDTO serverDTO, LightSearchChecker checker) {
        this.checker = checker;
        this.blacklist = serverDTO.blacklist();
        this.clients = serverDTO.clients();
        this.currentDirectory = serverDTO.currentDirectory();
    }

    @Override
    synchronized public CommandResult apply(AdminCommand command) {
        if(!checker.isNull(command.IMEI(), command.name())) {

            if(!blacklist.contains(command.IMEI())) {
                blacklist.add(command.IMEI());
                clients.remove(command.IMEI());
                try(FileOutputStream fout = new FileOutputStream(currentDirectory + "blacklist", true);
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fout))) {
                    bw.write(command.IMEI());
                    bw.newLine();
                }
                catch(IOException ex) {
                    blacklist.remove(command.IMEI());

                    return commandResult(command.name(), LogMessageTypeEnum.ERROR, ResultTypeMessageEnum.FALSE,
                            "Client " + command.IMEI() + " has not been added to the blacklist. Try again.",
                            command.name() + ": client " + command.IMEI() + " has not been added to the blacklist. Exception: " +
                                    ex.getMessage());
                }
                return commandResult(command.name(), LogMessageTypeEnum.INFO, ResultTypeMessageEnum.TRUE,
                        "Client " + command.IMEI() + " has been added to the blacklist.",
                        command.name() + ": client " + command.IMEI() + " has been added to the blacklist");
            }
            else {
                return commandResult(command.name(), LogMessageTypeEnum.ERROR, ResultTypeMessageEnum.FALSE,
                        "Client " + command.IMEI() + " already in the blacklist.",
                        command.name() + ": client " + command.IMEI() + " already in the blacklist");
            }
        } else
            return commandResult("Unknown", LogMessageTypeEnum.ERROR, ResultTypeMessageEnum.FALSE,
                    "Wrong command format. You are disconnected.", null);
    }

    private CommandResult commandResult(String name, LogMessageTypeEnum type, ResultTypeMessageEnum resultValue, Object message, String logMessage) {
        return producer.getCommandResultAdminCreatorDefaultInstance(name, type, resultValue, message, logMessage)
                .createCommandResult();
    }
}
