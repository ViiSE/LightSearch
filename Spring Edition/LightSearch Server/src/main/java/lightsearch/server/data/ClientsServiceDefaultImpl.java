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

import lightsearch.server.data.pojo.Client;
import lightsearch.server.data.pojo.LightSearchSettingsFromPropertiesFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("clientsServiceDefault")
public class ClientsServiceDefaultImpl implements ClientsService<String, Client> {

    @Autowired
    private LightSearchSettingsFromPropertiesFile settings;

    public ClientsServiceDefaultImpl() {}

    public ClientsServiceDefaultImpl(LightSearchSettingsFromPropertiesFile settings) {
        this.settings = settings;
    }

    private final static Map<String, Client> clients = new HashMap<>();

    @Override
    public Map<String, Client> clients() {
        return clients;
    }

    @Override
    public void refreshTimeout(String key) {
        if(clients.get(key) != null)
            if(settings != null)
                clients.get(key).setTimeoutLimitSeconds(settings.getTimeoutLimit());
    }

    @Override
    public void addClient(String IMEI, Client client) {
        if(IMEI != null)
            if(!(IMEI.isEmpty()))
                if(client != null) {
                    clients.put(IMEI, client);
                    refreshTimeout(IMEI);
                }
    }
}
