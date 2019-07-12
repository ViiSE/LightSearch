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

package ru.viise.lightsearch.cmd.processor;

import java.util.function.Function;

import ru.viise.lightsearch.cmd.result.CommandResult;
import ru.viise.lightsearch.cmd.result.creator.CommandResultCreator;
import ru.viise.lightsearch.cmd.result.creator.CommandResultCreatorInit;
import ru.viise.lightsearch.data.ClientCommandDTO;
import ru.viise.lightsearch.data.CommandDTO;
import ru.viise.lightsearch.data.CommandSearchDTO;
import ru.viise.lightsearch.data.CommandSearchSoftCheckDTO;
import ru.viise.lightsearch.exception.CommandResultCreatorException;
import ru.viise.lightsearch.exception.MessageRecipientException;
import ru.viise.lightsearch.exception.MessageSenderException;
import ru.viise.lightsearch.message.MessageRecipient;
import ru.viise.lightsearch.message.MessageSender;
import ru.viise.lightsearch.message.type.MessageSearch;
import ru.viise.lightsearch.message.type.MessageSearchInit;

public class SearchProcessor implements Function<CommandDTO, CommandResult> {

    private final String IMEI;
    private final MessageSender msgSender;
    private final MessageRecipient msgRecipient;

    public SearchProcessor(ClientCommandDTO clCmdDTO) {
        IMEI = clCmdDTO.IMEI();
        msgSender = clCmdDTO.messageSender();
        msgRecipient = clCmdDTO.messageRecipient();
    }

    @Override
    public CommandResult apply(CommandDTO commandDTO) {
        try {
            CommandSearchDTO cmdSearchDTO = (CommandSearchDTO) commandDTO;
            MessageSearch msgSearch = MessageSearchInit.messageSearch(IMEI, cmdSearchDTO);
            String message = msgSearch.message();
            msgSender.sendMessage(message);
            String rawMessage = msgRecipient.acceptMessage();
            CommandResultCreator cmdResCr;
            if(cmdSearchDTO instanceof CommandSearchSoftCheckDTO) {
                cmdResCr = CommandResultCreatorInit.commandResultSearchSoftCheckCreator(rawMessage, IMEI);
            }
            else {
                String subdivision = cmdSearchDTO.subdivision();
                cmdResCr = CommandResultCreatorInit.commandResultSearchCreator(rawMessage, IMEI, subdivision);
            }
            return cmdResCr.createCommandResult();
        }
        catch (CommandResultCreatorException ex) {
            return errorCommandResult(ex.getMessageRU(), false, (CommandSearchDTO)commandDTO);
        }
        catch(MessageSenderException | MessageRecipientException ex) {
            return errorCommandResult(ex.getMessageRU(), true, (CommandSearchDTO)commandDTO);
        }
    }

    private CommandResult errorCommandResult(String message, boolean isReconnect, CommandSearchDTO cmdSearchDTO) {
        CommandResultCreator cmdResCr;
        if(cmdSearchDTO instanceof CommandSearchSoftCheckDTO)
            cmdResCr = CommandResultCreatorInit.commandResultSearchSoftCheckCreator(false,
                    isReconnect ,message);
        else
            cmdResCr = CommandResultCreatorInit.commandResultSearchCreator(false, isReconnect,
                    message);

        try { return cmdResCr.createCommandResult(); }
        catch(CommandResultCreatorException ignore) { return null; /* never happen */ }
    }
}
