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
import ru.viise.lightsearch.data.CommandConfirmCartRecordsDTO;
import ru.viise.lightsearch.data.CommandConfirmSoftCheckRecordsDTO;
import ru.viise.lightsearch.data.CommandDTO;
import ru.viise.lightsearch.data.ReconnectDTO;
import ru.viise.lightsearch.data.ReconnectDTOInit;
import ru.viise.lightsearch.exception.CommandResultCreatorException;
import ru.viise.lightsearch.exception.MessageRecipientException;
import ru.viise.lightsearch.exception.MessageSenderException;
import ru.viise.lightsearch.message.MessageRecipient;
import ru.viise.lightsearch.message.MessageSender;
import ru.viise.lightsearch.message.type.MessageConfirmSoftCheckProducts;
import ru.viise.lightsearch.message.type.MessageConfirmSoftCheckProductsInit;

public class ConfirmSoftCheckProductsProcessor implements Function<CommandDTO, CommandResult> {

    private final String IMEI;
    private final MessageSender msgSender;
    private final MessageRecipient msgRecipient;

    public ConfirmSoftCheckProductsProcessor(ClientCommandDTO clCmdDTO) {
        IMEI = clCmdDTO.IMEI();
        msgSender = clCmdDTO.messageSender();
        msgRecipient = clCmdDTO.messageRecipient();
    }

    @Override
    public CommandResult apply(CommandDTO commandDTO) {
        CommandConfirmSoftCheckRecordsDTO cmdConSCRecDTO = (CommandConfirmSoftCheckRecordsDTO) commandDTO;
        try {
            MessageConfirmSoftCheckProducts msgConSCProd
                    = MessageConfirmSoftCheckProductsInit.messageConfirmSoftCheckProducts(IMEI,
                        cmdConSCRecDTO);
            String message = msgConSCProd.message();
            msgSender.sendMessage(message);
            String rawMessage = msgRecipient.acceptMessage();
            CommandResultCreator cmdResCr;
            if(cmdConSCRecDTO instanceof CommandConfirmCartRecordsDTO) {
                cmdResCr = CommandResultCreatorInit.commandResultConfirmCartProductsCreator(
                        rawMessage, IMEI, cmdConSCRecDTO.softCheckRecords());
            }
            else {
                cmdResCr = CommandResultCreatorInit.commandResultConfirmSoftCheckProductsCreator(
                        rawMessage, IMEI, cmdConSCRecDTO.softCheckRecords());
            }
            CommandResult cmdRes = cmdResCr.createCommandResult();
            return cmdRes;
        }
        catch(CommandResultCreatorException ex) {
            return errorCommandResult(ex.getMessageRU(), cmdConSCRecDTO);
        }
        catch(MessageSenderException | MessageRecipientException ex) {
            return errorCommandResult(ex.getMessageRU(), cmdConSCRecDTO);
        }
    }

    private CommandResult errorCommandResult(String message, CommandDTO cmdConCRecDTO) {
        ReconnectDTO recDTO = ReconnectDTOInit.reconnectDTO(cmdConCRecDTO, CommandTypeEnum.CONFIRM_SOFT_CHECK_PRODUCTS);
        CommandResultCreator cmdResCr;
        if(cmdConCRecDTO instanceof CommandConfirmCartRecordsDTO)
            cmdResCr = CommandResultCreatorInit.commandResultConfirmCartProductsCreator(false, message,
                    recDTO);
        else
            cmdResCr = CommandResultCreatorInit.commandResultConfirmSoftCheckProductsCreator(false,
                    message, recDTO);

        try { return cmdResCr.createCommandResult(); }
        catch(CommandResultCreatorException ignore) { return null; /* never happen */ }
    }
}
