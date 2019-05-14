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
import ru.viise.lightsearch.cmd.result.CommandResultCreator;
import ru.viise.lightsearch.cmd.result.CommandResultCreatorInit;
import ru.viise.lightsearch.data.ClientCommandDTO;
import ru.viise.lightsearch.data.CommandDTO;
import ru.viise.lightsearch.data.CommandOpenSoftCheckDTO;
import ru.viise.lightsearch.exception.MessageRecipientException;
import ru.viise.lightsearch.exception.MessageSenderException;
import ru.viise.lightsearch.message.MessageRecipient;
import ru.viise.lightsearch.message.MessageSender;
import ru.viise.lightsearch.message.type.MessageOpenSoftCheck;
import ru.viise.lightsearch.message.type.MessageOpenSoftCheckInit;

public class OpenSoftCheckProcessor implements Function<CommandDTO, CommandResult> {

    private final String IMEI;
    private final MessageSender msgSender;
    private final MessageRecipient msgRecipient;

    public OpenSoftCheckProcessor(ClientCommandDTO clCmdDTO) {
        IMEI = clCmdDTO.IMEI();
        msgSender = clCmdDTO.messageSender();
        msgRecipient = clCmdDTO.messageRecipient();
    }

    @Override
    public CommandResult apply(CommandDTO commandDTO) {
        try {
            CommandOpenSoftCheckDTO cmdOSCDTO = (CommandOpenSoftCheckDTO) commandDTO;
            MessageOpenSoftCheck msgOpenSC = MessageOpenSoftCheckInit.messageOpenSoftCheck(IMEI, cmdOSCDTO);
            String message = msgOpenSC.message();
            msgSender.sendMessage(message);
            String rawMessage = msgRecipient.acceptMessage();
            CommandResultCreator cmdResCr =
                    CommandResultCreatorInit.commandResultOpenSoftCheckCreator(rawMessage, IMEI);
            CommandResult cmdRes = cmdResCr.createCommandResult();
            if(cmdRes != null)
                return cmdRes;
            else {
                String messageErr = "Произошла ошибка при обработке сообщения. " +
                        "Для устранения проблемы обратитесь к администратору." +
                        "Вы отключены от сервера";
                return errorCommandResult(messageErr);
            }
        }
        catch(MessageSenderException ex) {
            String message = "Произошла ошибка при отправки сообщения. " +
                    "Для устранения проблемы обратитесь к администратору." +
                    "Вы отключены от сервера";
            return errorCommandResult(message);
        }
        catch(MessageRecipientException ex) {
            String message = "Произошла ошибка при принятии сообщения. " +
                    "Для устранения проблемы обратитесь к администратору." +
                    "Вы отключены от сервера";
            return errorCommandResult(message);
        }
    }

    private CommandResult errorCommandResult(String message) {
        CommandResultCreator cmdResCr =
                CommandResultCreatorInit.commandResultOpenSoftCheckCreator(false, message);
        return cmdResCr.createCommandResult();
    }
}
