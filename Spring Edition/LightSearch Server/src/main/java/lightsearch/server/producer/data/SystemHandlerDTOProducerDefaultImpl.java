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

package lightsearch.server.producer.data;


import lightsearch.server.data.SystemHandlerDTO;
import lightsearch.server.data.SystemParametersHolder;
import lightsearch.server.data.ThreadParametersHolder;
import lightsearch.server.thread.ThreadManager;
import lightsearch.server.time.CurrentDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service("systemHandlerDTOProducerDefault")
public class SystemHandlerDTOProducerDefaultImpl implements SystemHandlerDTOProducer {

    private final String SYSTEM_HANDLER_DTO = "systemHandlerDTODefault";

    @Autowired
    private ApplicationContext ctx;

    @Override
    public SystemHandlerDTO getSystemHandlerDTODefaultInstance(
            SystemParametersHolder systemParametersHolder, ThreadParametersHolder threadParametersHolder,
            ThreadManager threadManager, CurrentDateTime currentDateTime) {
        return (SystemHandlerDTO) ctx.getBean(SYSTEM_HANDLER_DTO, systemParametersHolder, threadParametersHolder,
                threadManager, currentDateTime);
    }
}
