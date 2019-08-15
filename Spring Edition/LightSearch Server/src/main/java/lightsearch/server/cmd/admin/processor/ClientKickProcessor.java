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

import java.util.Map;

/**
 *
 * @author ViiSE
 */
@Component("clientKickProcessorAdmin")
@Scope("prototype")
public class ClientKickProcessor implements ProcessorAdmin {

    private final LightSearchChecker checker;
    private final Map<String, String> clients;

    @Autowired
    private CommandResultAdminCreatorProducer producer;

    public ClientKickProcessor(LightSearchServerDTO serverDTO, LightSearchChecker checker) {
        this.checker = checker;
        this.clients = serverDTO.clients();
    }

    @Override
    public CommandResult apply(AdminCommand admCommand) {
        if(!checker.isNull(admCommand.IMEI(), admCommand.name())) {
            if(!admCommand.IMEI().equals("") && clients.remove(admCommand.IMEI()) != null) {

                return commandResult(admCommand.name(), LogMessageTypeEnum.INFO, ResultTypeMessageEnum.TRUE,
                        "Client " + admCommand.IMEI() + " is kicked.",
                        admCommand.name() + " kicked client. IMEI: " + admCommand.IMEI());
            }
            else {
                return commandResult(admCommand.name(), LogMessageTypeEnum.ERROR, ResultTypeMessageEnum.FALSE,
                        "Client " + admCommand.IMEI() + " does not exist.",
                        admCommand.name() + ": client does not exist. IMEI: " + admCommand.IMEI());
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
