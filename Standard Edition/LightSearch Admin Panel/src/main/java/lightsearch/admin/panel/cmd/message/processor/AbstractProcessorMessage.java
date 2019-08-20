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
package lightsearch.admin.panel.cmd.message.processor;

import java.util.function.Function;
import lightsearch.admin.panel.cmd.result.CommandResult;
import lightsearch.admin.panel.cmd.result.CommandResultInit;
import lightsearch.admin.panel.data.AdminCommandDAO;
import lightsearch.admin.panel.data.MessageCommandDTO;
import lightsearch.admin.panel.exception.MessageParserException;
import lightsearch.admin.panel.message.parser.MessageParser;
import lightsearch.admin.panel.message.parser.MessageParserInit;

/**
 *
 * @author ViiSE
 */
public abstract class AbstractProcessorMessage implements Function<AdminCommandDAO, CommandResult> {
    
    private final MessageCommandDTO messageCommandDTO;

    public AbstractProcessorMessage(MessageCommandDTO messageCommandDTO) {
        this.messageCommandDTO = messageCommandDTO;
    }
    
    public MessageCommandDTO messageCommandDTO() {
        return messageCommandDTO;
    }
    
    public CommandResult commandResult(String rawMessage) {
        try {
            MessageParser messageParser = MessageParserInit.messageParser();
            Object msgParse = messageParser.parse(rawMessage);
            CommandResult commandResult = CommandResultInit.commandResult(msgParse);
            return commandResult;
        } catch (MessageParserException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
}
