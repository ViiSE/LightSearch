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

import java.util.function.Function;
import lightsearch.server.checker.LightSearchChecker;
import lightsearch.server.cmd.SuperAbstractProcessor;
import lightsearch.server.cmd.admin.AdminCommand;
import lightsearch.server.cmd.result.CommandResult;
import lightsearch.server.cmd.result.CommandResultInit;
import lightsearch.server.message.result.MessageCreator;
import lightsearch.server.message.result.MessageCreatorInit;
import lightsearch.server.message.result.type.MessageType;
import lightsearch.server.message.result.type.MessageTypeInit;
import lightsearch.server.data.LightSearchServerDTO;
import lightsearch.server.log.LogMessageTypeEnum;
import lightsearch.server.message.result.ResultTypeMessageEnum;

/**
 *
 * @author ViiSE
 */
public abstract class AbstractProcessorAdmin extends SuperAbstractProcessor implements Function<AdminCommand, CommandResult> {
    
    public AbstractProcessorAdmin(LightSearchServerDTO serverDTO, LightSearchChecker checker) {
        super(serverDTO, checker);
    }
    
    @Override
    protected CommandResult commandResult(String name, LogMessageTypeEnum type, ResultTypeMessageEnum resultValue, Object message, String logMessage) {       
        MessageType messageType = MessageTypeInit.messageTypeJSONAdmin();
        MessageCreator messageCreator = MessageCreatorInit.messageCreator(messageType);
        String messageResult = messageCreator.createMessage(name, resultValue, message);
        return CommandResultInit.commandResult(type, messageResult, logMessage);
    }
}
