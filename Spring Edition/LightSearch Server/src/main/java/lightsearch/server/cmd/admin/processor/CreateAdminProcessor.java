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
import java.util.Map;

/**
 *
 * @author ViiSE
 */
@Component("createAdminProcessorAdmin")
@Scope("prototype")
public class CreateAdminProcessor implements ProcessorAdmin {

    private final LightSearchChecker checker;
    private final Map<String, String> admins;
    private final String currentDirectory;

    @Autowired
    private CommandResultAdminCreatorProducer producer;

    public CreateAdminProcessor(LightSearchServerDTO serverDTO, LightSearchChecker checker) {
        this.checker = checker;
        this.admins = serverDTO.admins();
        this.currentDirectory = serverDTO.currentDirectory();
    }

    @Override
    synchronized public CommandResult apply(AdminCommand admCommand) {
        if(!checker.isNull(admCommand.name(), admCommand.adminName(), admCommand.password())) {
            if(!admins.containsValue(admCommand.adminName())) {
                try(FileOutputStream fout = new FileOutputStream(currentDirectory + "admins", true);
                        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fout))) {
                    bw.write(admCommand.adminName() + ";" + admCommand.password());
                    bw.newLine();

                    admins.put(admCommand.adminName(), admCommand.password());

                    return commandResult(admCommand.name(), LogMessageTypeEnum.INFO, ResultTypeMessageEnum.TRUE,
                            "Administrator " + admCommand.adminName() + " is created.",
                            admCommand.name() + " created new administrator - " + admCommand.adminName());                
                }
                catch(IOException ex) {
                    return commandResult(admCommand.adminName(), LogMessageTypeEnum.ERROR, ResultTypeMessageEnum.FALSE,
                            "Administrator " + admCommand.adminName() + " is not created. Try again.",
                            admCommand.name() + ": administrator " + admCommand.adminName() + " is not created. Exception: " + ex.getMessage());
                }
            }
            else {
                return commandResult(admCommand.name(), LogMessageTypeEnum.ERROR, ResultTypeMessageEnum.FALSE,
                        "Administrator " + admCommand.adminName() + " is already exist.",
                        admCommand.name() + ": administrator " + admCommand.adminName() + " is already exist");
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
