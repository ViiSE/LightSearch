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

package lightsearch.server.producer.cmd.admin;

import lightsearch.server.cmd.admin.AdminCommandCreator;
import lightsearch.server.data.AdminDAO;
import lightsearch.server.data.LightSearchListenerDTO;
import lightsearch.server.data.LightSearchServerDTO;
import lightsearch.server.log.LoggerServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service("adminCommandCreatorProducerDefault")
public class AdminCommandCreatorProducerDefaultImpl implements AdminCommandCreatorProducer {

    private final String ADMIN_COMMAND_CREATOR = "adminCommandCreatorDefault";

    @Autowired
    private ApplicationContext ctx;

    @Override
    public AdminCommandCreator getAdminCommandCreatorDefaultInstance(
            LightSearchServerDTO serverDTO, LightSearchListenerDTO listenerDTO, LoggerServer logger, AdminDAO adminDAO) {
        return (AdminCommandCreator) ctx.getBean(ADMIN_COMMAND_CREATOR, serverDTO, listenerDTO, logger, adminDAO);
    }
}
