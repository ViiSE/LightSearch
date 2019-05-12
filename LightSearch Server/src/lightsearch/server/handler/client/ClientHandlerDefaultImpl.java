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
package lightsearch.server.handler.client;

import java.util.function.Function;
import lightsearch.server.cmd.client.ClientCommand;
import lightsearch.server.cmd.client.ClientCommandConverter;
import lightsearch.server.cmd.client.ClientCommandConverterInit;
import lightsearch.server.exception.CommandConverterException;
import lightsearch.server.cmd.result.CommandResult;
import lightsearch.server.data.ClientDAO;
import lightsearch.server.data.ClientHandlerDTO;
import lightsearch.server.data.ClientParametersHolder;
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
public class ClientHandlerDefaultImpl extends Handler {

    private final ClientParametersHolder clientParametersHolder;
    private final ClientDAO clientDAO;
    private boolean exit = false;
    
    ClientHandlerDefaultImpl(ClientHandlerDTO clientHandlerDTO, 
            LightSearchServerDTO serverDTO, LoggerServer loggerServer) {
        super(serverDTO, clientHandlerDTO.threadParametersHolder(), loggerServer, 
                clientHandlerDTO.currentDateTime(), clientHandlerDTO.threadManager());
        this.clientParametersHolder = clientHandlerDTO.clientParametersHolder();
        this.clientDAO = clientHandlerDTO.clientDAO();
    }

    private void doCommand(MessageSender messageSender, ClientCommand clientCommand) {
        String command = clientCommand.command();
        if(command != null) {
            Function<ClientCommand, CommandResult> processor = 
                    clientParametersHolder.commandHolder().get(command);

            if(processor != null) {
                CommandResult result = processor.apply(clientCommand);
                try {
                    messageSender.sendMessage(result.message());
                    if(result.logMessage().isEmpty())
                        return;
                    if(result.logMessage() != null)
                            super.logger().log(result.type(), super.currentDateTime(), result.logMessage());
                    else
                        exit = true;
                } catch (MessageSenderException ex) {
                    super.logger().log(result.type(), super.currentDateTime(), ex.getMessage());
                }
            }
        }
        else
            exit = true;
    }
    
    @Override
    public void run() {
        String message;
        ClientCommandConverter clientCmdConverter = ClientCommandConverterInit.clientCommandConverter();
        MessageRecipient messageRecipient = MessageRecipientInit.messageRecipient(clientParametersHolder.dataStream().dataInputStream());
        MessageSender messageSender = MessageSenderInit.messageSender(clientParametersHolder.dataStream().dataOutputStream());
        ReceivedClientCommandVerifier receivedCmdVerifier = ReceivedClientCommandVerifierInit.receivedCommandVerifier();
        
        try {
            while(super.serverDTO().clients().containsKey(clientDAO.IMEI()) ||
                    super.threadManager().holder().getThread(super.threadParametersHolder().id()).isWorked()) {
                try {
                    message = messageRecipient.acceptMessage();
                    ClientCommand clientCommand = clientCmdConverter.convertToClientCommand(message);
                    if(clientDAO.isFirst())
                        doCommand(messageSender, clientCommand);
                    else {
                        receivedCmdVerifier.verifyReceivedClientCommand(clientCommand, clientDAO.IMEI());
                        doCommand(messageSender, clientCommand);
                    }
                    
                    if(exit)
                        break;
                    
                } catch(CommandConverterException ex) {
                    super.logger().log(LogMessageTypeEnum.ERROR, super.currentDateTime(),
                            "Client Command: " + ex.getMessage());
                    super.threadManager().interrupt(super.threadParametersHolder().id());
                    break;
                } catch(ReceivedCommandVerifierException ex) {
                    super.logger().log(LogMessageTypeEnum.ERROR, super.currentDateTime(),
                            "Received Client Command Verifier: " + ex.getMessage());
                    super.threadManager().interrupt(super.threadParametersHolder().id());
                    break;
                }
            }
            super.threadManager().holder().getThread(super.threadParametersHolder().id()).setIsDone(true);
            if(exit)
                super.threadManager().interrupt(super.threadParametersHolder().id()); 
        }
        catch(MessageRecipientException ignore) {
            if(!clientDAO.isFirst())
                super.logger().log(LogMessageTypeEnum.INFO, super.currentDateTime(), "Client " + clientDAO.IMEI() + " disconnected");
            super.threadManager().holder().getThread(super.threadParametersHolder().id()).setIsDone(true);
            super.threadManager().interrupt(super.threadParametersHolder().id());                    
        }
        
    }
    
}
