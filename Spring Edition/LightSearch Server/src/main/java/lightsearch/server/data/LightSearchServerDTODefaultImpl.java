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
package lightsearch.server.data;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 *
 * @author ViiSE
 */
@Component("lightSearchServerDTODefault")
@Scope("prototype")
public class LightSearchServerDTODefaultImpl implements LightSearchServerDTO{

    private final Map<String, String> admins;
    private final Map<String,String> clients;
    private final List<String> blacklist;
    private final LightSearchServerDatabaseDTO databaseDTO;
    private final int serverPort;
    private final LightSearchServerSettingsDAO settingsDAO;
    private final String currentDirectory;
    
    public LightSearchServerDTODefaultImpl(Map<String, String> admins,
                                           Map<String, String> clients,
                                           List<String> blacklist,
                                           LightSearchServerDatabaseDTO databaseDTO,
                                           int serverPort,
                                           LightSearchServerSettingsDAO settingsDAO,
                                           String currentDirectory) {
        this.admins = admins;
        this.clients = clients;
        this.blacklist = blacklist;
        this.databaseDTO = databaseDTO;
        this.serverPort = serverPort;
        this.settingsDAO = settingsDAO;
        this.currentDirectory = currentDirectory;
    }

    @Override
    public Map<String, String> admins() {
        return admins;
    }
    
    @Override
    public Map<String, String> clients() {
        return clients;
    }

    @Override
    public List<String> blacklist() {
        return blacklist;
    }

    @Override
    public LightSearchServerDatabaseDTO databaseDTO() {
        return databaseDTO;
    }

    @Override
    public int serverPort() {
        return serverPort;
    }

    @Override
    public String currentDirectory() {
        return currentDirectory;
    }

    @Override
    public LightSearchServerSettingsDAO settingsDAO() {
        return settingsDAO;
    }
}
