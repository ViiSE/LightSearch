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
import lightsearch.server.cmd.admin.AdminCommandCreatorInit;
import lightsearch.server.cmd.result.CommandResult;
import lightsearch.server.data.*;
import lightsearch.server.handler.Handler;
import lightsearch.server.handler.admin.AdminHandlerInit;
import lightsearch.server.identifier.ConnectionIdentifierResult;
import lightsearch.server.identifier.HandlerIdentifier;
import lightsearch.server.log.LoggerServer;
import lightsearch.server.thread.LightSearchThreadID;

import java.util.Map;
import java.util.function.Function;

/**
 * Обработчик команды создания обработчика администратора LightSearch Server.
 * <p>
 * @author ViiSE
 * @see lightsearch.server.listener.LightSearchServerListener
 * @see lightsearch.server.handler.HandlerCreator
 * @since 2.0.0
 */
public class HandlerCreatorAdminProcessor extends SuperHandlerCreatorProcessor {

    public HandlerCreatorAdminProcessor(ConnectionIdentifierResult identifierResult, 
            LightSearchServerDTO serverDTO, LightSearchListenerDTO listenerDTO, 
            LoggerServer loggerServer, HandlerIdentifier handlerIdentifier) {
        super(identifierResult, serverDTO, listenerDTO, loggerServer, handlerIdentifier);
    }
    
    @Override
    public Handler apply(Void ignore) {
        AdminDAO adminDAO = AdminDAOInit.adminDAO();
            
        AdminCommandCreator admCmdCreator = AdminCommandCreatorInit.adminCommandCreator(super.serverDTO(),
                    super.listenerDTO(), super.loggerServer(), adminDAO);
        Map<String, Function<AdminCommand, CommandResult>> commandHolder = admCmdCreator.createCommandHolder();
            
        String id = LightSearchThreadID.createID(super.identifierResult().identifier(), super.handlerIdentifier().next());
            
        ThreadParametersHolder threadParametersHolder = ThreadParametersHolderInit.threadParametersHolder(id);
            
        AdminParametersHolder admParamHolder = AdminParametersHolderInit.adminParametersHolder(
                super.identifierResult().clientSocket(), super.identifierResult().dataStream(), commandHolder);
            
        AdminHandlerDTO admHandlerDTO = AdminHandlerDTOInit.adminHandlerDTO(admParamHolder, threadParametersHolder,
                    super.listenerDTO().currentDateTime(), super.listenerDTO().threadManager(), adminDAO);

        return AdminHandlerInit.adminHandler(admHandlerDTO, super.serverDTO(), super.loggerServer());
    }
    
}
