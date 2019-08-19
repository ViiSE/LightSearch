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

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import lightsearch.server.checker.LightSearchChecker;
import lightsearch.server.checker.LightSearchCheckerInit;
import lightsearch.server.cmd.changer.ServerStateChanger;
import lightsearch.server.cmd.changer.ServerStateChangerInit;
import lightsearch.server.data.LightSearchListenerDTO;
import lightsearch.server.data.LightSearchListenerDTOInit;
import lightsearch.server.data.LightSearchServerDTOInit;
import lightsearch.server.data.LightSearchServerDatabaseDTO;
import lightsearch.server.data.LightSearchServerDatabaseDTOInit;
import lightsearch.server.data.LightSearchServerSettingsDAOInit;
import lightsearch.server.data.stream.DataStream;
import lightsearch.server.data.stream.DataStreamCreator;
import lightsearch.server.data.stream.DataStreamCreatorInit;
import lightsearch.server.data.stream.DataStreamInit;
import lightsearch.server.exception.DataStreamCreatorException;
import lightsearch.server.initialization.AdministratorsMap;
import lightsearch.server.initialization.AdministratorsMapInit;
import lightsearch.server.initialization.ClientBlacklist;
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
import lightsearch.server.iterator.IteratorDatabaseRecord;
import lightsearch.server.iterator.IteratorDatabaseRecordInit;
import lightsearch.server.iterator.IteratorDatabaseRecordReader;
import lightsearch.server.iterator.IteratorDatabaseRecordReaderInit;
import lightsearch.server.iterator.IteratorDatabaseRecordWriter;
import lightsearch.server.iterator.IteratorDatabaseRecordWriterInit;
import lightsearch.server.listener.LightSearchServerListener;
import lightsearch.server.listener.LightSearchServerListenerInit;
import lightsearch.server.log.LogDirectory;
import lightsearch.server.log.LogDirectoryInit;
import lightsearch.server.log.LoggerFile;
import lightsearch.server.log.LoggerFileInit;
import lightsearch.server.log.LoggerServer;
import lightsearch.server.log.LoggerServerInit;
import lightsearch.server.log.LoggerWindow;
import lightsearch.server.log.LoggerWindowInit;
import lightsearch.server.thread.LightSearchThread;
import lightsearch.server.thread.LightSearchThreadInit;
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
public class LightSearchServerListenerTestNG {
    
    private static final Pattern PATTERN = Pattern.compile(
        "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
    
    private static boolean validate(final String ip) {
        return PATTERN.matcher(ip).matches();
    }
    
    public class Admin implements Runnable {

        @Override
        public void run() {
            try(Socket socket = new Socket("127.0.0.1", 49000);) {
                DataStreamCreator dataStreamCreator = DataStreamCreatorInit.dataStreamCreator(socket);
                dataStreamCreator.createDataStream();
                DataStream dataStream = DataStreamInit.dataStream(dataStreamCreator);
                long i = 0;
                while(i == 0) {

                    String message = "{\"identifier\":\"admin\"}";
                    dataStream.dataOutputStream().writeUTF(message);
                    dataStream.dataOutputStream().flush();

                    String getMessage = dataStream.dataInputStream().readUTF();
                    System.out.println("ADMIN GET MESSAGE: " + getMessage);

                    String message2 = "{"
                                        + "\"command\":\"connect\","
                                        + "\"name\":\"admin\","
                                        + "\"password\":\"5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8\""
                                    + "}";
                    dataStream.dataOutputStream().writeUTF(message2);
                    dataStream.dataOutputStream().flush();

                    System.out.println("ADMIN GET MESSAGE: " + dataStream.dataInputStream().readUTF());
                    
                    try { Thread.sleep(4000); } catch(InterruptedException ignore) {}
                    
                    String message3 = "{"
                                        + "\"name\":\"admin\","
                                        + "\"command\":\"addBlacklist\","
                                        + "\"IMEI\":\"123456789123456\""
                                    + "}";
                    dataStream.dataOutputStream().writeUTF(message3);
                    dataStream.dataOutputStream().flush();

                    System.out.println("ADMIN GET MESSAGE: " + dataStream.dataInputStream().readUTF());
                    
                    try { Thread.sleep(3000); } catch(InterruptedException ignore) {}

                    String message4 = "{"
                                        + "\"name\":\"admin\","
                                        + "\"command\":\"restart\""
                                    + "}";
                    dataStream.dataOutputStream().writeUTF(message4);
                    dataStream.dataOutputStream().flush();

                    i++;
                }
            } 
            catch (IOException | DataStreamCreatorException ignore) { }   
        }   
    }
    
    public class Client implements Runnable {

        @Override
        public void run() {
            try(Socket socket = new Socket("127.0.0.1", 49000);) {
                DataStreamCreator dataStreamCreator = DataStreamCreatorInit.dataStreamCreator(socket);
                dataStreamCreator.createDataStream();
                DataStream dataStream = DataStreamInit.dataStream(dataStreamCreator);
                long i = 0;
                while(i == 0) {
                    String message = "{\"identifier\":\"android\"}";
                    dataStream.dataOutputStream().writeUTF(message);
                    dataStream.dataOutputStream().flush();

                    String getMessage = dataStream.dataInputStream().readUTF();
                    System.out.println("CLIENT GET MESSAGE: " + getMessage);

                    String message2 = "{"
                        + "\"command\":\"connect\","
                        + "\"IMEI\":\"123456789123456\","
                        + "\"ip\":\"127.0.0.1\","
                        + "\"os\":\"Android 8.1 Oreo\","
                        + "\"model\":\"Nexus 5\","
                        + "\"username\":\"androidUser\","
                        + "\"password\":\"superSecretPass!12\""
                    + "}";
                    dataStream.dataOutputStream().writeUTF(message2);
                    dataStream.dataOutputStream().flush();

                    System.out.println("CLIENT GET MESSAGE: " + dataStream.dataInputStream().readUTF());
                    
                    i++;
                }
            } 
            catch (IOException | DataStreamCreatorException ignore) { }   
        }   
    }
    
    @Test
    public void startListener() {
        testBegin("LightSearchServerListener", "startListener()");
        
        CurrentServerDirectory currentServerDirectory = CurrentServerDirectoryInit.currentDirectory(
            OsDetectorInit.osDetector());
        String currentDirectory = currentServerDirectory.currentDirectory();
        assertNotNull(currentDirectory, "CurrentDirectory is null!");
        assertFalse(currentDirectory.equals(""), "CurretnDirectory is null!");
        
        ServerPort serverPort = ServerPortInit.serverPort(currentServerDirectory);
        int port = serverPort.port();
        assertFalse(port < 1023, "Wrong Server port!");
        assertFalse(port > 65535, "Wrong Server port!");
        
        ServerSettings serverSettings = ServerSettingsInit.serverSettings(currentServerDirectory);
        int serverReboot = serverSettings.rebootServerValue();
        int clientTimeout = serverSettings.timeoutClientValue();
        assertFalse(serverReboot < 0, "ServerReboot Value is less than 0!");
        assertFalse(clientTimeout < 0, "ClientTimeout Value is less than 0!");
        
        AdministratorsMap administratorsMap = AdministratorsMapInit.administratorsMap(currentServerDirectory);
        Map<String, String> admins = administratorsMap.administratorsMap();
        assertNotNull(admins, "Amdins Map is null!");
        
        ClientBlacklist clientBlacklist = ClientBlacklistInit.clientBlacklist(currentServerDirectory);
        List<String> blacklist = clientBlacklist.blacklist();
        assertNotNull(blacklist, "Blacklist is null!");
        
        DatabaseSettings dbSettings = DatabaseSettingsInit.databaseSettings(currentServerDirectory);
        String dbName = dbSettings.name();
        int dbPort = dbSettings.port();
        String dbIp   = dbSettings.ip();
        assertNotNull(dbName, "DatabaseName is null!");
        assertFalse(dbName.equals("DatabseName is null!"));
        assertFalse(dbPort < 1023, "Wrong Database port!");
        assertFalse(dbPort > 65535, "Wrong Database port!");
        assertTrue(validate(dbIp), "Wrong Database ip!");
        
        LightSearchServerSettingsDAO settingsDAO = LightSearchServerSettingsDAOInit.settingsDAO(serverReboot, clientTimeout);
        assertNotNull(settingsDAO, "settingsDAO is null!");
        
        LightSearchServerDatabaseDTO databaseDTO = LightSearchServerDatabaseDTOInit.lightSearchServerDatabaseDTO(dbIp, 
                dbName,
                dbPort);
        assertNotNull(databaseDTO, "DatabaseDTO is null!");
        
        LightSearchServerDTO serverDTO = LightSearchServerDTOInit.LightSearchServerDTO(admins, 
                new HashMap<>(), 
                blacklist, 
                databaseDTO, 
                port,
                settingsDAO,
                currentDirectory);
        assertNotNull(serverDTO, "ServerDTO is null!");
        
        LogDirectory logDirectory = LogDirectoryInit.logDirectory("logs");
        assertNotNull(logDirectory, "LogDirectory is null!");
        LoggerFile loggerFile = LoggerFileInit.loggerFile(logDirectory);
        assertNotNull(loggerFile, "LoggerFile is null!");
        LoggerWindow loggerWindow = LoggerWindowInit.loggerWindow();
        assertNotNull(loggerWindow, "LoggerWindow is null!");
        
        LoggerServer loggerServer = LoggerServerInit.loggerServer(loggerFile, loggerWindow);
        assertNotNull(loggerServer, "LoggerServer is null!");
        
        CurrentDateTime currentDateTime = CurrentDateTimeInit.currentDateTime();
        assertNotNull(currentDateTime, "CurrentDateTime is null!");
        
        HashMap<String, LightSearchThread> threads = new HashMap<>();
        ThreadHolder threadHolder = ThreadHolderInit.threadHolder(threads);
        ThreadManager threadManager = ThreadManagerInit.threadManager(threadHolder);
        assertNotNull(threadManager, "ThreadManager is null!");
        
        IteratorDatabaseRecordReader iteratorReader = IteratorDatabaseRecordReaderInit.iteratorDatabaseRecordReader(serverDTO);
        assertNotNull(iteratorReader, "IteratorReader is null!");
        IteratorDatabaseRecordWriter iteratorWriter = IteratorDatabaseRecordWriterInit.iteratorDatabaseRecordWriter(serverDTO);
        assertNotNull(iteratorWriter, "IteratorWriter is null!");
        
        IteratorDatabaseRecord iterator = IteratorDatabaseRecordInit.iteratorDatabaseRecord(iteratorReader.read());
        assertNotNull(iterator, "Iterator is null!");
        
        LightSearchChecker checker = LightSearchCheckerInit.lightSearchChecker();
        assertNotNull(checker, "Checker is null!");
        
        TimersIDEnum timerRebootId = TimersIDEnum.REBOOT_TIMER_ID;
        assertNotNull(timerRebootId, "TimerRebootId is null!");
        assertFalse(timerRebootId.stringValue().isEmpty(), "TimerRebootId is null!");
        
        LightSearchListenerDTO listenerDTO = LightSearchListenerDTOInit.lightSearchListenerDTO(
                checker, currentDateTime, threadManager, iterator, iteratorWriter, timerRebootId);
        assertNotNull(listenerDTO, "ListenerDTO is null!");
        
        ServerStateChanger stateChanger = ServerStateChangerInit.serverStateChanger(serverDTO, 
                    loggerServer, currentDateTime, threadManager);
        assertNotNull(stateChanger, "StateChanger is null!");
        
        if(serverDTO.settingsDAO().serverRebootValue() != 0)
            stateChanger.executeRebootTimer(timerRebootId);
        
        TimersIDEnum timerIteratorId = TimersIDEnum.ITERATOR_WRITER_TIMER_ID;
        assertNotNull(timerIteratorId, "timerIteratorId is null!");
        
        long minutesToWrite = 30;
        assertFalse(minutesToWrite < 0, "MinutesToWrite is null!");
        
        stateChanger.executeIteratorDatabaseRecordWriterTimer(iterator, iteratorWriter, 
                minutesToWrite, timerIteratorId);
        
        LightSearchServerListener serverListener = LightSearchServerListenerInit.lightSearchServerListener(
                serverDTO,
                listenerDTO,
                loggerServer
        );
        
        LightSearchThread adminThread = LightSearchThreadInit.lightSearchThread(new Admin());
        LightSearchThread clientThread = LightSearchThreadInit.lightSearchThread(new Client());
        adminThread.start();
        clientThread.start();
        
        serverListener.startServer();
        
        testEnd("LightSearchServerListener", "startListener()");
    }
}
