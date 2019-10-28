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

package lightsearch.server.producer.cmd.result;

import lightsearch.server.cmd.result.ClientCommandResultCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service("clientCommandResultCreatorProducerDefault")
public class ClientCommandResultCreatorProducerDefaultImpl implements ClientCommandResultCreatorProducer {

    @Autowired
    private ApplicationContext ctx;

    @Override
    public ClientCommandResultCreator getCommandResultCreatorClientJSONInstance(String rawJSONResult) {
        return (ClientCommandResultCreator) ctx.getBean("clientCommandResultCreatorJSON", rawJSONResult);
    }

    @Override
    public ClientCommandResultCreator getCommandResultCreatorClientErrorInstance(String IMEI, String message) {
        return (ClientCommandResultCreator) ctx.getBean("clientCommandResultCreatorClientError", IMEI, message);
    }
}
