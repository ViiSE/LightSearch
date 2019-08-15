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
package lightsearch.server.handler.admin;

import lightsearch.server.cmd.admin.AdminCommand;
import lightsearch.server.cmd.admin.AdminCommandConverter;
import lightsearch.server.cmd.result.CommandResult;
import lightsearch.server.data.*;
import lightsearch.server.exception.CommandConverterException;
import lightsearch.server.exception.MessageRecipientException;
import lightsearch.server.exception.MessageSenderException;
import lightsearch.server.exception.ReceivedCommandVerifierException;
import lightsearch.server.log.LogMessageTypeEnum;
import lightsearch.server.log.LoggerServer;
import lightsearch.server.message.MessageRecipient;
import lightsearch.server.message.MessageSender;
import lightsearch.server.producer.cmd.admin.AdminCommandConverterProducer;
import lightsearch.server.producer.handler.ReceivedAdminCommandVerifierProducer;
import lightsearch.server.producer.message.MessageRecipientProducer;
import lightsearch.server.producer.message.MessageSenderProducer;
import lightsearch.server.thread.ThreadManager;
import lightsearch.server.time.CurrentDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.function.Function;

/**
 *
 * @author ViiSE
 */
@Component("adminHandlerDefault")
@Scope("prototype")
public class AdminHandlerDefaultImpl implements AdminHandler {

    private final ThreadParametersHolder threadParametersHolder;
    private final LoggerServer logger;
    private final CurrentDateTime currentDateTime;
    private final ThreadManager threadManager;
    private final AdminParametersHolder admParamHolder;
    private final AdminDAO adminDAO;
    private boolean exit = false;

    @Autowired private AdminCommandConverterProducer admCmdConverterProducer;
    @Autowired private MessageRecipientProducer msgRecipientProducer;
    @Autowired private MessageSenderProducer msgSenderProducer;
    @Autowired private ReceivedAdminCommandVerifierProducer recAdmCmdVerifierProducer;

    AdminHandlerDefaultImpl(AdminHandlerDTO handlerDTO, LoggerServer loggerServer) {
        this.threadParametersHolder = handlerDTO.threadParametersHolder();
        this.logger = loggerServer;
        this.currentDateTime = handlerDTO.currentDateTime();
        this.threadManager = handlerDTO.threadManager();
        this.admParamHolder = handlerDTO.adminParametersHolder();
        this.adminDAO = handlerDTO.adminDAO();
    }
    
    private void doCommand(MessageSender messageSender, AdminCommand admCommand) {
        String command = admCommand.command();
        if(command != null) {
            Function<AdminCommand, CommandResult> processor = 
                    admParamHolder.commandHolder().get(command);

            if(processor != null) {
                CommandResult result = processor.apply(admCommand);
                try {
                    messageSender.sendMessage(result.message());
                    if(result.logMessage() != null)
                        logger.log(result.type(), currentDateTime, result.logMessage());
                    else
                        exit = true;
                } catch (MessageSenderException ex) {
                    logger.log(result.type(), currentDateTime, ex.getMessage());
                }
            }
        }
    }
    
    @Override
    public void run() {
        String message;
        AdminCommandConverter admCmdConverter = admCmdConverterProducer.getAdminCommandConverterDefaultInstance();
        MessageRecipient messageRecipient = msgRecipientProducer.getMessageRecipientProducerDebugInstance(admParamHolder.dataStream().dataInputStream());
        MessageSender messageSender = msgSenderProducer.getMessageSenderDefaultInstance(admParamHolder.dataStream().dataOutputStream());
        ReceivedAdminCommandVerifier receivedCmdVerifier = recAdmCmdVerifierProducer.getReceivedAdminCommandVerifierDefaultInstance();
        try {
            while(threadManager.holder().getThread(threadParametersHolder.id()).isWorked()) {
                try {
                    message = messageRecipient.acceptMessage();
                    AdminCommand admCommand = admCmdConverter.convertToAdminCommand(message);
                    if(adminDAO.isFirst())
                        doCommand(messageSender, admCommand);
                    else {
                        receivedCmdVerifier.verifyReceivedAdminCommand(admCommand, adminDAO.name());
                        doCommand(messageSender, admCommand);
                    }
                    
                    if(exit)
                        break;
                    
                } catch(CommandConverterException ex) {
                    logger.log(LogMessageTypeEnum.ERROR, currentDateTime,
                            "Admin Command: " + ex.getMessage());
                    threadManager.interrupt(threadParametersHolder.id());
                    break;
                } catch(ReceivedCommandVerifierException ex) {
                    logger.log(LogMessageTypeEnum.ERROR, currentDateTime,
                            "Received Admin Command Verifier: " + ex.getMessage());
                    threadManager.interrupt(threadParametersHolder.id());
                    break;
                }
            }
            admParamHolder.adminSocket().close();
            threadManager.holder().getThread(threadParametersHolder.id()).setIsDone(true);
            if(exit)
                threadManager.interrupt(threadParametersHolder.id());
        }
        catch(MessageRecipientException |
                IOException ignore) {
            if(!adminDAO.isFirst())
                logger.log(LogMessageTypeEnum.INFO, currentDateTime, "Administrator " + adminDAO.name() + " disconnected");
            threadManager.holder().getThread(threadParametersHolder.id()).setIsDone(true);
            threadManager.interrupt(threadParametersHolder.id());
        }
    }

    @Override
    public String id() {
        return threadParametersHolder.id();
    }
}
