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

import lightsearch.server.cmd.client.ClientCommand;
import lightsearch.server.cmd.client.ClientCommandCreator;
import lightsearch.server.cmd.result.CommandResult;
import lightsearch.server.data.*;
import lightsearch.server.handler.Handler;
import lightsearch.server.identifier.ConnectionIdentifierResult;
import lightsearch.server.identifier.HandlerIdentifier;
import lightsearch.server.log.LoggerServer;
import lightsearch.server.producer.cmd.client.ClientCommandCreatorProducer;
import lightsearch.server.producer.data.ClientDAOProducer;
import lightsearch.server.producer.data.ClientHandlerDTOProducer;
import lightsearch.server.producer.data.ClientParametersHolderProducer;
import lightsearch.server.producer.data.ThreadParametersHolderProducer;
import lightsearch.server.producer.handler.ClientHandlerProducer;
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
@Service("handlerCreatorClientProcessor")
@Scope("prototype")
public class HandlerCreatorClientProcessor implements HandlerCreatorProcessor {

    private final ConnectionIdentifierResult identifierResult;
    private final LightSearchServerDTO serverDTO;
    private final LightSearchListenerDTO listenerDTO;
    private final LoggerServer loggerServer;
    private final HandlerIdentifier handlerIdentifier;

    @Autowired private ClientDAOProducer clDAOProducer;
    @Autowired private ClientCommandCreatorProducer clCmdCrProducer;
    @Autowired private ThreadParametersHolderProducer threadParamsHolderProducer;
    @Autowired private ClientParametersHolderProducer clParamsHolderProducer;
    @Autowired private ClientHandlerDTOProducer clHandlerDTOProducer;
    @Autowired private ClientHandlerProducer clHandlerProducer;

    @Autowired
    public HandlerCreatorClientProcessor(
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
        ClientDAO clientDAO = clDAOProducer.getClientDAODefaultInstance();
            
        ClientCommandCreator clientCmdCreator = clCmdCrProducer.getClientCommandCreatorDebugInstance(
                serverDTO, listenerDTO, clientDAO);
        Map<String, Function<ClientCommand, CommandResult>> commandHolder = clientCmdCreator.createCommandHolder();

        String id = LightSearchThreadID.createID(identifierResult.identifier(), handlerIdentifier.next());
        ThreadParametersHolder threadParametersHolder = threadParamsHolderProducer.getThreadParametersHolderDefaultInstance(id);

        ClientParametersHolder clientParamHolder = clParamsHolderProducer.getClientParametersHolderDefaultInstance(
                identifierResult.clientSocket(), identifierResult.dataStream(), commandHolder);

        ClientHandlerDTO clientHandlerDTO = clHandlerDTOProducer.getClientHandlerDTODefaultInstance(
                clientParamHolder, threadParametersHolder, listenerDTO.currentDateTime(), listenerDTO.threadManager(), clientDAO);

        return clHandlerProducer.getClientHandlerDefaultInstance(clientHandlerDTO, serverDTO, loggerServer);
    }
}
