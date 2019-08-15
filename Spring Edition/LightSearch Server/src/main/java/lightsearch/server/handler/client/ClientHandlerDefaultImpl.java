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

import lightsearch.server.cmd.client.ClientCommand;
import lightsearch.server.cmd.client.ClientCommandConverter;
import lightsearch.server.cmd.client.ClientCommandEnum;
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
import lightsearch.server.producer.cmd.client.ClientCommandConverterProducer;
import lightsearch.server.producer.handler.ReceivedClientCommandVerifierProducer;
import lightsearch.server.producer.message.MessageRecipientProducer;
import lightsearch.server.producer.message.MessageSenderProducer;
import lightsearch.server.thread.ThreadManager;
import lightsearch.server.time.CurrentDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.function.Function;

/**
 *
 * @author ViiSE
 */
@Component("clientHandlerDefault")
@Scope("prototype")
public class ClientHandlerDefaultImpl implements ClientHandler {

    private final ThreadParametersHolder threadParametersHolder;
    private final Map<String, String> clients;
    private final LoggerServer logger;
    private final CurrentDateTime currentDateTime;
    private final ThreadManager threadManager;
    private final ClientParametersHolder clientParametersHolder;
    private final ClientDAO clientDAO;
    private boolean exit = false;

    @Autowired private ClientCommandConverterProducer clCmdConverterProducer;
    @Autowired private MessageRecipientProducer msgRecipientProducer;
    @Autowired private MessageSenderProducer msgSenderProducer;
    @Autowired private ReceivedClientCommandVerifierProducer recClCmdVerifierProducer;

    ClientHandlerDefaultImpl(ClientHandlerDTO clientHandlerDTO, LightSearchServerDTO serverDTO, LoggerServer loggerServer) {
        this.threadParametersHolder = clientHandlerDTO.threadParametersHolder();
        this.clients = serverDTO.clients();
        this.logger = loggerServer;
        this.currentDateTime = clientHandlerDTO.currentDateTime();
        this.threadManager = clientHandlerDTO.threadManager();
        this.clientParametersHolder = clientHandlerDTO.clientParametersHolder();
        this.clientDAO = clientHandlerDTO.clientDAO();
    }

    private void doCommand(MessageSender messageSender, ClientCommand clientCommand, boolean isFirst) {
        String command = clientCommand.command();
        if(command != null) {
            Function<ClientCommand, CommandResult> processor;
            if(isFirst)
                processor = clientParametersHolder.commandHolder().get(ClientCommandEnum.CONNECT.stringValue());
            else
                processor = clientParametersHolder.commandHolder().get(command);
            
            if(processor != null) {
                CommandResult result = processor.apply(clientCommand);
                try {
                    messageSender.sendMessage(result.message());
                    if(result.logMessage() != null) {
                        if(!result.logMessage().isEmpty())
                            logger.log(result.type(), currentDateTime, result.logMessage());
                    }
                    else
                        exit = true;
                } catch (MessageSenderException ex) {
                    logger.log(result.type(), currentDateTime, ex.getMessage());
                }
            }
        }
        else
            exit = true;
    }
    
    @Override
    public void run() {
        String message;
        ClientCommandConverter clientCmdConverter = clCmdConverterProducer.getClientCommandConverterDefaultInstance();
        MessageRecipient messageRecipient = msgRecipientProducer.getMessageRecipientProducerDebugInstance(clientParametersHolder.dataStream().dataInputStream());
        MessageSender messageSender = msgSenderProducer.getMessageSenderDefaultInstance(clientParametersHolder.dataStream().dataOutputStream());
        ReceivedClientCommandVerifier receivedCmdVerifier = recClCmdVerifierProducer.getReceivedClientCommandVerifierDefaultInstance();
        
        try {
            while(threadManager.holder().getThread(threadParametersHolder.id()).isWorked()) {
                try {
                    message = messageRecipient.acceptMessage();
                    ClientCommand clientCommand = clientCmdConverter.convertToClientCommand(message);
                    if(clientDAO.isFirst())
                        doCommand(messageSender, clientCommand, clientDAO.isFirst());
                    else {
                        if(clients.containsKey(clientDAO.IMEI())) {
                            receivedCmdVerifier.verifyReceivedClientCommand(clientCommand, clientDAO.IMEI());
                            doCommand(messageSender, clientCommand, clientDAO.isFirst());
                        }
                        else
                            exit = true;
                    }
                    
                    if(exit)
                        break;
                    
                } catch(CommandConverterException ex) {
                    logger.log(LogMessageTypeEnum.ERROR, currentDateTime,
                            "Client Command: " + ex.getMessage());
                    threadManager.interrupt(threadParametersHolder.id());
                    break;
                } catch(ReceivedCommandVerifierException ex) {
                    logger.log(LogMessageTypeEnum.ERROR, currentDateTime,
                            "Received Client Command Verifier: " + ex.getMessage());
                    threadManager.interrupt(threadParametersHolder.id());
                    break;
                }
            }
            threadManager.holder().getThread(threadParametersHolder.id()).setIsDone(true);
            try {
                clientParametersHolder.clientSocket().close();
                if(clientDAO.databaseConnection() != null)
                    clientDAO.databaseConnection().connection().close();
            }
            catch(IOException  ignore) {}
            catch(SQLException ex) { System.out.println("SQL! ::" + ex.getMessage()); }
            
            if(exit)
                threadManager.interrupt(threadParametersHolder.id());
        }
        catch(MessageRecipientException ignore) {
            if(!clientDAO.isFirst())
                logger.log(LogMessageTypeEnum.INFO, currentDateTime, "Client " + clientDAO.IMEI() + " disconnected");
            threadManager.holder().getThread(threadParametersHolder.id()).setIsDone(true);
            threadManager.interrupt(threadParametersHolder.id());
        }
    }

    @Override
    public String id() {
        return threadParametersHolder.id();
    }
}
