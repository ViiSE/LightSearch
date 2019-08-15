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

package lightsearch.server.producer.cmd.client;

import lightsearch.server.cmd.client.ClientCommandCreator;
import lightsearch.server.data.ClientDAO;
import lightsearch.server.data.LightSearchListenerDTO;
import lightsearch.server.data.LightSearchServerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service("clientCommandCreatorProducerDefault")
public class ClientCommandCreatorProducerDefaultImpl implements ClientCommandCreatorProducer {

    private final String CLIENT_COMMAND_CREATOR       = "clientCommandCreatorDefault";
    private final String CLIENT_COMMAND_CREATOR_DEBUG = "clientCommandCreatorDebug";

    @Autowired
    private ApplicationContext ctx;

    @Override
    public ClientCommandCreator getClientCommandCreatorDebugInstance(
            LightSearchServerDTO serverDTO, LightSearchListenerDTO listenerDTO, ClientDAO clientDAO) {
        return (ClientCommandCreator) ctx.getBean(CLIENT_COMMAND_CREATOR_DEBUG, serverDTO, listenerDTO, clientDAO);
    }

    @Override
    public ClientCommandCreator getClientCommandCreatorDefaultInstance(
            LightSearchServerDTO serverDTO, LightSearchListenerDTO listenerDTO, ClientDAO clientDAO) {
        return (ClientCommandCreator) ctx.getBean(CLIENT_COMMAND_CREATOR, serverDTO, listenerDTO, clientDAO);
    }
}
