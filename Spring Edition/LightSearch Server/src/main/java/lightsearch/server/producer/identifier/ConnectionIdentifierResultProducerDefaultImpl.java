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

package lightsearch.server.producer.identifier;

import lightsearch.server.data.stream.DataStream;
import lightsearch.server.identifier.ConnectionIdentifierResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.net.Socket;

@Service("connectionIdentifierResultProducerDefault")
public class ConnectionIdentifierResultProducerDefaultImpl implements ConnectionIdentifierResultProducer {

    private final String CONNECTION_IDENTIFIER_RESULT = "connectionIdentifierResultDefault";

    @Autowired
    private ApplicationContext ctx;

    @Override
    public ConnectionIdentifierResult getConnectionIdentifierResultDefaultInstance(Socket clientSocket, String identifier, DataStream dataStream) {
        return (ConnectionIdentifierResult) ctx.getBean(CONNECTION_IDENTIFIER_RESULT, clientSocket, identifier, dataStream);
    }
}
