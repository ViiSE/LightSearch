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

import ru.viise.lightsearch.cmd.CommandTypeEnum;
import ru.viise.lightsearch.cmd.result.CommandResult;
import ru.viise.lightsearch.cmd.result.creator.CommandResultCreator;
import ru.viise.lightsearch.cmd.result.creator.CommandResultCreatorInit;
import ru.viise.lightsearch.data.ClientCommandDTO;
import ru.viise.lightsearch.data.CommandCheckBindDTO;
import ru.viise.lightsearch.data.CommandDTO;
import ru.viise.lightsearch.data.ReconnectDTO;
import ru.viise.lightsearch.data.ReconnectDTOInit;
import ru.viise.lightsearch.exception.CommandResultCreatorException;
import ru.viise.lightsearch.exception.MessageRecipientException;
import ru.viise.lightsearch.exception.MessageSenderException;
import ru.viise.lightsearch.message.MessageRecipient;
import ru.viise.lightsearch.message.MessageSender;
import ru.viise.lightsearch.message.type.MessageCheckBind;
import ru.viise.lightsearch.message.type.MessageCheckBindInit;

public class BindCheckProcessor implements Function<CommandDTO, CommandResult> {

    private final String IMEI;
    private final MessageSender msgSender;
    private final MessageRecipient msgRecipient;

    public BindCheckProcessor(ClientCommandDTO clCmdDTO) {
        IMEI = clCmdDTO.IMEI();
        msgSender = clCmdDTO.messageSender();
        msgRecipient = clCmdDTO.messageRecipient();
    }

    @Override
    public CommandResult apply(CommandDTO commandDTO) {
        CommandCheckBindDTO cmdCheckBindDTO = (CommandCheckBindDTO) commandDTO;
        try {
            MessageCheckBind msgCheckBind = MessageCheckBindInit.messageCheckBind(IMEI, cmdCheckBindDTO);
            String message = msgCheckBind.message();
            msgSender.sendMessage(message);
            String rawMessage = msgRecipient.acceptMessage();
            CommandResultCreator cmdResCr = CommandResultCreatorInit.commandResultBindCheckCreator(
                    rawMessage,
                    IMEI,
                    cmdCheckBindDTO.selected(),
                    cmdCheckBindDTO.factoryBarcode());

            return cmdResCr.createCommandResult();
        } catch (CommandResultCreatorException | MessageSenderException | MessageRecipientException ex) {
            return errorCommandResult(ex.getMessageRU(), commandDTO);
        }
    }

    private CommandResult errorCommandResult(String message, CommandDTO cmdCheckBindingDTO) {
        ReconnectDTO recDTO = ReconnectDTOInit.reconnectDTO(cmdCheckBindingDTO, CommandTypeEnum.BIND_CHECK);
        CommandResultCreator cmdResCr = CommandResultCreatorInit
                .commandResultBindCheckCreator(false, message, recDTO);

        try { return cmdResCr.createCommandResult(); }
        catch(CommandResultCreatorException ignore) { return null; /* never happen */ }
    }
}
