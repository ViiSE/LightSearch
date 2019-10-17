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

import lightsearch.server.producer.initialization.CurrentServerDirectoryProducer;
import lightsearch.server.producer.initialization.OsDetectorProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("lightSearchServerServiceDefault")
public class LightSearchServerServiceDefaultImpl implements LightSearchServerService {

    @Autowired
    @Qualifier("adminsServiceDefault")
    private AdminsService<String, String> adminsService;

    @Autowired
    @Qualifier("clientsServiceDefault")
    private ClientsService<String, String> clientsService;

    @Autowired
    @Qualifier("blacklistServiceDefault")
    private BlacklistService<String> blacklistService;

    @Autowired
    private CurrentServerDirectoryProducer currentDirectoryProducer;

    @Autowired
    private OsDetectorProducer osDetectorProducer;

    @Override
    public AdminsService adminsService() {
        return adminsService;
    }

    @Override
    public ClientsService clientsService() {
        return clientsService;
    }

    @Override
    public BlacklistService blacklistService() {
        return blacklistService;
    }

    @Override
    public String currentDirectory() {
        return currentDirectoryProducer.getCurrentServerDirectoryFromFileInstance(
                osDetectorProducer.getOsDetectorDefaultInstance())
                .currentDirectory();
    }
}
