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
package test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import lightsearch.server.data.LightSearchServerDTOInit;
import lightsearch.server.data.LightSearchServerDatabaseDTO;
import lightsearch.server.data.LightSearchServerDatabaseDTOInit;
import lightsearch.server.data.LightSearchServerSettingsDAOInit;
import lightsearch.server.initialization.AdministratorsMapInit;
import lightsearch.server.initialization.ClientBlacklistInit;
import lightsearch.server.initialization.CurrentServerDirectory;
import lightsearch.server.initialization.CurrentServerDirectoryInit;
import lightsearch.server.initialization.DatabaseSettings;
import lightsearch.server.initialization.DatabaseSettingsInit;
import lightsearch.server.initialization.OsDetectorInit;
import lightsearch.server.initialization.ServerPort;
import lightsearch.server.initialization.ServerPortInit;
import lightsearch.server.initialization.ServerSettings;
import lightsearch.server.initialization.ServerSettingsInit;
import static org.testng.Assert.*;
import org.testng.annotations.Test;
import lightsearch.server.data.LightSearchServerDTO;
import lightsearch.server.data.LightSearchServerSettingsDAO;
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
    
    private LightSearchServerDTO initTest() {
        CurrentServerDirectory currentDirectory;
        currentDirectory = CurrentServerDirectoryInit.currentDirectory(
                OsDetectorInit.osDetector());
        Map<String,String> admins = AdministratorsMapInit.administratorsMap(currentDirectory).administratorsMap();
        Map<String,String> clients = new HashMap<>();
        List<String> blacklist = ClientBlacklistInit.clientBlacklist(currentDirectory).blacklist();
        
        DatabaseSettings dbSettings = DatabaseSettingsInit.databaseSettings(currentDirectory);
        String dbName = dbSettings.name();
        String dbIP = dbSettings.ip();
        int dbPort = dbSettings.port();

        ServerSettings serverSettings = ServerSettingsInit.serverSettings(currentDirectory);
        int rebootServer = serverSettings.rebootServerValue();
        int timeoutClient = serverSettings.timeoutClientValue();
        
        ServerPort serverPort = ServerPortInit.serverPort(currentDirectory);
        int sPort = serverPort.port();
        
        LightSearchServerDatabaseDTO databaseDTO = LightSearchServerDatabaseDTOInit.lightSearchServerDatabaseDTO(
                dbIP, 
                dbName, 
                dbPort);
        
        LightSearchServerSettingsDAO settingsDTO = LightSearchServerSettingsDAOInit.settingsDAO(rebootServer, timeoutClient);
        
        LightSearchServerDTO serverDTO = LightSearchServerDTOInit.LightSearchServerDTO(
                admins,
                clients,
                blacklist,
                databaseDTO,
                sPort,
                settingsDTO,
                currentDirectory.currentDirectory());
        
        return serverDTO;
    }
    
    @Test(groups = {"Initialization", "Server"})
    public void amdins() {     
        testBegin("LightSearchServerDTO", "admins()");
        
        LightSearchServerDTO serverDTO = initTest();
        assertNotNull(serverDTO, "Server DTO is null!");
        
        Map<String, String> adminMap = serverDTO.admins();
        assertFalse(adminMap.isEmpty(), "AdminMap is null!");
        assertTrue(adminMap.containsKey("admin"), "AdminMap is not contains \"admin\"!");
        
        adminMap.values().forEach((pass) -> {
            assertFalse(pass.equals(""), "Password is null!");
        });
        
        adminMap.keySet().forEach((admin) -> {
            assertFalse(admin.equals(""), "Admin is null!");
        });
        
        testEnd("LightSearchServerDTO", "admins()");
    }
    
    @Test(groups = {"Initialization", "Server"})
    public void clients() {
        testBegin("LightSearchServerDTO", "clients()");
        
        LightSearchServerDTO serverDTO = initTest();
        assertNotNull(serverDTO, "Server DTO is null!");
        
        Map<String, String> clientMap = serverDTO.clients();
        assertNotNull(clientMap, "Client map is empty!");
        
        clientMap.values().forEach((username) -> {
            assertFalse(username.equals(""), "Username is null!");
        });
        
        clientMap.keySet().forEach((IMEI) -> {
            assertFalse(IMEI.equals(""), "IMEI is null!");
        });
        
        testEnd("LightSearchServerDTO", "clients()");
    }
    
    @Test(groups = {"Initialization", "Server"})
    public void blacklist() {
        testBegin("LightSearchServerDTO", "blacklist()");
        
        LightSearchServerDTO serverDTO = initTest();
        assertNotNull(serverDTO, "Server DTO is null!");
        
        List<String> blacklist = serverDTO.blacklist();
        assertNotNull(blacklist, "Blacklist is null!");
        
        blacklist.forEach((client) -> {
            assertFalse(client.equals(""), "Client is null!");
            assertNotNull(client, "Client is null!");
        });
        
        for(int i = 0; i < blacklist.size(); i++) {
            String clientT = blacklist.get(i);
            for(int j = 0; j < blacklist.size(); j++)
                if(i != j) {
                    assertFalse(clientT.equals(blacklist.get(j)), "The same client in blacklist several times!");
                }
        }
        
        testEnd("LightSearchServerDTO", "blacklist()");
    }
    
    @Test(groups = {"Initialization", "Server"})
    public void databaseDTO() {
        testBegin("LightSearchServerDTO", "databaseDTO()");
        
        LightSearchServerDTO serverDTO = initTest();
        assertNotNull(serverDTO, "Server DTO is null!");
        
        LightSearchServerDatabaseDTO serverDbDTO = serverDTO.databaseDTO();
        assertNotNull(serverDbDTO, "Server DB DTO is null!");
        assertNotNull(serverDbDTO.dbName(), "DB name is null!");
        assertFalse(serverDbDTO.dbName().equals(""), "DB name is null!");

        assertNotNull(serverDbDTO.dbIP(), "DB ip is null!");
        assertFalse(serverDbDTO.dbIP().equals(""), "DB ip is null!");
        assertTrue(validate(serverDbDTO.dbIP()), "Wrong DB ip!");
        
        assertFalse(serverDbDTO.dbPort() < 1023, "Wrong DB port!");
        assertFalse(serverDbDTO.dbPort() > 65535, "Wrong DB port!");
        
        testEnd("LightSearchServerDTO", "databaseDTO()");
    }
    
    @Test(groups = {"Initialization", "Server"})
    public void serverPort() {
        testBegin("LightSearchServerDTO", "serverPort()");
        
        LightSearchServerDTO serverDTO = initTest();
        assertNotNull(serverDTO, "Server DTO is null!");
        
        int serverPort = serverDTO.serverPort();
        
        assertFalse(serverPort < 1023, "Wrong server port!");
        assertFalse(serverPort > 65535, "Wrong server port!");
        
        testEnd("LightSearchServerDTO", "serverPort()");
    }
}
