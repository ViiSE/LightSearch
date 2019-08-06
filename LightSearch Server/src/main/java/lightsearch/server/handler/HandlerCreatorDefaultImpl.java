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
package lightsearch.server.handler;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import lightsearch.server.data.LightSearchListenerDTO;
import lightsearch.server.iterator.HandlerIterator;
import lightsearch.server.iterator.HandlerIteratorInit;
import lightsearch.server.identifier.ConnectionIdentifierResult;
import lightsearch.server.log.LoggerServer;
import lightsearch.server.data.LightSearchServerDTO;
import lightsearch.server.handler.processor.HandlerCreatorAdminProcessor;
import lightsearch.server.identifier.IdentifierEnum;
import lightsearch.server.handler.processor.HandlerCreatorClientProcessor;
import lightsearch.server.handler.processor.HandlerCreatorSystemProcessor;

/**
 *
 * @author ViiSE
 */
public class HandlerCreatorDefaultImpl implements HandlerCreator {
    
    private final String ADMIN  = IdentifierEnum.ADMIN.stringValue();
    private final String CLIENT = IdentifierEnum.CLIENT.stringValue();
    private final String SYSTEM = IdentifierEnum.SYSTEM.stringValue();
    
    private final ConnectionIdentifierResult identifierResult;
    private final LightSearchServerDTO serverDTO;
    private final LightSearchListenerDTO listenerDTO;
    private final LoggerServer loggerServer;
    private final HandlerIterator handlerIterator;
    private final Map<String, Function<Void, Handler>> handlerHolder = new HashMap<>();
    
    HandlerCreatorDefaultImpl(ConnectionIdentifierResult identifierResult, 
            LightSearchServerDTO serverDTO, LightSearchListenerDTO listenerDTO, 
            LoggerServer loggerServer) {
        this.identifierResult = identifierResult;
        this.serverDTO = serverDTO;
        this.listenerDTO = listenerDTO;
        this.loggerServer = loggerServer;
        this.handlerIterator = HandlerIteratorInit.handlerIterator();
        
        initHandlerHolder();
    }
            
    private void initHandlerHolder() {
        handlerHolder.put(ADMIN, new HandlerCreatorAdminProcessor(identifierResult, serverDTO, 
                listenerDTO, loggerServer, handlerIterator));
        handlerHolder.put(CLIENT, new HandlerCreatorClientProcessor(identifierResult, serverDTO,
                listenerDTO, loggerServer, handlerIterator));
        handlerHolder.put(SYSTEM, new HandlerCreatorSystemProcessor(identifierResult, serverDTO, 
                listenerDTO, loggerServer, handlerIterator));
    }
    
    @Override
    public Handler getHandler() {
        Function<Void, Handler> processor = handlerHolder.get(identifierResult.identifier());

        if(processor != null) {
            Handler handler = processor.apply(null);
            return handler;
        }
        return null;
    }
    
}
