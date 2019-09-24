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
package lightsearch.server;

import lightsearch.server.checker.LightSearchChecker;
import lightsearch.server.checker.LightSearchCheckerInit;
import lightsearch.server.checker.TimerRebootValueChecker;
import lightsearch.server.checker.TimerRebootValueCheckerInit;
import lightsearch.server.cmd.changer.ServerStateChanger;
import lightsearch.server.cmd.changer.ServerStateChangerInit;
import lightsearch.server.data.*;
import lightsearch.server.identifier.*;
import lightsearch.server.initialization.*;
import lightsearch.server.listener.LightSearchServerListener;
import lightsearch.server.listener.LightSearchServerListenerInit;
import lightsearch.server.log.*;
import lightsearch.server.thread.*;
import lightsearch.server.time.CurrentDateTime;
import lightsearch.server.time.CurrentDateTimeInit;
import lightsearch.server.timer.TimersIDEnum;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*v. a.b.c:
    a {uint} - Very significant update(for example, adding a new features, 
              fundamental refactoring, e.t.c),
    b {uint} - Error correction usually,
    c {uint} - Minor internal server changes.
    */

/**
 * Main класс LightSearch Server.
 * @author ViiSE
 * @since 1.0.0
 */
public class LightSearchServer {
    
    /**
     * Main метод.
     * @param argc В данный момент нет передаваемых аргументов в командную строку.
     * @throws InterruptedException
     */
    public static void main(String[] argc) throws InterruptedException {
        System.out.println("LightSearch Server, v. 2.8.1");
        System.out.println("Welcome!");
        
        CurrentServerDirectory currentServerDirectory = 
                CurrentServerDirectoryInit.currentDirectory(OsDetectorInit.osDetector());
        String currentDirectory = currentServerDirectory.currentDirectory();
        
        ServerPort serverPort = ServerPortInit.serverPort(currentServerDirectory);
        int port = serverPort.port();
        
        ServerSettings serverSettings = ServerSettingsInit.serverSettings(currentServerDirectory);
        int serverReboot = serverSettings.rebootServerValue();
        int clientTimeout = serverSettings.timeoutClientValue();
        
        AdministratorsMap administratorsMap = AdministratorsMapInit.administratorsMap(currentServerDirectory);
        Map<String, String> admins = administratorsMap.administratorsMap();

        ClientBlacklist clientBlacklist = ClientBlacklistInit.clientBlacklist(currentServerDirectory);
        List<String> blacklist = clientBlacklist.blacklist();
        
        DatabaseSettings dbSettings = DatabaseSettingsInit.databaseSettings(currentServerDirectory);
        String dbName = dbSettings.name();
        int dbPort = dbSettings.port();
        String dbIp   = dbSettings.ip();
        
        LightSearchServerSettingsDAO settingsDAO =
                LightSearchServerSettingsDAOInit.settingsDAO(serverReboot, clientTimeout);
        
        LightSearchServerDatabaseDTO databaseDTO = 
                LightSearchServerDatabaseDTOInit.lightSearchServerDatabaseDTO(dbIp, dbName, dbPort);
        
        LightSearchServerDTO serverDTO = LightSearchServerDTOInit.LightSearchServerDTO(
                admins, new HashMap<>(), blacklist, databaseDTO, port, settingsDAO, currentDirectory);
        
        LogDirectory logDirectory = LogDirectoryInit.logDirectory("logs");
        LoggerFile loggerFile = LoggerFileInit.loggerFile(logDirectory);
        LoggerWindow loggerWindow = LoggerWindowInit.loggerWindow();
        
        LoggerServer loggerServer = LoggerServerInit.loggerServer(loggerFile, loggerWindow);
        
        CurrentDateTime currentDateTime = CurrentDateTimeInit.currentDateTime();
        
        HashMap<String, LightSearchThread> threads = new HashMap<>();
        ThreadHolder threadHolder = ThreadHolderInit.threadHolder(threads);
        ThreadManager threadManager = ThreadManagerInit.threadManager(threadHolder);
        
        DatabaseRecordIdentifierReader identifierReader =
                DatabaseRecordIdentifierReaderInit.databaseRecordIdentifierReader(serverDTO);
        DatabaseRecordIdentifierWriter identifierWriter =
                DatabaseRecordIdentifierWriterInit.databaseRecordIdentifierWriter(serverDTO);
        
        DatabaseRecordIdentifier identifier =
                DatabaseRecordIdentifierInit.databaseRecordIdentifier(identifierReader.read());
        
        LightSearchChecker checker = LightSearchCheckerInit.lightSearchChecker();
        
        TimersIDEnum timerRebootId = TimersIDEnum.REBOOT_TIMER_ID;
        
        LightSearchListenerDTO listenerDTO = LightSearchListenerDTOInit.lightSearchListenerDTO(
                checker, currentDateTime, threadManager, identifier, identifierWriter, timerRebootId);
        
        System.out.println("Now Logging is in the folder logs and this window. "
                + "For administration use LightSearch Server Admin Panel.");        
        Thread.sleep(100);
        
        ServerStateChanger stateChanger =
                ServerStateChangerInit.serverStateChanger(serverDTO, loggerServer, currentDateTime, threadManager);
        
        TimerRebootValueChecker timerChecker = 
                TimerRebootValueCheckerInit.timerRebootValueChecker();
        if(timerChecker.check(serverDTO.settingsDAO().serverRebootValue()))
            stateChanger.executeRebootTimer(timerRebootId);
        
        TimersIDEnum timerIdentifierId = TimersIDEnum.IDENTIFIER_WRITER_TIMER_ID;
        long minutesToWrite = 30;
        stateChanger.executeDatabaseRecordIdentifierWriterTimer(
                identifier, identifierWriter, minutesToWrite, timerIdentifierId);
        
        LightSearchServerListener serverListener =
                LightSearchServerListenerInit.lightSearchServerListener(serverDTO, listenerDTO, loggerServer);
        serverListener.startServer();
    }
}