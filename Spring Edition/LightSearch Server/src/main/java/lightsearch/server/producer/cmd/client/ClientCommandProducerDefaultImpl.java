/*
 *  Copyright 2019 ViiSE.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package lightsearch.server.producer.cmd.client;

import lightsearch.server.cmd.client.ClientCommand;
import lightsearch.server.data.pojo.ClientCommandDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service("clientCommandProducerDefault")
public class ClientCommandProducerDefaultImpl implements ClientCommandProducer {

    private final String CLIENT_COMMAND = "clientCommandDefault";

    @Autowired
    private ApplicationContext ctx;

    @Override
    public ClientCommand getClientCommandDefaultInstance(ClientCommandDTO clientCommandDTO) {
        return (ClientCommand) ctx.getBean(CLIENT_COMMAND, clientCommandDTO);
    }
}
