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
package test.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lightsearch.server.checker.LightSearchChecker;
import lightsearch.server.checker.LightSearchCheckerInit;
import lightsearch.server.data.LightSearchListenerDTO;
import lightsearch.server.data.LightSearchListenerDTOInit;
import lightsearch.server.data.LightSearchServerDTOInit;
import lightsearch.server.data.LightSearchServerDatabaseDTO;
import lightsearch.server.data.LightSearchServerDatabaseDTOInit;
import lightsearch.server.data.LightSearchServerSettingsDAOInit;
import lightsearch.server.identifier.*;
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
import lightsearch.server.thread.LightSearchThread;
import lightsearch.server.thread.ThreadHolder;
import lightsearch.server.thread.ThreadHolderInit;
import lightsearch.server.thread.ThreadManager;
import lightsearch.server.thread.ThreadManagerInit;
import lightsearch.server.time.CurrentDateTime;
import lightsearch.server.time.CurrentDateTimeInit;
import static org.testng.Assert.*;
import org.testng.annotations.Test;
import lightsearch.server.data.LightSearchServerDTO;
import lightsearch.server.data.LightSearchServerSettingsDAO;
import lightsearch.server.timer.TimersIDEnum;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class LightSearchListenerDTOTestNG {
    
    private LightSearchServerDTO initDTO() {
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
        
        serverDTO.clients().put("123456789123456", "user");
        serverDTO.clients().put("789544131546489", "user1");
        serverDTO.clients().put("555364213589963", "user2");
        
        return serverDTO;
    }
    
    private ThreadManager initThreadManager() {
        HashMap<String,LightSearchThread> threads = new HashMap<>();
        ThreadHolder holder = ThreadHolderInit.threadHolder(threads);
        ThreadManager threadManager = ThreadManagerInit.threadManager(holder);
        return threadManager;
    }
    
    private DatabaseRecordIdentifier initIdentifier(LightSearchServerDTO serverDTO) {
        DatabaseRecordIdentifierReader identifierReader = DatabaseRecordIdentifierReaderInit.databaseRecordIdentifierReader(serverDTO);
        DatabaseRecordIdentifier identifier = DatabaseRecordIdentifierInit.databaseRecordIdentifier(identifierReader.read());
        return identifier;
    }
    
    private LightSearchListenerDTO initListenerDTO() {
        LightSearchServerDTO serverDTO = initDTO();
        assertNotNull(serverDTO, "ServerDTO is null!");
        
        CurrentDateTime currentDateTime = CurrentDateTimeInit.currentDateTime();
        ThreadManager threadManager = initThreadManager();
        TimersIDEnum timerRebootId = TimersIDEnum.REBOOT_TIMER_ID;
        LightSearchChecker checker = LightSearchCheckerInit.lightSearchChecker();
        DatabaseRecordIdentifier databaseRecordIdentifier = initIdentifier(serverDTO);
        DatabaseRecordIdentifierWriter databaseRecordIdentifierWriter = DatabaseRecordIdentifierWriterInit.databaseRecordIdentifierWriter(serverDTO);
        
        LightSearchListenerDTO listenerDTO = LightSearchListenerDTOInit.lightSearchListenerDTO(
                checker, currentDateTime, threadManager, databaseRecordIdentifier,
                databaseRecordIdentifierWriter, timerRebootId);
        
        return listenerDTO;
    }
    
    @Test
    public void checker() {
        testBegin("LightSearchListenerDTO", "checker()");
        
        LightSearchListenerDTO listenerDTO = initListenerDTO();
        System.out.println("LightSearchListenerDTO.checker(): " + listenerDTO.checker());
        
        testEnd("LightSearchListenerDTO", "checker()");
    }
    
    @Test
    public void timerRebootId() {
        testBegin("LightSearchListenerDTO", "timerRebootId()");
        
        LightSearchListenerDTO listenerDTO = initListenerDTO();
        System.out.println("LightSearchListenerDTO.timerRebootId(): " + listenerDTO.timerRebootId());
                
        testEnd("LightSearchListenerDTO", "timerRebootId()");
    }
    
    @Test
    public void currentDateTime() {
        testBegin("LightSearchListenerDTO", "currentDateTime()");
        
        LightSearchListenerDTO listenerDTO = initListenerDTO();
        System.out.println("LightSearchListenerDTO.currentDateTime(): " + listenerDTO.currentDateTime());
        
        testEnd("LightSearchListenerDTO", "currentDateTime()");
    }
    
    @Test
    public void databaseRecordIdentifier() {
        testBegin("LightSearchListenerDTO", "databaseRecordIdentifier()");
        
        LightSearchListenerDTO listenerDTO = initListenerDTO();
        System.out.println("LightSearchListenerDTO.databaseRecordIdentifier(): " + listenerDTO.databaseRecordIdentifier());
                
        testEnd("LightSearchListenerDTO", "databaseRecordIdentifier()");
    }
    
    @Test
    public void databaseRecordIdentifierWriter() {
        testBegin("LightSearchListenerDTO", "databaseRecordIdentifierWriter()");
        
        LightSearchListenerDTO listenerDTO = initListenerDTO();
        System.out.println("LightSearchListenerDTO.databaseRecordIdentifierWriter(): " + listenerDTO.databaseRecordIdentifierWriter());
        
        testEnd("LightSearchListenerDTO", "databaseRecordIdentifierWriter()");
    }
    
    @Test
    public void threadManager() {
        testBegin("LightSearchListenerDTO", "threadManager()");
        
        LightSearchListenerDTO listenerDTO = initListenerDTO();
        System.out.println("LightSearchListenerDTO.threadManager(): " + listenerDTO.threadManager());
        
        testEnd("LightSearchListenerDTO", "threadManager()");        
    }
}
