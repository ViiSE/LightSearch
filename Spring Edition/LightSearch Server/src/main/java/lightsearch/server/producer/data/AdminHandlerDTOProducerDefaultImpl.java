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

import lightsearch.server.data.AdminDAO;
import lightsearch.server.data.AdminHandlerDTO;
import lightsearch.server.data.AdminParametersHolder;
import lightsearch.server.data.ThreadParametersHolder;
import lightsearch.server.thread.ThreadManager;
import lightsearch.server.time.CurrentDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service("adminHandlerDTOProducerDefault")
public class AdminHandlerDTOProducerDefaultImpl implements AdminHandlerDTOProducer {

    private final String ADMIN_HANDLER_DTO = "adminHandlerDTO";

    @Autowired
    private ApplicationContext ctx;

    @Override
    public AdminHandlerDTO getAdminHandlerDTODefaultInstance(
            AdminParametersHolder adminParametersHolder, ThreadParametersHolder threadParametersHolder,
            CurrentDateTime currentDateTime, ThreadManager threadManager, AdminDAO adminDAO) {
        return (AdminHandlerDTO) ctx.getBean(ADMIN_HANDLER_DTO, adminParametersHolder, threadParametersHolder,
                currentDateTime, threadManager, adminDAO);
    }
}
