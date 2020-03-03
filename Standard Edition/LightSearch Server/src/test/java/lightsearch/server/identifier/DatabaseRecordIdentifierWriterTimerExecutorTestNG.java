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
package lightsearch.server.identifier;

import lightsearch.server.data.LightSearchServerDTO;
import lightsearch.server.log.LoggerServer;
import lightsearch.server.thread.ThreadManager;
import lightsearch.server.time.CurrentDateTime;
import lightsearch.server.time.CurrentDateTimeInit;
import lightsearch.server.timer.*;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import test.TestUtils;
import test.data.DataProviderCreator;

import static org.testng.Assert.assertNotNull;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class DatabaseRecordIdentifierWriterTimerExecutorTestNG {

    
    @Test
    @Parameters({"logDirectory", "minutesToWrite"})
    public void databaseRecordIdentifierWriterTimerExecutor(String logDir, long minutesToWrite) {
        testBegin("DatabaseRecordIdentifierWriterTimerExecutor", "databaseRecordIdentifierWriterTimerExecutor()");
        
        LoggerServer loggerServer = DataProviderCreator.createDataProvider(LoggerServer.class, logDir);
        assertNotNull(loggerServer, "Logger server is null!");
        
        CurrentDateTime currentDateTime = CurrentDateTimeInit.currentDateTime();
        
        ThreadManager threadManager = DataProviderCreator.createDataProvider(ThreadManager.class);
        assertNotNull(threadManager, "Thread manager is null!");
        
        LightSearchServerDTO serverDTO = DataProviderCreator.createDataProvider(LightSearchServerDTO.class);
        assertNotNull(serverDTO, "ServerDTO is null!");
        
        DatabaseRecordIdentifier identifier = DataProviderCreator.createDataProvider(DatabaseRecordIdentifier.class, serverDTO);
        assertNotNull(identifier, "DatabaseRecordIdentifier is null!");
        
        DatabaseRecordIdentifierWriter identifierWriter =
                DatabaseRecordIdentifierWriterInit.databaseRecordIdentifierWriter(serverDTO);
        assertNotNull(identifierWriter, "DatabaseRecordIdentifierWriter is null!");
        
        TimersIDEnum id = TimersIDEnum.IDENTIFIER_WRITER_TIMER_ID;
        
        DatabaseRecordIdentifierWriterTimerCreator identifierTimerCreator =
                DatabaseRecordIdentifierWriterTimerCreatorInit.databaseRecordIdentifierWriterTimerCreator(
                        loggerServer, currentDateTime, threadManager, identifierWriter, identifier, minutesToWrite, id);
        
        DatabaseRecordIdentifierWriterTimerExecutor identifierTimerExecutor =
                DatabaseRecordIdentifierWriterTimerExecutorInit.databaseRecordIdentifierWriterTimerExecutor(
                        identifierTimerCreator.getTimer());
        
        System.out.println("DatabaseRecordIdentifierWriter Value BEFORE: " + identifier.databaseRecordIdentifier());
        identifier.next();
        identifier.next();
        System.out.println("Trying to write DatabaseRecordIdentifierWriter Value: " + identifier.databaseRecordIdentifier());
        
        identifierTimerExecutor.startDatabaseRecordIdentifierWriterTimer();
        
        System.out.println("Wait 1.5 minute...");
        TestUtils.sleep(90000);
        
        java.awt.Toolkit.getDefaultToolkit().beep();
        
        identifier.next();
        identifier.next();
        System.out.println("Trying to write DatabaseRecordIdentifierWriter Value: " + identifier.databaseRecordIdentifier());
        
        System.out.println("Wait 1 minute...");
        TestUtils.sleep(60000);
        
        testEnd("DatabaseRecordIdentifierWriterTimerExecutor", "databaseRecordIdentifierWriterTimerExecutor()");
    }
}
