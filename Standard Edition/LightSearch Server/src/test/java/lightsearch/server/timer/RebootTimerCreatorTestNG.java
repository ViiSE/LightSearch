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
package lightsearch.server.timer;

import lightsearch.server.initialization.CurrentServerDirectoryInit;
import lightsearch.server.initialization.OsDetector;
import lightsearch.server.initialization.OsDetectorInit;
import lightsearch.server.log.LoggerServer;
import lightsearch.server.thread.ThreadManager;
import lightsearch.server.time.CurrentDateTime;
import lightsearch.server.time.CurrentDateTimeInit;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import test.data.DataProviderCreator;

import java.time.LocalDateTime;

import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class RebootTimerCreatorTestNG {
    
    @Test
    @Parameters({"logDirectory"})
    public void getTimer(String logDir) {
        testBegin("RebootTimerCreator", "getTimer()");
        
        LocalDateTime serverDateTimeRebootValue = LocalDateTime.now().plusMinutes(1);

        OsDetector osDetector = OsDetectorInit.osDetector();
        String currentDirectory = CurrentServerDirectoryInit.currentDirectoryDebug(osDetector).currentDirectory();

        assertNotNull(currentDirectory, "CurrentDirectory is null!");
        assertNotEquals(currentDirectory, "", "CurrentDirectory is null!");

        LoggerServer loggerServer = DataProviderCreator.createDataProvider(LoggerServer.class, logDir);
        assertNotNull(loggerServer, "Logger server is null!");
        
        CurrentDateTime currentDateTime = CurrentDateTimeInit.currentDateTime();
        
        ThreadManager threadManager = DataProviderCreator.createDataProvider(ThreadManager.class);
        assertNotNull(threadManager, "Thread manager is null!");
        
        TimersIDEnum id = TimersIDEnum.REBOOT_TIMER_ID;
        
        RebootTimerCreator rebootTimerCreator =
                RebootTimerCreatorInit.rebootTimerCreator(serverDateTimeRebootValue, currentDirectory, loggerServer,
                        currentDateTime, threadManager, id);
        
        System.out.println("rebootTimerCreator.getTimer(): " + rebootTimerCreator.getTimer());
        
        testEnd("RebootTimerCreator", "getTimer()");
    }
}
