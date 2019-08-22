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
package test.identifier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import lightsearch.server.initialization.OsDetector;
import lightsearch.server.initialization.OsDetectorInit;
import lightsearch.server.initialization.ServerPort;
import lightsearch.server.initialization.ServerPortInit;
import lightsearch.server.initialization.ServerSettings;
import lightsearch.server.initialization.ServerSettingsInit;
import lightsearch.server.log.LogDirectory;
import lightsearch.server.log.LogDirectoryInit;
import lightsearch.server.log.LoggerFile;
import lightsearch.server.log.LoggerFileInit;
import lightsearch.server.log.LoggerServer;
import lightsearch.server.log.LoggerServerInit;
import lightsearch.server.log.LoggerWindow;
import lightsearch.server.log.LoggerWindowInit;
import lightsearch.server.thread.LightSearchThread;
import lightsearch.server.thread.ThreadHolder;
import lightsearch.server.thread.ThreadHolderInit;
import lightsearch.server.thread.ThreadManager;
import lightsearch.server.thread.ThreadManagerInit;
import lightsearch.server.time.CurrentDateTime;
import lightsearch.server.time.CurrentDateTimeInit;
import lightsearch.server.timer.DatabaseRecordIdentifierWriterTimerCreator;
import lightsearch.server.timer.DatabaseRecordIdentifierWriterTimerCreatorInit;
import lightsearch.server.timer.DatabaseRecordIdentifierWriterTimerExecutor;
import lightsearch.server.timer.DatabaseRecordIdentifierWriterTimerExecutorInit;
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
public class DatabaseRecordIdentifierWriterTimerExecutorTestNG {
    
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
    
    private String initCurrentDirectory() {
        OsDetector osDetector = OsDetectorInit.osDetector();
        CurrentServerDirectory currentDirectory = CurrentServerDirectoryInit.currentDirectory(osDetector);
        
        return currentDirectory.currentDirectory();
    }
    
    private LoggerServer initLoggerServer() {
        LogDirectory logDir = LogDirectoryInit.logDirectory("logs");
        LoggerFile logFile = LoggerFileInit.loggerFile(logDir);
        LoggerWindow logWindow = LoggerWindowInit.loggerWindow();
        LoggerServer logServer = LoggerServerInit.loggerServer(logFile, logWindow);
        return logServer;
    }
    
    private ThreadManager initThreadManager() {
        HashMap<String,LightSearchThread> threads = new HashMap<>();
        ThreadHolder holder = ThreadHolderInit.threadHolder(threads);
        ThreadManager threadManager = ThreadManagerInit.threadManager(holder);
        return threadManager;
    }
    
    private DatabaseRecordIdentifierWriter initIdentifierWriter(LightSearchServerDTO serverDTO) {
        DatabaseRecordIdentifierWriter identifierWriter = DatabaseRecordIdentifierWriterInit.databaseRecordIdentifierWriter(serverDTO);
        return identifierWriter;
    }
    
    private DatabaseRecordIdentifier initIdentifierReader(LightSearchServerDTO serverDTO) {
        DatabaseRecordIdentifierReader identifierReader = DatabaseRecordIdentifierReaderInit.databaseRecordIdentifierReader(serverDTO);
        DatabaseRecordIdentifier identifier = DatabaseRecordIdentifierInit.databaseRecordIdentifier(identifierReader.read());
        return identifier;
    }
    
    @Test
    public void databaseRecordIdentifierWriterTimerExecutor() {
        testBegin("DatabaseRecordIdentifierWriterTimerExecutor", "databaseRecordIdentifierWriterTimerExecutor()");
        
        String currentDirectory = initCurrentDirectory();
        assertNotNull(currentDirectory, "CurrentDirectory is null!");
        assertFalse(currentDirectory.equals(""), "CurrentDirectory is null!");
        
        LoggerServer loggerServer = initLoggerServer();
        assertNotNull(loggerServer, "Logger server is null!");
        
        CurrentDateTime currentDateTime = CurrentDateTimeInit.currentDateTime();
        
        ThreadManager threadManager = initThreadManager();
        assertNotNull(threadManager, "Thread manager is null!");
        
        LightSearchServerDTO serverDTO = initDTO();
        assertNotNull(serverDTO, "ServerDTO is null!");
        
        DatabaseRecordIdentifier identifier = initIdentifierReader(serverDTO);
        assertNotNull(identifier, "DatabaseRecordIdentifier is null!");
        
        DatabaseRecordIdentifierWriter identifierWriter = initIdentifierWriter(serverDTO);
        assertNotNull(identifierWriter, "DatabaseRecordIdentifierWriter is null!");
        
        long minutesToWrite = 1;
        
        TimersIDEnum id = TimersIDEnum.IDENTIFIER_WRITER_TIMER_ID;
        
        DatabaseRecordIdentifierWriterTimerCreator identifierTimerCreator =
                DatabaseRecordIdentifierWriterTimerCreatorInit.databaseRecordIdentifierWriterTimerCreator(
                        loggerServer, currentDateTime, threadManager, identifierWriter, identifier,
                        minutesToWrite, id);
        
        DatabaseRecordIdentifierWriterTimerExecutor identifierTimerExecutor =
                DatabaseRecordIdentifierWriterTimerExecutorInit.databaseRecordIdentifierWriterTimerExecutor(identifierTimerCreator.getTimer());
        
        System.out.println("DatabaseRecordIdentifierWriter Value BEFORE: " + identifier.databaseRecordIdentifier());
        identifier.next();
        identifier.next();
        System.out.println("Trying to write DatabaseRecordIdentifierWriter Value: " + identifier.databaseRecordIdentifier());
        
        identifierTimerExecutor.startDatabaseRecordIdentifierWriterTimer();
        
        System.out.println("Wait 1.5 minute...");
        try { Thread.sleep(90000); } catch (InterruptedException ignore) { }
        
        java.awt.Toolkit.getDefaultToolkit().beep();
        
        identifier.next();
        identifier.next();
        System.out.println("Trying to write DatabaseRecordIdentifierWriter Value: " + identifier.databaseRecordIdentifier());
        
        System.out.println("Wait 1 minute...");
        try { Thread.sleep(60000); } catch (InterruptedException ignore) { }
        
        testEnd("DatabaseRecordIdentifierWriterTimerExecutor", "databaseRecordIdentifierWriterTimerExecutor()");
    }
}
