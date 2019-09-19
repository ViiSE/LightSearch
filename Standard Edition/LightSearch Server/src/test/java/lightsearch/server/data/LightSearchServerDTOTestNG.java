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

import org.testng.annotations.Test;
import test.data.DataProviderCreator;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static org.testng.Assert.*;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class LightSearchServerDTOTestNG {
    
    private static final Pattern PATTERN = Pattern.compile(
        "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
    
    private static boolean validate(final String ip) {
        return PATTERN.matcher(ip).matches();
    }

    public void admins() {
        testBegin("LightSearchServerDTO", "admins()");
        
        LightSearchServerDTO serverDTO = DataProviderCreator.createDataProvider(LightSearchServerDTO.class);
        assertNotNull(serverDTO, "Server DTO is null!");
        
        Map<String, String> adminMap = serverDTO.admins();
        assertFalse(adminMap.isEmpty(), "AdminMap is null!");
        assertTrue(adminMap.containsKey("admin"), "AdminMap is not contains \"admin\"!");
        
        adminMap.values().forEach((pass) -> assertNotEquals(pass, "", "Password is null!"));
        
        adminMap.keySet().forEach((admin) -> assertNotEquals(admin, "", "Admin is null!"));
        
        testEnd("LightSearchServerDTO", "admins()");
    }
    
    @Test
    public void clients() {
        testBegin("LightSearchServerDTO", "clients()");

        LightSearchServerDTO serverDTO = DataProviderCreator.createDataProvider(LightSearchServerDTO.class);
        assertNotNull(serverDTO, "Server DTO is null!");
        
        Map<String, String> clientMap = serverDTO.clients();
        assertNotNull(clientMap, "Client map is empty!");
        
        clientMap.values().forEach((username) -> assertNotEquals(username, "", "Username is null!"));
        
        clientMap.keySet().forEach((IMEI) -> assertNotEquals(IMEI, "", "IMEI is null!"));
        
        testEnd("LightSearchServerDTO", "clients()");
    }
    
    @Test
    public void blacklist() {
        testBegin("LightSearchServerDTO", "blacklist()");

        LightSearchServerDTO serverDTO = DataProviderCreator.createDataProvider(LightSearchServerDTO.class);
        assertNotNull(serverDTO, "Server DTO is null!");
        
        List<String> blacklist = serverDTO.blacklist();
        assertNotNull(blacklist, "Blacklist is null!");
        
        blacklist.forEach((client) -> {
            assertNotEquals(client, "", "Client is null!");
            assertNotNull(client, "Client is null!");
        });
        
        for(int i = 0; i < blacklist.size(); i++) {
            String clientT = blacklist.get(i);
            for(int j = 0; j < blacklist.size(); j++)
                if(i != j)
                    assertNotEquals(blacklist.get(j), clientT, "The same client in blacklist several times!");
        }
        
        testEnd("LightSearchServerDTO", "blacklist()");
    }
    
    @Test
    public void databaseDTO() {
        testBegin("LightSearchServerDTO", "databaseDTO()");

        LightSearchServerDTO serverDTO = DataProviderCreator.createDataProvider(LightSearchServerDTO.class);
        assertNotNull(serverDTO, "Server DTO is null!");
        
        LightSearchServerDatabaseDTO serverDbDTO = serverDTO.databaseDTO();
        assertNotNull(serverDbDTO, "Server DB DTO is null!");
        assertNotNull(serverDbDTO.dbName(), "DB name is null!");
        assertNotEquals(serverDbDTO.dbName(), "", "DB name is null!");

        assertNotNull(serverDbDTO.dbIP(), "DB ip is null!");
        assertNotEquals(serverDbDTO.dbIP(), "", "DB ip is null!");
        assertTrue(validate(serverDbDTO.dbIP()), "Wrong DB ip!");
        
        assertFalse(serverDbDTO.dbPort() < 1023, "Wrong DB port!");
        assertFalse(serverDbDTO.dbPort() > 65535, "Wrong DB port!");
        
        testEnd("LightSearchServerDTO", "databaseDTO()");
    }
    
    @Test
    public void serverPort() {
        testBegin("LightSearchServerDTO", "serverPort()");

        LightSearchServerDTO serverDTO = DataProviderCreator.createDataProvider(LightSearchServerDTO.class);
        assertNotNull(serverDTO, "Server DTO is null!");
        
        int serverPort = serverDTO.serverPort();
        
        assertFalse(serverPort < 1023, "Wrong server port!");
        assertFalse(serverPort > 65535, "Wrong server port!");
        
        testEnd("LightSearchServerDTO", "serverPort()");
    }
}
