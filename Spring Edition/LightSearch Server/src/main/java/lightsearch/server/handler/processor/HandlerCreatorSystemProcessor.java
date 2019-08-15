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

import lightsearch.server.cmd.result.CommandResult;
import lightsearch.server.cmd.system.SystemCommand;
import lightsearch.server.cmd.system.SystemCommandCreator;
import lightsearch.server.data.*;
import lightsearch.server.handler.Handler;
import lightsearch.server.identifier.ConnectionIdentifierResult;
import lightsearch.server.iterator.HandlerIterator;
import lightsearch.server.log.LoggerServer;
import lightsearch.server.producer.cmd.system.SystemCommandCreatorProducer;
import lightsearch.server.producer.data.SystemHandlerDTOProducer;
import lightsearch.server.producer.data.SystemParametersHolderProducer;
import lightsearch.server.producer.data.ThreadParametersHolderProducer;
import lightsearch.server.producer.handler.SystemHandlerProducer;
import lightsearch.server.thread.LightSearchThreadID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Function;

/**
 *
 * @author ViiSE
 */
@Service("handlerCreatorSystemProcessor")
@Scope("prototype")
public class HandlerCreatorSystemProcessor implements HandlerCreatorProcessor {

    private final ConnectionIdentifierResult identifierResult;
    private final LightSearchListenerDTO listenerDTO;
    private final LoggerServer loggerServer;
    private final HandlerIterator handlerIterator;

    @Autowired private SystemCommandCreatorProducer sysCmdCrProducer;
    @Autowired private ThreadParametersHolderProducer threadParamsHolderProducer;
    @Autowired private SystemParametersHolderProducer sysParamsHolderProducer;
    @Autowired private SystemHandlerDTOProducer sysHandlerDTOProducer;
    @Autowired private SystemHandlerProducer sysHandlerProducer;

    @Autowired
    public HandlerCreatorSystemProcessor(
            ConnectionIdentifierResult identifierResult, LightSearchListenerDTO listenerDTO, LoggerServer loggerServer,
            HandlerIterator handlerIterator) {
        this.identifierResult = identifierResult;
        this.listenerDTO = listenerDTO;
        this.loggerServer = loggerServer;
        this.handlerIterator = handlerIterator;
    }
    
    @Override
    public Handler apply(Void ignore) {
        
        SystemCommandCreator sysCmdCreator = sysCmdCrProducer.getSystemCommandCreatorDefaultInstance();
        Map<String, Function<SystemCommand, CommandResult>> commandHolder = sysCmdCreator.createCommandHolder();

        String id = LightSearchThreadID.createID(identifierResult.identifier(), handlerIterator.next());
        ThreadParametersHolder threadParametersHolder = threadParamsHolderProducer.getThreadParametersHolderDefaultInstance(id);
        
        SystemParametersHolder sysParamHolder = sysParamsHolderProducer.getSystemParametersHolderDefaultInstance(
                identifierResult.clientSocket(), identifierResult.dataStream(), commandHolder);
        
        SystemHandlerDTO systemHandlerDTO = sysHandlerDTOProducer.getSystemHandlerDTODefaultInstance(
                sysParamHolder, threadParametersHolder, listenerDTO.threadManager(), listenerDTO.currentDateTime());
        
        return sysHandlerProducer.getSystemHandlerDefaultInstance(systemHandlerDTO, loggerServer);
    }
    
}
