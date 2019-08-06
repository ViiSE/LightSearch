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

import java.io.IOException;
import java.util.function.Function;
import lightsearch.server.cmd.admin.AdminCommand;
import lightsearch.server.cmd.admin.AdminCommandConverter;
import lightsearch.server.cmd.admin.AdminCommandConverterInit;
import lightsearch.server.exception.CommandConverterException;
import lightsearch.server.cmd.result.CommandResult;
import lightsearch.server.data.AdminDAO;
import lightsearch.server.data.AdminHandlerDTO;
import lightsearch.server.data.AdminParametersHolder;
import lightsearch.server.handler.Handler;
import lightsearch.server.exception.ReceivedCommandVerifierException;
import lightsearch.server.log.LoggerServer;
import lightsearch.server.message.MessageRecipient;
import lightsearch.server.message.MessageRecipientInit;
import lightsearch.server.message.MessageSender;
import lightsearch.server.message.MessageSenderInit;
import lightsearch.server.exception.MessageRecipientException;
import lightsearch.server.exception.MessageSenderException;
import lightsearch.server.data.LightSearchServerDTO;
import lightsearch.server.log.LogMessageTypeEnum;

/**
 *
 * @author ViiSE
 */
public class AdminHandlerDefaultImpl extends Handler {

    private final AdminParametersHolder admParamHolder;
    private final AdminDAO adminDAO;
    private boolean exit = false;
    
    AdminHandlerDefaultImpl(AdminHandlerDTO handlerDTO, LightSearchServerDTO serverDTO, 
            LoggerServer loggerServer) {
        super(serverDTO, handlerDTO.threadParametersHolder(), loggerServer, 
                handlerDTO.currentDateTime(), handlerDTO.threadManager());
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
                        super.logger().log(result.type(), super.currentDateTime(), result.logMessage());
                    else
                        exit = true;
                } catch (MessageSenderException ex) {
                    super.logger().log(result.type(), super.currentDateTime(), ex.getMessage());
                }
            }
        }
    }
    
    @Override
    public void run() {
        String message;
        AdminCommandConverter admCmdConverter = AdminCommandConverterInit.adminCommandConverter();            
        MessageRecipient messageRecipient = MessageRecipientInit.messageRecipient(admParamHolder.dataStream().dataInputStream());
        MessageSender messageSender = MessageSenderInit.messageSender(admParamHolder.dataStream().dataOutputStream());
        ReceivedAdminCommandVerifier receivedCmdVerifier = ReceivedAdminCommandVerifierInit.receivedCommandVerifier();                
        try {
            while(super.threadManager().holder().getThread(super.threadParametersHolder().id()).isWorked()) {
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
                    super.logger().log(LogMessageTypeEnum.ERROR, super.currentDateTime(),
                            "Admin Command: " + ex.getMessage());
                    super.threadManager().interrupt(super.threadParametersHolder().id());
                    break;
                } catch(ReceivedCommandVerifierException ex) {
                    super.logger().log(LogMessageTypeEnum.ERROR, super.currentDateTime(),
                            "Received Admin Command Verifier: " + ex.getMessage());
                    super.threadManager().interrupt(super.threadParametersHolder().id());
                    break;
                }
            }
            admParamHolder.adminSocket().close();
            super.threadManager().holder().getThread(super.threadParametersHolder().id()).setIsDone(true);
            if(exit)
                super.threadManager().interrupt(super.threadParametersHolder().id());    
        }
        catch(MessageRecipientException |
                IOException ignore) {
            if(!adminDAO.isFirst())
                super.logger().log(LogMessageTypeEnum.INFO, super.currentDateTime(), "Administrator " + adminDAO.name() + " disconnected");
            super.threadManager().holder().getThread(super.threadParametersHolder().id()).setIsDone(true);
            super.threadManager().interrupt(super.threadParametersHolder().id());                    
        }
    }
}
