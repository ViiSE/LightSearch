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

package lightsearch.server.timer;

import lightsearch.server.data.ClientsService;
import lightsearch.server.data.pojo.Client;
import lightsearch.server.log.LogMessageTypeEnum;
import lightsearch.server.log.LoggerServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Map;

import static lightsearch.server.log.LogMessageTypeEnum.INFO;

@Component("timeoutManagerReducer")
@Scope("prototype")
public class TimeoutManagerReducerImpl implements TimeoutManager {

    @Autowired
    private LoggerServer logger;

    private final int reduceValue;
    private final ClientsService<String, Client> clientsService;

    public TimeoutManagerReducerImpl(int reduceValue, ClientsService<String, Client> clientsService) {
        this.reduceValue = reduceValue;
        this.clientsService = clientsService;
    }

    @Override
    public void refresh() {
        clientsService.clients().forEach((IMEI, client) -> client.decreaseTimeoutLimitValue(reduceValue));
    }

    @Override
    public void check() {
        Iterator iterator = clientsService.clients().entrySet().iterator();
        while(iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            Client client = (Client) entry.getValue();
            String IMEI = (String) entry.getKey();
            if(client.getTimeoutLimitSeconds() <= 0) {
                logger.log(TimeoutManagerReducerImpl.class, INFO, "Client " + IMEI + " timeout.");
                iterator.remove();
            }
        }
    }
}
