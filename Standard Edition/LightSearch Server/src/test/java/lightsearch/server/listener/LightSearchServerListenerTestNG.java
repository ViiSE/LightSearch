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
package lightsearch.server.listener;

import lightsearch.server.checker.LightSearchChecker;
import lightsearch.server.checker.LightSearchCheckerInit;
import lightsearch.server.cmd.changer.ServerStateChanger;
import lightsearch.server.cmd.changer.ServerStateChangerInit;
import lightsearch.server.data.*;
import lightsearch.server.identifier.*;
import lightsearch.server.initialization.*;
import lightsearch.server.log.LoggerServer;
import lightsearch.server.thread.*;
import lightsearch.server.time.CurrentDateTime;
import lightsearch.server.time.CurrentDateTimeInit;
import lightsearch.server.timer.TimersIDEnum;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import test.data.DataProviderCreator;

import java.util.HashMap;
import java.util.regex.Pattern;

import static org.testng.Assert.*;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class LightSearchServerListenerTestNG {
    
    private static final Pattern PATTERN = Pattern.compile(
        "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
    
    private static boolean validate(final String ip) {
        return PATTERN.matcher(ip).matches();
    }
    
    @Test
    @Parameters({"ip", "logDirectory", "minutesToWrite", "openTest"})
    public void startListener(String ip, String logDir, long minutesToWrite, boolean openTest) {
        testBegin("LightSearchServerListener", "startListener()");

        CurrentServerDirectory currentServerDirectory =
                CurrentServerDirectoryInit.currentDirectory(OsDetectorInit.osDetector());
        String currentDirectory = currentServerDirectory.currentDirectory();
        assertNotNull(currentDirectory, "CurrentDirectory is null!");
        assertNotEquals(currentDirectory, "", "CurrentDirectory is null!");

        ServerPort serverPort = ServerPortInit.serverPort(currentServerDirectory);
        int port = serverPort.port();
        assertFalse(port < 1023, "Wrong Server port!");
        assertFalse(port > 65535, "Wrong Server port!");

        ServerSettings serverSettings = ServerSettingsInit.serverSettings(currentServerDirectory);
        int serverReboot = serverSettings.rebootServerValue();
        int clientTimeout = serverSettings.timeoutClientValue();
        assertFalse(serverReboot < 0, "ServerReboot Value is less than 0!");
        assertFalse(clientTimeout < 0, "ClientTimeout Value is less than 0!");
        
        LightSearchServerSettingsDAO settingsDAO = LightSearchServerSettingsDAOInit.settingsDAO(serverReboot, clientTimeout);
        assertNotNull(settingsDAO, "settingsDAO is null!");
        
        LightSearchServerDTO serverDTO = DataProviderCreator.createDataProvider(LightSearchServerDTO.class);
        assertNotNull(serverDTO, "ServerDTO is null!");

        LoggerServer loggerServer = DataProviderCreator.createDataProvider(LoggerServer.class, logDir);
        assertNotNull(loggerServer, "LoggerServer is null!");
        
        CurrentDateTime currentDateTime = CurrentDateTimeInit.currentDateTime();
        assertNotNull(currentDateTime, "CurrentDateTime is null!");
        
        HashMap<String, LightSearchThread> threads = new HashMap<>();
        ThreadHolder threadHolder = ThreadHolderInit.threadHolder(threads);
        ThreadManager threadManager = ThreadManagerInit.threadManager(threadHolder);
        assertNotNull(threadManager, "ThreadManager is null!");
        
        DatabaseRecordIdentifierReader identifierReader =
                DatabaseRecordIdentifierReaderInit.databaseRecordIdentifierReader(serverDTO);
        assertNotNull(identifierReader, "IdentifierReader is null!");
        DatabaseRecordIdentifierWriter identifierWriter =
                DatabaseRecordIdentifierWriterInit.databaseRecordIdentifierWriter(serverDTO);
        assertNotNull(identifierWriter, "IdentifierWriter is null!");
        
        DatabaseRecordIdentifier identifier = DatabaseRecordIdentifierInit.databaseRecordIdentifier(identifierReader.read());
        assertNotNull(identifier, "Identifier is null!");
        
        LightSearchChecker checker = LightSearchCheckerInit.lightSearchChecker();
        assertNotNull(checker, "Checker is null!");

        TimersIDEnum timerRebootId = TimersIDEnum.REBOOT_TIMER_ID;
        assertNotNull(timerRebootId, "TimerRebootId is null!");
        assertFalse(timerRebootId.stringValue().isEmpty(), "TimerRebootId is null!");
        
        LightSearchListenerDTO listenerDTO =
                LightSearchListenerDTOInit.lightSearchListenerDTO(checker, currentDateTime, threadManager, identifier,
                        identifierWriter, timerRebootId);
        assertNotNull(listenerDTO, "ListenerDTO is null!");
        
        ServerStateChanger stateChanger =
                ServerStateChangerInit.serverStateChanger(serverDTO, loggerServer, currentDateTime, threadManager);
        assertNotNull(stateChanger, "StateChanger is null!");
        
        if(serverDTO.settingsDAO().serverRebootValue() != 0)
            stateChanger.executeRebootTimer(timerRebootId);
        
        TimersIDEnum timerIdentifierId = TimersIDEnum.IDENTIFIER_WRITER_TIMER_ID;
        assertNotNull(timerIdentifierId, "timerIdentifierId is null!");
        
        assertFalse(minutesToWrite < 0, "MinutesToWrite is null!");
        
        stateChanger.executeDatabaseRecordIdentifierWriterTimer(identifier, identifierWriter, minutesToWrite, timerIdentifierId);
        
        LightSearchServerListener serverListener =
                LightSearchServerListenerInit.lightSearchServerListener(serverDTO, listenerDTO, loggerServer);
        
        LightSearchThread adminThread = LightSearchThreadInit.lightSearchThread(new ListenerTestUtils.Admin(ip, port));
        LightSearchThread clientThread = LightSearchThreadInit.lightSearchThread(new ListenerTestUtils.Client(ip, port));
        adminThread.start();
        clientThread.start();

        if(openTest)
            serverListener.startServer();
        
        testEnd("LightSearchServerListener", "startListener()");
    }
}
