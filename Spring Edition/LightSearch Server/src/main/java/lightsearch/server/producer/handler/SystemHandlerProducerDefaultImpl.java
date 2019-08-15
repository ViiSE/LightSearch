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

import lightsearch.server.data.SystemHandlerDTO;
import lightsearch.server.handler.system.SystemHandler;
import lightsearch.server.log.LoggerServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service("systemHandlerProducerDefault")
public class SystemHandlerProducerDefaultImpl implements SystemHandlerProducer {

    private final String SYSTEM_HANDLER = "systemHandlerDefault";

    @Autowired
    private ApplicationContext ctx;

    @Override
    public SystemHandler getSystemHandlerDefaultInstance(
            SystemHandlerDTO systemHandlerDTO, LoggerServer loggerServer) {
        return (SystemHandler) ctx.getBean(SYSTEM_HANDLER, systemHandlerDTO, loggerServer);
    }
}
