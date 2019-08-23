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

import java.io.IOException;
import java.util.function.Function;
import lightsearch.server.cmd.result.CommandResult;
import lightsearch.server.cmd.system.SystemCommand;
import lightsearch.server.cmd.system.SystemCommandConverter;
import lightsearch.server.cmd.system.SystemCommandConverterInit;
import lightsearch.server.data.LightSearchServerDTO;
import lightsearch.server.data.SystemHandlerDTO;
import lightsearch.server.data.SystemParametersHolder;
import lightsearch.server.exception.CommandConverterException;
import lightsearch.server.exception.MessageRecipientException;
import lightsearch.server.exception.MessageSenderException;
import lightsearch.server.handler.Handler;
import lightsearch.server.log.LogMessageTypeEnum;
import lightsearch.server.log.LoggerServer;
import lightsearch.server.message.MessageRecipient;
import lightsearch.server.message.MessageRecipientInit;
import lightsearch.server.message.MessageSender;
import lightsearch.server.message.MessageSenderInit;

/**
 * Обработчик системного бота LightSearch по умолчанию.
 * <p>
 * Получает от бота команду и ее параметры, затем, используя {@link SystemParametersHolder#commandHolder()}, вызывает
 * необходимый обработчик команды и отправляет результат боту.
 * @author ViiSE
 * @since 2.0
 */
public class SystemHandlerDefaultImpl extends Handler {

    private final SystemParametersHolder systemParametersHolder;
    private boolean exit = false;

    SystemHandlerDefaultImpl(SystemHandlerDTO systemHandlerDTO, 
            LightSearchServerDTO serverDTO, LoggerServer loggerServer) {
        super(serverDTO, systemHandlerDTO.threadParametersHolder(), loggerServer, 
                systemHandlerDTO.currentDateTime(), systemHandlerDTO.threadManager());
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
                            super.logger().log(result.type(), super.currentDateTime(), result.logMessage());
                    }
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
        SystemCommandConverter systemCmdConverter = SystemCommandConverterInit.systemCommandConverter();
        MessageRecipient messageRecipient = MessageRecipientInit.messageRecipient(systemParametersHolder.dataStream().dataInputStream());
        MessageSender messageSender = MessageSenderInit.messageSender(systemParametersHolder.dataStream().dataOutputStream());
        
        try {
            while(super.threadManager().holder().getThread(super.threadParametersHolder().id()).isWorked()) {
                try {
                    message = messageRecipient.acceptMessage();
                    SystemCommand systemCommand = systemCmdConverter.convertToSystemCommand(message);
                    doCommand(messageSender, systemCommand);
                    
                    if(exit)
                        break;
                    
                } catch(CommandConverterException ex) {
                    super.logger().log(LogMessageTypeEnum.ERROR, super.currentDateTime(),
                            "System Command: " + ex.getMessage());
                    super.threadManager().interrupt(super.threadParametersHolder().id());
                    break;
                }
            }
            super.threadManager().holder().getThread(super.threadParametersHolder().id()).setIsDone(true);
            try {
                systemParametersHolder.systemSocket().close();
            }
            catch(IOException  ignore) {}
            
            if(exit)
                super.threadManager().interrupt(super.threadParametersHolder().id()); 
        }
        catch(MessageRecipientException ignore) {
            super.logger().log(LogMessageTypeEnum.INFO, super.currentDateTime(), "System bot disconnected");
            super.threadManager().holder().getThread(super.threadParametersHolder().id()).setIsDone(true);
            super.threadManager().interrupt(super.threadParametersHolder().id());                    
        }
        
    }
    
}
