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

import java.util.function.Function;
import lightsearch.server.data.LightSearchListenerDTO;
import lightsearch.server.data.LightSearchServerDTO;
import lightsearch.server.handler.Handler;
import lightsearch.server.identifier.ConnectionIdentifierResult;
import lightsearch.server.identifier.HandlerIdentifier;
import lightsearch.server.log.LoggerServer;

/**
 * Абстрактный класс создания обработчика клиента LightSearch Server.
 * <p>
 * @author ViiSE
 * @see lightsearch.server.listener.LightSearchServerListener
 * @see lightsearch.server.handler.HandlerCreator
 * @see lightsearch.server.handler.processor.HandlerCreatorAdminProcessor
 * @see lightsearch.server.handler.processor.HandlerCreatorClientProcessor
 * @see lightsearch.server.handler.processor.HandlerCreatorSystemProcessor
 * @since 2.0.0
 */
public abstract class SuperHandlerCreatorProcessor implements Function<Void, Handler> {
    private final ConnectionIdentifierResult identifierResult;
    private final LightSearchServerDTO serverDTO;
    private final LightSearchListenerDTO listenerDTO;
    private final LoggerServer loggerServer;
    private final HandlerIdentifier handlerIdentifier;
    
    public SuperHandlerCreatorProcessor(ConnectionIdentifierResult identifierResult, 
            LightSearchServerDTO serverDTO, LightSearchListenerDTO listenerDTO, 
            LoggerServer loggerServer, HandlerIdentifier handlerIdentifier) {
        this.identifierResult = identifierResult;
        this.serverDTO = serverDTO;
        this.listenerDTO = listenerDTO;
        this.loggerServer = loggerServer;
        this.handlerIdentifier = handlerIdentifier;
    }
    
    public ConnectionIdentifierResult identifierResult() {
        return identifierResult;
    }
    
    public LightSearchServerDTO serverDTO() {
        return serverDTO;
    }
    
    public LightSearchListenerDTO listenerDTO() {
        return listenerDTO;
    }
    
    public LoggerServer loggerServer() {
        return loggerServer;
    }
    
    public HandlerIdentifier handlerIdentifier() {
        return handlerIdentifier;
    }
}
