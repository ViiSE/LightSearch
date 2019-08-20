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
package lightsearch.server.handler.processor;

import java.util.Map;
import java.util.function.Function;
import lightsearch.server.cmd.result.CommandResult;
import lightsearch.server.cmd.system.SystemCommand;
import lightsearch.server.cmd.system.SystemCommandCreator;
import lightsearch.server.cmd.system.SystemCommandCreatorInit;
import lightsearch.server.data.LightSearchListenerDTO;
import lightsearch.server.data.LightSearchServerDTO;
import lightsearch.server.data.SystemHandlerDTO;
import lightsearch.server.data.SystemHandlerDTOInit;
import lightsearch.server.data.SystemParametersHolder;
import lightsearch.server.data.SystemParametersHolderInit;
import lightsearch.server.data.ThreadParametersHolder;
import lightsearch.server.data.ThreadParametersHolderInit;
import lightsearch.server.handler.Handler;
import lightsearch.server.handler.system.SystemHandlerInit;
import lightsearch.server.identifier.ConnectionIdentifierResult;
import lightsearch.server.iterator.HandlerIterator;
import lightsearch.server.log.LoggerServer;
import lightsearch.server.thread.LightSearchThreadID;

/**
 *
 * @author ViiSE
 */
public class HandlerCreatorSystemProcessor extends SuperHandlerCreatorProcessor {
    
    public HandlerCreatorSystemProcessor(ConnectionIdentifierResult identifierResult, 
            LightSearchServerDTO serverDTO, LightSearchListenerDTO listenerDTO, 
            LoggerServer loggerServer, HandlerIterator handlerIterator) {
        super(identifierResult, serverDTO, listenerDTO, loggerServer, handlerIterator);
    }
    
    @Override
    public Handler apply(Void ignore) {
        
        SystemCommandCreator sysCmdCreator = SystemCommandCreatorInit.systemCommandCreator(
                super.serverDTO(), super.listenerDTO());
        Map<String, Function<SystemCommand, CommandResult>> commandHolder = sysCmdCreator.createCommandHolder();

        String id = LightSearchThreadID.createID(super.identifierResult().identifier(), super.handlerIterator().next());
        ThreadParametersHolder threadParametersHolder = ThreadParametersHolderInit.threadParametersHolder(id);
        
        SystemParametersHolder sysParamHolder = 
                SystemParametersHolderInit.systemParametersHolder(
                        super.identifierResult().clientSocket(), 
                        super.identifierResult().dataStream(), commandHolder);
        
        SystemHandlerDTO systemHandlerDTO = SystemHandlerDTOInit.systemHandlerDTO(
                sysParamHolder, threadParametersHolder, 
                super.listenerDTO().threadManager(), super.listenerDTO().currentDateTime());

        return SystemHandlerInit.systemHandler(systemHandlerDTO, super.serverDTO(), super.loggerServer());
    }
    
}
