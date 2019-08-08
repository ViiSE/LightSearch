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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import lightsearch.server.checker.LightSearchChecker;
import lightsearch.server.checker.LightSearchCheckerInit;
import lightsearch.server.cmd.admin.AdminCommand;
import lightsearch.server.cmd.admin.AdminCommandCreator;
import lightsearch.server.cmd.admin.AdminCommandCreatorInit;
import lightsearch.server.cmd.result.CommandResult;
import lightsearch.server.data.AdminDAO;
import lightsearch.server.data.AdminDAOInit;
import lightsearch.server.data.LightSearchListenerDTO;
import lightsearch.server.data.LightSearchListenerDTOInit;
import lightsearch.server.data.LightSearchServerDTOInit;
import lightsearch.server.data.LightSearchServerDatabaseDTO;
import lightsearch.server.data.LightSearchServerDatabaseDTOInit;
import lightsearch.server.data.LightSearchServerSettingsDAOInit;
import lightsearch.server.initialization.CurrentServerDirectory;
import lightsearch.server.initialization.CurrentServerDirectoryInit;
import lightsearch.server.initialization.OsDetector;
import lightsearch.server.initialization.OsDetectorInit;
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
public class AdminCommandCreatorTestNG {
    
    private LightSearchServerDTO serverDTOInit() {
        Map<String,String> admins = new HashMap<>();
        Map<String,String> clients = new HashMap<>();
        List<String> blacklist = new ArrayList<>();
        
        String dbIP = "127.0.0.1";
        assertNotNull(dbIP, "Database IP is null!");
        assertFalse(dbIP.equals(""), "Database IP is null!");
        
        String dbName = "dbExample";
        assertNotNull(dbName, "Database name is null!");
        assertFalse(dbName.equals(""), "Database name is null!");
        
        int dbPort = 12345;
        assertFalse(dbPort < 0 && dbPort > 655535, "Wrong database port!");
        
        LightSearchServerDatabaseDTO databaseDTO = LightSearchServerDatabaseDTOInit.lightSearchServerDatabaseDTO(dbIP, dbName, dbPort);
        
        
        int serverPort = 50000;
        assertFalse(serverPort < 0 && serverPort > 655535, "Wrong server port!");
        
        
        int serverReboot = 0;
        assertFalse(serverReboot < 0, "Server reboot value is less than 0!");
        
        int clientTimeout = 0;
        assertFalse(clientTimeout < 0, "Client timeout value is less than 0!");
        
        LightSearchServerSettingsDAO settingsDTO = LightSearchServerSettingsDAOInit.settingsDAO(serverReboot, clientTimeout);
        
        OsDetector osDetector = OsDetectorInit.osDetector();
        CurrentServerDirectory currentServerDirectory = CurrentServerDirectoryInit.currentDirectory(osDetector);
        String currentDirectory = currentServerDirectory.currentDirectory();
        assertNotNull(currentDirectory, "Current directory is null!");
        
        LightSearchServerDTO serverDTO = LightSearchServerDTOInit.LightSearchServerDTO(admins, clients, blacklist, databaseDTO, serverPort, settingsDTO, currentDirectory);
        
        return serverDTO;
    }
    
    private LoggerServer loggerServerInit() {
        String logDirectoryName = "logs";
        LogDirectory logDirectory = LogDirectoryInit.logDirectory(logDirectoryName);
        LoggerFile loggerFile = LoggerFileInit.loggerFile(logDirectory);
        LoggerWindow loggerWindow = LoggerWindowInit.loggerWindow();
        LoggerServer logger = LoggerServerInit.loggerServer(loggerFile, loggerWindow);
        
        return logger;
    }
    
    private ThreadManager initThreadManager() {
        HashMap<String, LightSearchThread> threads = new HashMap<>();
        ThreadHolder holder = ThreadHolderInit.threadHolder(threads);
        ThreadManager threadManager = ThreadManagerInit.threadManager(holder);
        
        return threadManager;
    }
    
    private IteratorDatabaseRecord initIterator(LightSearchServerDTO serverDTO) {
        IteratorDatabaseRecordReader iteratorReader = IteratorDatabaseRecordReaderInit.iteratorDatabaseRecordReader(serverDTO);
        IteratorDatabaseRecord iterator = IteratorDatabaseRecordInit.iteratorDatabaseRecord(iteratorReader.read());
        return iterator;
    }
    
    private LightSearchListenerDTO initListenerDTO(LightSearchServerDTO serverDTO) {
        CurrentDateTime currentDateTime = CurrentDateTimeInit.currentDateTime();
        ThreadManager threadManager = initThreadManager();
        TimersIDEnum timerRebootId = TimersIDEnum.REBOOT_TIMER_ID;
        LightSearchChecker checker = LightSearchCheckerInit.lightSearchChecker();
        IteratorDatabaseRecord iteratorDatabaseRecord = initIterator(serverDTO);
        IteratorDatabaseRecordWriter iteratorDatabaseRecordWriter = IteratorDatabaseRecordWriterInit.iteratorDatabaseRecordWriter(serverDTO);
        
        LightSearchListenerDTO listenerDTO = LightSearchListenerDTOInit.lightSearchListenerDTO(
                checker, currentDateTime, threadManager, iteratorDatabaseRecord, 
                iteratorDatabaseRecordWriter, timerRebootId);
        
        return listenerDTO;
    }
    
    @Test
    public void createCommandHolder() {
        testBegin("AdminCommandCreator", "createCommandHolder()");
        
        LightSearchServerDTO serverDTO = serverDTOInit();
        LoggerServer logger = loggerServerInit();
        LightSearchListenerDTO listenerDTO = initListenerDTO(serverDTO);
        AdminDAO adminDAO = AdminDAOInit.adminDAO();
        
        AdminCommandCreator admCmdCreator = AdminCommandCreatorInit.adminCommandCreator(serverDTO,
                listenerDTO, logger, adminDAO);
        Map<String, Function<AdminCommand, CommandResult>> cmdHolder = admCmdCreator.createCommandHolder();
        assertNotNull(cmdHolder, "Command holder is null!");
        System.out.println("Command holder: " + cmdHolder); 
        
        testEnd("AdminCommandCreator", "createCommandHolder()");
    }
}
