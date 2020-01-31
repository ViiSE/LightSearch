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

package lightsearch.server.data;

import lightsearch.server.data.pojo.ClientDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("clientsServiceDefault")
public class ClientsServiceDefaultImpl implements ClientsService<String, ClientDTO> {

    private final int timeoutLimit;
    private final static Map<String, ClientDTO> clients = new HashMap<>();

    public ClientsServiceDefaultImpl(
            @Value("${lightsearch.server.settings.timeout.client-timeout}") int timeoutLimit) {
        this.timeoutLimit = timeoutLimit;
    }

    @Override
    public Map<String, ClientDTO> clients() {
        return clients;
    }

    @Override
    public void refreshTimeout(String key) {
        if(clients.get(key) != null)
            clients.get(key).setTimeoutLimitSeconds(timeoutLimit);
    }

    @Override
    public void addClient(String IMEI, ClientDTO client) {
        if(IMEI != null)
            if(!(IMEI.isEmpty()))
                if(client != null) {
                    clients.put(IMEI, client);
                    refreshTimeout(IMEI);
                }
    }
}
