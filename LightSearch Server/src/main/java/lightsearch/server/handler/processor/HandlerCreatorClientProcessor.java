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
import lightsearch.server.cmd.client.ClientCommand;
import lightsearch.server.cmd.client.ClientCommandCreator;
import lightsearch.server.cmd.client.ClientCommandCreatorInit;
import lightsearch.server.cmd.result.CommandResult;
import lightsearch.server.data.ClientDAO;
import lightsearch.server.data.ClientDAOInit;
import lightsearch.server.data.ClientHandlerDTO;
import lightsearch.server.data.ClientHandlerDTOInit;
import lightsearch.server.data.ClientParametersHolder;
import lightsearch.server.data.ClientParametersHolderInit;
import lightsearch.server.data.LightSearchListenerDTO;
import lightsearch.server.data.LightSearchServerDTO;
import lightsearch.server.data.ThreadParametersHolder;
import lightsearch.server.data.ThreadParametersHolderInit;
import lightsearch.server.handler.Handler;
import lightsearch.server.handler.client.ClientHandlerInit;
import lightsearch.server.identifier.ConnectionIdentifierResult;
import lightsearch.server.iterator.HandlerIterator;
import lightsearch.server.log.LoggerServer;
import lightsearch.server.thread.LightSearchThreadID;

/**
 *
 * @author ViiSE
 */
public class HandlerCreatorClientProcessor extends SuperHandlerCreatorProcessor {
    
    public HandlerCreatorClientProcessor(ConnectionIdentifierResult identifierResult, 
            LightSearchServerDTO serverDTO, LightSearchListenerDTO listenerDTO, 
            LoggerServer loggerServer, HandlerIterator handlerIterator) {
        super(identifierResult, serverDTO, listenerDTO, loggerServer, handlerIterator);
    }
    
    @Override
    public Handler apply(Void ignore) {
        ClientDAO clientDAO = ClientDAOInit.clientDAO();
            
        ClientCommandCreator clientCmdCreator = ClientCommandCreatorInit.clientCommandCreator(
                super.serverDTO(), super.listenerDTO(), clientDAO);
        Map<String, Function<ClientCommand, CommandResult>> commandHolder = clientCmdCreator.createCommandHolder();

        String id = LightSearchThreadID.createID(super.identifierResult().identifier(), super.handlerIterator().next());
        ThreadParametersHolder threadParametersHolder = ThreadParametersHolderInit.threadParametersHolder(id);

        ClientParametersHolder clientParamHolder = ClientParametersHolderInit.clientParametersHolder(
            super.identifierResult().clientSocket(), super.identifierResult().dataStream(), commandHolder);

        ClientHandlerDTO clientHandlerDTO = ClientHandlerDTOInit.clientHandlerDTO(clientParamHolder,
                threadParametersHolder, super.listenerDTO().currentDateTime(), 
                super.listenerDTO().threadManager(), clientDAO);

        Handler clientHandler = ClientHandlerInit.clientHandler(clientHandlerDTO, super.serverDTO(), super.loggerServer());

        return clientHandler;
    }
}
