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
package lightsearch.server.handler.system;

import lightsearch.server.cmd.result.CommandResult;
import lightsearch.server.cmd.system.SystemCommand;
import lightsearch.server.cmd.system.SystemCommandConverter;
import lightsearch.server.data.SystemHandlerDTO;
import lightsearch.server.data.SystemParametersHolder;
import lightsearch.server.data.ThreadParametersHolder;
import lightsearch.server.exception.CommandConverterException;
import lightsearch.server.exception.MessageRecipientException;
import lightsearch.server.exception.MessageSenderException;
import lightsearch.server.log.LogMessageTypeEnum;
import lightsearch.server.log.LoggerServer;
import lightsearch.server.message.MessageRecipient;
import lightsearch.server.message.MessageSender;
import lightsearch.server.producer.cmd.system.SystemCommandConverterProducer;
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
@Component("systemHandlerDefault")
@Scope("prototype")
public class SystemHandlerDefaultImpl implements SystemHandler {

    private final LoggerServer logger;
    private final CurrentDateTime currentDateTime;
    private final ThreadManager threadManager;
    private final ThreadParametersHolder threadParametersHolder;
    private final SystemParametersHolder systemParametersHolder;
    private boolean exit = false;

    @Autowired private SystemCommandConverterProducer sysCmdConverterProducer;
    @Autowired private MessageRecipientProducer msgRecipientProducer;
    @Autowired private MessageSenderProducer msgSenderProducer;

    SystemHandlerDefaultImpl(SystemHandlerDTO systemHandlerDTO, LoggerServer loggerServer) {
        this.logger = loggerServer;
        this.currentDateTime = systemHandlerDTO.currentDateTime();
        this.threadManager = systemHandlerDTO.threadManager();
        this.threadParametersHolder = systemHandlerDTO.threadParametersHolder();
        this.systemParametersHolder = systemHandlerDTO.systemParametersHolder();
    }

    private void doCommand(MessageSender messageSender, SystemCommand systemCommand) {
        String command = systemCommand.command();
        if(command != null) {
            Function<SystemCommand, CommandResult> processor;
            processor = systemParametersHolder.commandHolder().get(command);
            
            if(processor != null) {
                CommandResult result = processor.apply(systemCommand);
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
        SystemCommandConverter systemCmdConverter = sysCmdConverterProducer.getSystemCommandConverterDefaultInstance();
        MessageRecipient messageRecipient = msgRecipientProducer.getMessageRecipientProducerDebugInstance(systemParametersHolder.dataStream().dataInputStream());
        MessageSender messageSender = msgSenderProducer.getMessageSenderDefaultInstance(systemParametersHolder.dataStream().dataOutputStream());
        
        try {
            while(threadManager.holder().getThread(threadParametersHolder.id()).isWorked()) {
                try {
                    message = messageRecipient.acceptMessage();
                    SystemCommand systemCommand = systemCmdConverter.convertToSystemCommand(message);
                    doCommand(messageSender, systemCommand);
                    
                    if(exit)
                        break;
                    
                } catch(CommandConverterException ex) {
                    logger.log(LogMessageTypeEnum.ERROR, currentDateTime,
                            "System Command: " + ex.getMessage());
                    threadManager.interrupt(threadParametersHolder.id());
                    break;
                }
            }
            threadManager.holder().getThread(threadParametersHolder.id()).setIsDone(true);
            try {
                systemParametersHolder.systemSocket().close();
            }
            catch(IOException  ignore) {}
            
            if(exit)
                threadManager.interrupt(threadParametersHolder.id());
        }
        catch(MessageRecipientException ignore) {
            logger.log(LogMessageTypeEnum.INFO, currentDateTime, "System bot disconnected");
            threadManager.holder().getThread(threadParametersHolder.id()).setIsDone(true);
            threadManager.interrupt(threadParametersHolder.id());
        }
        
    }

    @Override
    public String id() {
        return threadParametersHolder.id();
    }
}
