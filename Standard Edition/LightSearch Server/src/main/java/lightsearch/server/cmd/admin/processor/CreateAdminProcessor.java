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
 * Обработчик команды создания нового администратора LightSearch Server.
 * <p>
 * Создает нового администратора LightSearch Server.
 * @author ViiSE
 * @since 1.0
 */
public class CreateAdminProcessor extends AbstractProcessorAdmin {

    public CreateAdminProcessor(LightSearchServerDTO serverDTO, LightSearchChecker checker) {
        super(serverDTO, checker);
    }

    @Override
    synchronized public CommandResult apply(AdminCommand admCommand) {
        if(!super.checker.isNull(admCommand.name(), admCommand.adminName(), admCommand.password())) {
            if(!super.serverDTO.admins().containsValue(admCommand.adminName())) {
                try(FileOutputStream fout = new FileOutputStream(super.serverDTO.currentDirectory() + "admins", true); 
                        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fout))) {
                    bw.write(admCommand.adminName() + ";" + admCommand.password());
                    bw.newLine();

                    super.serverDTO.admins().put(admCommand.adminName(), admCommand.password());

                    return super.commandResult(admCommand.name(), LogMessageTypeEnum.INFO, ResultTypeMessageEnum.TRUE,
                            "Administrator " + admCommand.adminName() + " is created.",
                            admCommand.name() + " created new administrator - " + admCommand.adminName());                
                }
                catch(IOException ex) {
                    return super.commandResult(admCommand.adminName(), LogMessageTypeEnum.ERROR, ResultTypeMessageEnum.FALSE,
                            "Administrator " + admCommand.adminName() + " is not created. Try again.",
                            admCommand.name() + ": administrator " + admCommand.adminName() + " is not created. Exception: " + ex.getMessage());
                }
            }
            else {
                return super.commandResult(admCommand.name(), LogMessageTypeEnum.ERROR, ResultTypeMessageEnum.FALSE,
                        "Administrator " + admCommand.adminName() + " is already exist.",
                        admCommand.name() + ": administrator " + admCommand.adminName() + " is already exist");
            }
        }
        else
            return super.commandResult("Unknown", LogMessageTypeEnum.ERROR, ResultTypeMessageEnum.FALSE, 
                    "Wrong command format. You are disconnected.", null);
    }
    
}
