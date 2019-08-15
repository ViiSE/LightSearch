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

import lightsearch.server.data.LightSearchListenerDTO;
import lightsearch.server.data.LightSearchServerDTO;
import lightsearch.server.handler.processor.HandlerCreatorProcessor;
import lightsearch.server.identifier.ConnectionIdentifierResult;
import lightsearch.server.identifier.IdentifierEnum;
import lightsearch.server.iterator.HandlerIterator;
import lightsearch.server.log.LoggerServer;
import lightsearch.server.producer.handler.HandlerCreatorProcessorProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 *
 * @author ViiSE
 */
@Service("handlerCreatorDefault")
@Scope("prototype")
public class HandlerCreatorDefaultImpl implements HandlerCreator {
    
    private final String ADMIN  = IdentifierEnum.ADMIN.stringValue();
    private final String CLIENT = IdentifierEnum.CLIENT.stringValue();
    private final String SYSTEM = IdentifierEnum.SYSTEM.stringValue();
    
    private final ConnectionIdentifierResult identifierResult;
    private final LightSearchServerDTO serverDTO;
    private final LightSearchListenerDTO listenerDTO;
    private final LoggerServer loggerServer;
    private final HandlerIterator handlerIterator;
    private final Map<String, HandlerCreatorProcessor> handlerHolder = new HashMap<>();

    @Autowired
    private HandlerCreatorProcessorProducer handlerCreatorProcessorProducer;

    public HandlerCreatorDefaultImpl(
            ConnectionIdentifierResult identifierResult, LightSearchServerDTO serverDTO, LightSearchListenerDTO listenerDTO,
            LoggerServer loggerServer, HandlerIterator handlerIterator) {
        this.identifierResult = identifierResult;
        this.serverDTO = serverDTO;
        this.listenerDTO = listenerDTO;
        this.loggerServer = loggerServer;
        this.handlerIterator = handlerIterator;
    }
            
    private void initHandlerHolder() {
        handlerHolder.put(ADMIN, handlerCreatorProcessorProducer.getHandlerCreatorAdminProcessorInstance(
                identifierResult, serverDTO, listenerDTO, loggerServer, handlerIterator));
        handlerHolder.put(CLIENT, handlerCreatorProcessorProducer.getHandlerCreatorClientProcessorInstance(
                identifierResult, serverDTO, listenerDTO, loggerServer, handlerIterator));
        handlerHolder.put(SYSTEM, handlerCreatorProcessorProducer.getHandlerCreatorSystemProcessorInstance(
                identifierResult, listenerDTO, loggerServer, handlerIterator));
    }
    
    @Override
    public Handler getHandler() {
        if(handlerHolder.isEmpty())
            initHandlerHolder();

        Function<Void, Handler> processor = handlerHolder.get(identifierResult.identifier());
        if(processor != null)
            return processor.apply(null);
        return null;
    }
    
}
