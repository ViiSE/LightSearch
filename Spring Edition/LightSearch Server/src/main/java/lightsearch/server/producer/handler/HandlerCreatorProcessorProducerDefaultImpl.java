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

package lightsearch.server.producer.handler;

import lightsearch.server.data.LightSearchListenerDTO;
import lightsearch.server.data.LightSearchServerDTO;
import lightsearch.server.handler.processor.HandlerCreatorProcessor;
import lightsearch.server.identifier.ConnectionIdentifierResult;
import lightsearch.server.iterator.HandlerIterator;
import lightsearch.server.log.LoggerServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service("handlerCreatorProcessorProducerDefault")
public class HandlerCreatorProcessorProducerDefaultImpl implements HandlerCreatorProcessorProducer {

    private final String HANDLER_CREATOR_ADMIN_PROCESSOR  = "handlerCreatorAdminProcessor";
    private final String HANDLER_CREATOR_CLIENT_PROCESSOR = "handlerCreatorClientProcessor";
    private final String HANDLER_CREATOR_SYSTEM_PROCESSOR = "handlerCreatorSystemProcessor";

    @Autowired
    private ApplicationContext ctx;

    @Override
    public HandlerCreatorProcessor getHandlerCreatorAdminProcessorInstance(
            ConnectionIdentifierResult identifierResult, LightSearchServerDTO serverDTO, LightSearchListenerDTO listenerDTO,
            LoggerServer loggerServer, HandlerIterator handlerIterator) {
        return (HandlerCreatorProcessor) ctx.getBean(HANDLER_CREATOR_ADMIN_PROCESSOR, identifierResult, serverDTO,
                listenerDTO, loggerServer, handlerIterator);
    }

    @Override
    public HandlerCreatorProcessor getHandlerCreatorClientProcessorInstance(
            ConnectionIdentifierResult identifierResult, LightSearchServerDTO serverDTO,
            LightSearchListenerDTO listenerDTO, LoggerServer loggerServer, HandlerIterator handlerIterator) {
        return (HandlerCreatorProcessor) ctx.getBean(HANDLER_CREATOR_CLIENT_PROCESSOR, identifierResult, serverDTO,
                listenerDTO, loggerServer, handlerIterator);
    }

    @Override
    public HandlerCreatorProcessor getHandlerCreatorSystemProcessorInstance(
            ConnectionIdentifierResult identifierResult, LightSearchListenerDTO listenerDTO, LoggerServer loggerServer,
            HandlerIterator handlerIterator) {
        return (HandlerCreatorProcessor) ctx.getBean(HANDLER_CREATOR_SYSTEM_PROCESSOR, identifierResult, listenerDTO,
                loggerServer, handlerIterator);
    }
}
