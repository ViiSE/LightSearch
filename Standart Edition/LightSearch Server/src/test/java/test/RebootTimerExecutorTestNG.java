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

import java.time.LocalDateTime;
import java.util.HashMap;
import lightsearch.server.initialization.CurrentServerDirectory;
import lightsearch.server.initialization.CurrentServerDirectoryInit;
import lightsearch.server.initialization.OsDetector;
import lightsearch.server.initialization.OsDetectorInit;
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
import lightsearch.server.timer.RebootTimerCreator;
import lightsearch.server.timer.RebootTimerCreatorInit;
import lightsearch.server.timer.RebootTimerExecutor;
import lightsearch.server.timer.RebootTimerExecutorInit;
import lightsearch.server.timer.TimersIDEnum;
import static org.testng.Assert.*;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class RebootTimerExecutorTestNG {
    
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
    
    @Test
    public void startRebootTimer() {
        testBegin("RebootTimerExecutor", "startRebootTimer()");
        
        LocalDateTime serverDateTimeRebootValue = LocalDateTime.now().plusMinutes(1);
        
        String currentDirectory = initCurrentDirectory();
        assertNotNull(currentDirectory, "CurrentDirectory is null!");
        assertFalse(currentDirectory.equals(""), "CurrentDirectory is null!");
        
        LoggerServer loggerServer = initLoggerServer();
        assertNotNull(loggerServer, "Logger server is null!");
        
        CurrentDateTime currentDateTime = CurrentDateTimeInit.currentDateTime();
        
        ThreadManager threadManager = initThreadManager();
        assertNotNull(threadManager, "Thread manager is null!");
        
        TimersIDEnum id = TimersIDEnum.REBOOT_TIMER_ID;
        
        RebootTimerCreator rebootTimerCreator = RebootTimerCreatorInit.rebootTimerCreator(
                serverDateTimeRebootValue, currentDirectory, loggerServer, 
                currentDateTime, threadManager, id);
        
        RebootTimerExecutor rebootTimerExecutor = RebootTimerExecutorInit.rebootTimerExecutor(rebootTimerCreator.getTimer());
        rebootTimerExecutor.startRebootTimer();
        System.out.println("Wait 2 minute...");
        
        try { Thread.sleep(120000); } catch (InterruptedException ignore) { }
        
        testEnd("RebootTimerExecutor", "startRebootTimer()");
    }
}
