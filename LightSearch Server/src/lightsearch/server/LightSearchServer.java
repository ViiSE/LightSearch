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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lightsearch.server.checker.LightSearchChecker;
import lightsearch.server.checker.LightSearchCheckerInit;
import lightsearch.server.checker.TimerRebootValueChecker;
import lightsearch.server.checker.TimerRebootValueCheckerInit;
import lightsearch.server.cmd.changer.ServerStateChanger;
import lightsearch.server.cmd.changer.ServerStateChangerInit;
import lightsearch.server.data.LightSearchListenerDTO;
import lightsearch.server.data.LightSearchListenerDTOInit;
import lightsearch.server.thread.ThreadHolder;
import lightsearch.server.thread.ThreadManager;
import lightsearch.server.thread.ThreadHolderInit;
import lightsearch.server.thread.ThreadManagerInit;
import lightsearch.server.listener.LightSearchServerListenerInit;
import lightsearch.server.listener.LightSearchServerListener;
import lightsearch.server.data.LightSearchServerDTOInit;
import lightsearch.server.data.LightSearchServerDatabaseDTO;
import lightsearch.server.data.LightSearchServerDatabaseDTOInit;
import lightsearch.server.data.LightSearchServerSettingsDAOInit;
import lightsearch.server.initialization.*;
import lightsearch.server.iterator.IteratorDatabaseRecord;
import lightsearch.server.iterator.IteratorDatabaseRecordInit;
import lightsearch.server.iterator.IteratorDatabaseRecordReader;
import lightsearch.server.iterator.IteratorDatabaseRecordReaderInit;
import lightsearch.server.iterator.IteratorDatabaseRecordWriter;
import lightsearch.server.iterator.IteratorDatabaseRecordWriterInit;
import lightsearch.server.log.LogDirectory;
import lightsearch.server.log.LogDirectoryInit;
import lightsearch.server.log.LoggerFile;
import lightsearch.server.log.LoggerFileInit;
import lightsearch.server.log.LoggerServer;
import lightsearch.server.log.LoggerServerInit;
import lightsearch.server.log.LoggerWindow;
import lightsearch.server.log.LoggerWindowInit;
import lightsearch.server.thread.LightSearchThread;
import lightsearch.server.time.CurrentDateTime;
import lightsearch.server.time.CurrentDateTimeInit;
import lightsearch.server.data.LightSearchServerDTO;
import lightsearch.server.data.LightSearchServerSettingsDAO;
import lightsearch.server.timer.TimersIDEnum;

/*v. a.b.c:
    a {uint} - Very significant update(for example, adding a new features, 
              fundamental refactoring, e.t.c),
    b {uint} - Error correction usually,
    c {uint} - Minor internal server changes.
    */

/**
 *
 * @author ViiSE
 */
public class LightSearchServer {
    
    /**
     *
     * @param argc
     * @throws InterruptedException
     */
    public static void main(String[] argc) throws InterruptedException {
        System.out.println("LightSearch Server, v. 2.8.0");
        System.out.println("Welcome!");
        
        CurrentServerDirectory currentServerDirectory = 
                CurrentServerDirectoryInit.currentDirectory(OsDetectorInit.osDetector());
        String currentDirectory = currentServerDirectory.currentDirectory();
        
        ServerPort serverPort = ServerPortInit.serverPort(currentServerDirectory);
        int port = serverPort.port();
        
        ServerSettings serverSettings = 
                ServerSettingsInit.serverSettings(currentServerDirectory);
        int serverReboot = serverSettings.rebootServerValue();
        int clientTimeout = serverSettings.timeoutClientValue();
        
        AdministratorsMap administratorsMap = 
                AdministratorsMapInit.administratorsMap(currentServerDirectory);
        Map<String, String> admins = administratorsMap.administratorsMap();

        ClientBlacklist clientBlacklist = 
                ClientBlacklistInit.clientBlacklist(currentServerDirectory);
        List<String> blacklist = clientBlacklist.blacklist();
        
        DatabaseSettings dbSettings = 
                DatabaseSettingsInit.databaseSettings(currentServerDirectory);
        String dbName = dbSettings.name();
        int dbPort = dbSettings.port();
        String dbIp   = dbSettings.ip();
        
        LightSearchServerSettingsDAO settingsDAO = 
                LightSearchServerSettingsDAOInit.settingsDAO(serverReboot, clientTimeout);
        
        LightSearchServerDatabaseDTO databaseDTO = 
                LightSearchServerDatabaseDTOInit.lightSearchServerDatabaseDTO(dbIp, 
                dbName,
                dbPort);
        
        LightSearchServerDTO serverDTO = LightSearchServerDTOInit.LightSearchServerDTO(
                admins, 
                new HashMap<>(), 
                blacklist, 
                databaseDTO, 
                port,
                settingsDAO,
                currentDirectory);
        
        LogDirectory logDirectory = LogDirectoryInit.logDirectory("logs");
        LoggerFile loggerFile = LoggerFileInit.loggerFile(logDirectory);
        LoggerWindow loggerWindow = LoggerWindowInit.loggerWindow();
        
        LoggerServer loggerServer = LoggerServerInit.loggerServer(loggerFile, loggerWindow);
        
        CurrentDateTime currentDateTime = CurrentDateTimeInit.currentDateTime();
        
        HashMap<String, LightSearchThread> threads = new HashMap<>();
        ThreadHolder threadHolder = ThreadHolderInit.threadHolder(threads);
        ThreadManager threadManager = ThreadManagerInit.threadManager(threadHolder);
        
        IteratorDatabaseRecordReader iteratorReader = 
                IteratorDatabaseRecordReaderInit.iteratorDatabaseRecordReader(serverDTO);
        IteratorDatabaseRecordWriter iteratorWriter = 
                IteratorDatabaseRecordWriterInit.iteratorDatabaseRecordWriter(serverDTO);
        
        IteratorDatabaseRecord iterator = 
                IteratorDatabaseRecordInit.iteratorDatabaseRecord(iteratorReader.read());
        
        LightSearchChecker checker = LightSearchCheckerInit.lightSearchChecker();
        
        TimersIDEnum timerRebootId = TimersIDEnum.REBOOT_TIMER_ID;
        
        LightSearchListenerDTO listenerDTO = LightSearchListenerDTOInit.lightSearchListenerDTO(
                checker, currentDateTime, threadManager, iterator, iteratorWriter, timerRebootId);
        
        System.out.println("Now Logging is in the folder logs and this window. "
                + "For administration use LightSearch Server Admin Panel.");        
        Thread.sleep(100);
        
        ServerStateChanger stateChanger = ServerStateChangerInit.serverStateChanger(serverDTO, 
                    loggerServer, currentDateTime, threadManager);
        
        TimerRebootValueChecker timerChecker = 
                TimerRebootValueCheckerInit.timerRebootValueChecker();
        if(timerChecker.check(serverDTO.settingsDAO().serverRebootValue()))
            stateChanger.executeRebootTimer(timerRebootId);
        
        TimersIDEnum timerIteratorId = TimersIDEnum.ITERATOR_WRITER_TIMER_ID;
        long minutesToWrite = 30;
        stateChanger.executeIteratorDatabaseRecordWriterTimer(iterator, iteratorWriter, 
                minutesToWrite, timerIteratorId);
        
        LightSearchServerListener serverListener = 
                LightSearchServerListenerInit.lightSearchServerListener(
                        serverDTO,
                        listenerDTO,
                        loggerServer
        );
        serverListener.startServer();
    }
}