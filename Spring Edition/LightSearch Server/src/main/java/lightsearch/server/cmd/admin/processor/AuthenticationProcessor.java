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
import lightsearch.server.data.AdminDAO;
import lightsearch.server.data.LightSearchServerDTO;
import lightsearch.server.log.LogMessageTypeEnum;
import lightsearch.server.message.result.ResultTypeMessageEnum;
import lightsearch.server.producer.cmd.admin.CommandResultAdminCreatorProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 *
 * @author ViiSE
 */
@Component("authenticationProcessorAdmin")
@Scope("prototype")
public class AuthenticationProcessor implements ProcessorAdmin {

    private final LightSearchChecker checker;
    private final List<String> blacklist;
    private final Map<String, String> admins;
    private final AdminDAO adminDAO;

    @Autowired
    private CommandResultAdminCreatorProducer producer;

    public AuthenticationProcessor(LightSearchServerDTO serverDTO, LightSearchChecker checker,
            AdminDAO adminDAO) {
        this.checker = checker;
        this.blacklist = serverDTO.blacklist();
        this.admins = serverDTO.admins();
        this.adminDAO = adminDAO;
    }
    
    @Override
    public CommandResult apply(AdminCommand admCommand) {
        if(!checker.isNull(admCommand.name(), admCommand.password())) {
            if(!blacklist.contains(admCommand.name()) &&
                    admins.containsKey(admCommand.name()) &&
                    admins.containsValue(admCommand.password())) {
                adminDAO.setName(admCommand.name());
                adminDAO.setIsFirst(false);
                return commandResult(admCommand.name(), LogMessageTypeEnum.INFO, ResultTypeMessageEnum.TRUE,
                        "Administrator " + admCommand.name() + " connected.", "Administrator " + admCommand.name() + " connected");
            }
            else
                if(adminDAO.tryNumber() == adminDAO.maxTryNumber())
                    return commandResult(admCommand.name(), LogMessageTypeEnum.ERROR, ResultTypeMessageEnum.FALSE,
                            "Administrator " + admCommand.name() + " - invalid login and/or password, or this user in the blacklist. Disconnect to lightsearch.server.",
                            null);
                else {
                    adminDAO.iterateTryNumber();
                    return commandResult(admCommand.name(), LogMessageTypeEnum.ERROR, ResultTypeMessageEnum.FALSE,
                            "Administrator " + admCommand.name() + " - invalid login and/or password, or this user in the blacklist. " + 
                            (adminDAO.maxTryNumber() - adminDAO.tryNumber()) + " tries left.", 
                            "Administrator " + admCommand.name() + " - invalid login and/or password, or this user in the blacklist. " + 
                            (adminDAO.maxTryNumber() - adminDAO.tryNumber()) + " tries left");
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
