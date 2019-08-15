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

package lightsearch.server.producer.data.stream;

import lightsearch.server.data.stream.DataStreamCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.net.Socket;

@Service("dataStreamCreatorProducerDefault")
public class DataStreamCreatorProducerDefaultImpl implements DataStreamCreatorProducer {

    private final String DATA_STREAM_CREATOR = "dataStreamCreatorDefault";

    @Autowired
    private ApplicationContext ctx;

    @Override
    public DataStreamCreator getDataStreamCreatorDefaultInstance(Socket socket) {
        return (DataStreamCreator) ctx.getBean(DATA_STREAM_CREATOR, socket);
    }
}
