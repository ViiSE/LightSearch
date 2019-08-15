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

import lightsearch.server.cmd.client.ClientCommand;
import lightsearch.server.cmd.result.CommandResult;
import lightsearch.server.data.ClientParametersHolder;
import lightsearch.server.data.stream.DataStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.net.Socket;
import java.util.Map;
import java.util.function.Function;

@Service("clientParametersHolderProducerDefault")
public class ClientParametersHolderProducerDefaultImpl implements ClientParametersHolderProducer {

    private final String CLIENT_PARAMETERS_HOLDER = "clientParametersHolderDefault";

    @Autowired
    private ApplicationContext ctx;

    @Override
    public ClientParametersHolder getClientParametersHolderDefaultInstance(
            Socket clientSocket, DataStream dataStream, Map<String, Function<ClientCommand, CommandResult>> commandHolder) {
        return (ClientParametersHolder) ctx.getBean(CLIENT_PARAMETERS_HOLDER, clientSocket, dataStream, commandHolder);
    }
}
