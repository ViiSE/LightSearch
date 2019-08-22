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

import lightsearch.server.cmd.admin.AdminCommand;
import lightsearch.server.cmd.admin.AdminCommandCreator;
import lightsearch.server.cmd.result.CommandResult;
import lightsearch.server.data.*;
import lightsearch.server.handler.Handler;
import lightsearch.server.identifier.ConnectionIdentifierResult;
import lightsearch.server.identifier.HandlerIdentifier;
import lightsearch.server.log.LoggerServer;
import lightsearch.server.producer.data.AdminParametersHolderProducer;
import lightsearch.server.producer.cmd.admin.AdminCommandCreatorProducer;
import lightsearch.server.producer.data.AdminDAOProducer;
import lightsearch.server.producer.data.AdminHandlerDTOProducer;
import lightsearch.server.producer.data.ThreadParametersHolderProducer;
import lightsearch.server.producer.handler.AdminHandlerProducer;
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
@Service("handlerCreatorAdminProcessor")
@Scope("prototype")
public class HandlerCreatorAdminProcessor implements HandlerCreatorProcessor {

    private final ConnectionIdentifierResult identifierResult;
    private final LightSearchServerDTO serverDTO;
    private final LightSearchListenerDTO listenerDTO;
    private final LoggerServer loggerServer;
    private final HandlerIdentifier handlerIdentifier;

    @Autowired private AdminDAOProducer admDAOProducer;
    @Autowired private AdminCommandCreatorProducer admCmdCrProducer;
    @Autowired private ThreadParametersHolderProducer threadParamsHolderProducer;
    @Autowired private AdminParametersHolderProducer admParamsHolderProducer;
    @Autowired private AdminHandlerDTOProducer admHandlerDTOProducer;
    @Autowired private AdminHandlerProducer admHandlerProducer;

    @Autowired
    public HandlerCreatorAdminProcessor(
            ConnectionIdentifierResult identifierResult, LightSearchServerDTO serverDTO, LightSearchListenerDTO listenerDTO,
            LoggerServer loggerServer, HandlerIdentifier handlerIdentifier) {
        this.identifierResult = identifierResult;
        this.serverDTO = serverDTO;
        this.listenerDTO = listenerDTO;
        this.loggerServer = loggerServer;
        this.handlerIdentifier = handlerIdentifier;
    }
    
    @Override
    public Handler apply(Void ignore) {
        AdminDAO adminDAO = admDAOProducer.getAdminDAODefaultInstance();
            
        AdminCommandCreator admCmdCreator = admCmdCrProducer.getAdminCommandCreatorDefaultInstance(
                serverDTO, listenerDTO, loggerServer, adminDAO);
        Map<String, Function<AdminCommand, CommandResult>> commandHolder = admCmdCreator.createCommandHolder();
            
        String id = LightSearchThreadID.createID(identifierResult.identifier(), handlerIdentifier.next());
            
        ThreadParametersHolder threadParametersHolder = threadParamsHolderProducer.getThreadParametersHolderDefaultInstance(id);
            
        AdminParametersHolder admParamHolder = admParamsHolderProducer.getAdminParametersHolderDefaultInstance(
                identifierResult.clientSocket(), identifierResult.dataStream(), commandHolder);
            
        AdminHandlerDTO admHandlerDTO = admHandlerDTOProducer.getAdminHandlerDTODefaultInstance(
                admParamHolder, threadParametersHolder, listenerDTO.currentDateTime(), listenerDTO.threadManager(), adminDAO);
            
        return admHandlerProducer.getAdminHandlerDefaultDefaultInstance(admHandlerDTO, serverDTO, loggerServer);
    }
    
}
