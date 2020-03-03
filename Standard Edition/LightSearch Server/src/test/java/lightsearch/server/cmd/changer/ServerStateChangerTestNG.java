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
package lightsearch.server.cmd.changer;

import lightsearch.server.data.LightSearchServerDTO;
import lightsearch.server.identifier.DatabaseRecordIdentifier;
import lightsearch.server.identifier.DatabaseRecordIdentifierWriter;
import lightsearch.server.identifier.DatabaseRecordIdentifierWriterInit;
import lightsearch.server.log.LoggerServer;
import lightsearch.server.thread.ThreadManager;
import lightsearch.server.time.CurrentDateTime;
import lightsearch.server.time.CurrentDateTimeInit;
import lightsearch.server.timer.TimersIDEnum;
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
public class ServerStateChangerTestNG {
    
    @Test(priority = 1)
    @Parameters({"logDirectoryName"})
    public void executeRebootTimer(String logDirectoryName) {
        testBegin("ServerStateChanger", "executeRebootTimer()");
        
        LightSearchServerDTO serverDTO = DataProviderCreator.createDataProvider(LightSearchServerDTO.class);
        LoggerServer logger = DataProviderCreator.createDataProvider(LoggerServer.class, logDirectoryName);
        CurrentDateTime currentDateTime = CurrentDateTimeInit.currentDateTime();
        ThreadManager threadManager = DataProviderCreator.createDataProvider(ThreadManager.class);
        ServerStateChanger serverChanger =
                ServerStateChangerInit.serverStateChanger(serverDTO, logger, currentDateTime, threadManager);
        assertNotNull(serverChanger, "Server changer is null!");
        
        TimersIDEnum timerId = TimersIDEnum.REBOOT_TIMER_ID;
        serverChanger.executeRebootTimer(timerId);
        System.out.println("RebootTimer is start");
        System.out.println("ThreadManager:threadHolder BEFORE: ");
        threadManager.holder().getThreads().forEach((thread) ->
                System.out.println("\t" + thread + ": id:" + threadManager.holder().getId(thread)));
        
        System.out.println("Wait 2 seconds BEFORE");
        TestUtils.sleep(2000);
        
        serverChanger.destroyRebootTimer(timerId);
        System.out.println("RebootTimer is destroy");
        System.out.println("ThreadManager:threadHolder AFTER: ");
        threadManager.holder().getThreads().forEach((thread) ->
                System.out.println("\t" + thread + ": id:" + threadManager.holder().getId(thread)));
        
        System.out.println("Wait 2 seconds AFTER");
        TestUtils.sleep(2000);
        
        testEnd("ServerStateChanger", "executeRebootTimer()");
    }
    
    @Test(priority = 2)
    @Parameters({"logDirectoryName"})
    public void destroyRebootTimer(String logDirectoryName) {
        testBegin("ServerStateChanger", "destroyRebootTimer()");
        
        LightSearchServerDTO serverDTO = DataProviderCreator.createDataProvider(LightSearchServerDTO.class);
        LoggerServer logger = DataProviderCreator.createDataProvider(LoggerServer.class, logDirectoryName);
        CurrentDateTime currentDateTime = CurrentDateTimeInit.currentDateTime();
        ThreadManager threadManager = DataProviderCreator.createDataProvider(ThreadManager.class);
        ServerStateChanger serverChanger =
                ServerStateChangerInit.serverStateChanger(serverDTO, logger, currentDateTime, threadManager);
        assertNotNull(serverChanger, "Server changer is null!");
        
        TimersIDEnum timerId = TimersIDEnum.REBOOT_TIMER_ID;
        serverChanger.executeRebootTimer(timerId);
        System.out.println("RebootTimer is start");
        System.out.println("ThreadManager:threadHolder BEFORE: ");
        threadManager.holder().getThreads().forEach((thread) -> {
            System.out.println("\t" + thread + ": id:" + threadManager.holder().getId(thread));
        });
        
        System.out.println("Wait 2 seconds BEFORE");
        TestUtils.sleep(2000);
        
        serverChanger.destroyRebootTimer(timerId);
        System.out.println("RebootTimer is destroy");
        System.out.println("ThreadManager:threadHolder AFTER: ");
        threadManager.holder().getThreads().forEach((thread) -> System.out.println("\t" + thread));
        
        System.out.println("Wait 2 seconds AFTER");
        TestUtils.sleep(2000);
        
        testEnd("ServerStateChanger", "destroyRebootTimer()");
    }
    
    @Test(priority = 3)
    @Parameters({"logDirectoryName", "minutesToWrite"})
    public void executeDatabaseRecordIdentifierWriterTimer(String logDirectoryName, long minutesToWrite) {
        testBegin("ServerStateChanger", "executeDatabaseRecordIdentifierWriterTimer()");
        
        LightSearchServerDTO serverDTO = DataProviderCreator.createDataProvider(LightSearchServerDTO.class);
        LoggerServer logger = DataProviderCreator.createDataProvider(LoggerServer.class, logDirectoryName);
        CurrentDateTime currentDateTime = CurrentDateTimeInit.currentDateTime();
        ThreadManager threadManager = DataProviderCreator.createDataProvider(ThreadManager.class);
        ServerStateChanger serverChanger =
                ServerStateChangerInit.serverStateChanger(serverDTO, logger, currentDateTime, threadManager);
        assertNotNull(serverChanger, "Server changer is null!");
        
        DatabaseRecordIdentifier identifier = DataProviderCreator.createDataProvider(DatabaseRecordIdentifier.class, serverDTO);
        DatabaseRecordIdentifierWriter identifierWriter =
                DatabaseRecordIdentifierWriterInit.databaseRecordIdentifierWriter(serverDTO);
        TimersIDEnum timerId = TimersIDEnum.IDENTIFIER_WRITER_TIMER_ID;
        serverChanger.executeDatabaseRecordIdentifierWriterTimer(identifier, identifierWriter, minutesToWrite, timerId);
        System.out.println("DatabaseRecordIdentifierWriterTimer is start");
        System.out.println("ThreadManager:threadHolder BEFORE: ");
        System.out.println("Increment databaseRecordIdentifier value: ");
        System.out.println("\t BEFORE Incrementation: " + identifier.databaseRecordIdentifier());
        System.out.println("\t AFTER Incrementation: " + identifier.next());
        threadManager.holder().getThreads().forEach((thread) ->
                System.out.println("\t" + thread + ": id:" + threadManager.holder().getId(thread)));
        System.out.println("Wait 2 seconds BEFORE...");
        TestUtils.sleep(2000);
        
        serverChanger.destroyDatabaseRecordIdentifierWriterTimer(timerId);
        System.out.println("DatabaseRecordIdentifierWriterTimer is destroy");
        System.out.println("ThreadManager:threadHolder AFTER: ");
        threadManager.holder().getThreads().forEach((thread) ->
                System.out.println("\t" + thread + ": id:" + threadManager.holder().getId(thread)));
        
        System.out.println("Wait 2 seconds AFTER...");
        TestUtils.sleep(2000);
    
        testEnd("ServerStateChanger", "executeDatabaseRecordIdentifierWriterTimer()");
    }
    
    @Test(priority = 4)
    @Parameters({"logDirectoryName", "minutesToWrite"})
    public void destroyDatabaseRecordIdentifierWriterTimer(String logDirectoryName, long minutesToWrite) {
        testBegin("ServerStateChanger", "destroyDatabaseRecordIdentifierWriterTimer()");
        
        LightSearchServerDTO serverDTO = DataProviderCreator.createDataProvider(LightSearchServerDTO.class);
        LoggerServer logger = DataProviderCreator.createDataProvider(LoggerServer.class, logDirectoryName);
        CurrentDateTime currentDateTime = CurrentDateTimeInit.currentDateTime();
        ThreadManager threadManager = DataProviderCreator.createDataProvider(ThreadManager.class);
        ServerStateChanger serverChanger =
                ServerStateChangerInit.serverStateChanger(serverDTO, logger, currentDateTime, threadManager);
        assertNotNull(serverChanger, "Server changer is null!");
        
        DatabaseRecordIdentifier identifier = DataProviderCreator.createDataProvider(DatabaseRecordIdentifier.class, serverDTO);
        DatabaseRecordIdentifierWriter identifierWriter =
                DatabaseRecordIdentifierWriterInit.databaseRecordIdentifierWriter(serverDTO);
        TimersIDEnum timerId = TimersIDEnum.IDENTIFIER_WRITER_TIMER_ID;
        serverChanger.executeDatabaseRecordIdentifierWriterTimer(identifier, identifierWriter, minutesToWrite, timerId);
        System.out.println("DatabaseRecordIdentifierWriterTimer is start");
        System.out.println("ThreadManager:threadHolder BEFORE: ");
        System.out.println("Increment databaseRecordIdentifier value: ");
        System.out.println("\t BEFORE Incrementation: " + identifier.databaseRecordIdentifier());
        System.out.println("\t AFTER Incrementation: " + identifier.next());
        threadManager.holder().getThreads().forEach((thread) ->
                System.out.println("\t" + thread + ": id:" + threadManager.holder().getId(thread)));
        System.out.println("Wait for 1 minute BEFORE...");
        TestUtils.sleep(60000);
        
        serverChanger.destroyDatabaseRecordIdentifierWriterTimer(timerId);
        System.out.println("DatabaseRecordIdentifierWriterTimer is destroy");
        System.out.println("ThreadManager:threadHolder AFTER: ");
        threadManager.holder().getThreads().forEach((thread) ->
                System.out.println("\t" + thread + ": id:" + threadManager.holder().getId(thread)));

        System.out.println("Wait for 1 minute AFTER...");
        TestUtils.sleep(60000);

        testEnd("ServerStateChanger", "destroyDatabaseRecordIdentifierWriterTimer()");
    }
}
