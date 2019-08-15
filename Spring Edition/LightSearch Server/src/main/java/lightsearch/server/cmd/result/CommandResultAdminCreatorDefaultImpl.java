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

package lightsearch.server.cmd.result;

import lightsearch.server.log.LogMessageTypeEnum;
import lightsearch.server.message.result.MessageCreator;
import lightsearch.server.message.result.ResultTypeMessageEnum;
import lightsearch.server.message.result.type.MessageType;
import lightsearch.server.producer.cmd.result.CommandResultProducer;
import lightsearch.server.producer.message.MessageCreatorProducer;
import lightsearch.server.producer.message.MessageTypeProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service("commandResultAdminCreatorDefault")
@Scope("prototype")
public class CommandResultAdminCreatorDefaultImpl implements CommandResultAdminCreator {

    private final String name;
    private final LogMessageTypeEnum type;
    private final ResultTypeMessageEnum resultValue;
    private final Object message;
    private final String logMessage;

    @Autowired private CommandResultProducer cmdResProducer;
    @Autowired private MessageTypeProducer msgTypeProducer;
    @Autowired private MessageCreatorProducer msgCrProducer;
    
    public CommandResultAdminCreatorDefaultImpl(String name, LogMessageTypeEnum type, ResultTypeMessageEnum resultValue,
                                                Object message, String logMessage) {
        this.name = name;
        this.type = type;
        this.resultValue = resultValue;
        this.message = message;
        this.logMessage = logMessage;
    }

    @Override
    public CommandResult createCommandResult() {
        MessageType messageType = msgTypeProducer.getMessageTypeJSONAdminDefaultImpl();
        MessageCreator messageCreator = msgCrProducer.getMessageCreatorDefaultInstance(messageType);
        String messageResult = messageCreator.createMessage(name, resultValue, message);

        return cmdResProducer.getCommandResultDefaultInstance(type, messageResult, logMessage);
    }
}
