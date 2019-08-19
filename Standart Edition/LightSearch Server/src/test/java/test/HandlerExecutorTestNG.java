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
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lightsearch.server.checker.LightSearchChecker;
import lightsearch.server.checker.LightSearchCheckerInit;
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
import lightsearch.server.handler.Handler;
import lightsearch.server.handler.HandlerCreator;
import lightsearch.server.handler.HandlerCreatorInit;
import lightsearch.server.handler.HandlerExecutor;
import lightsearch.server.handler.HandlerExecutorInit;
import lightsearch.server.identifier.ConnectionIdentifier;
import lightsearch.server.identifier.ConnectionIdentifierInit;
import lightsearch.server.identifier.ConnectionIdentifierResult;
import lightsearch.server.exception.ConnectionIdentifierException;
import lightsearch.server.initialization.AdministratorsMapInit;
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
public class HandlerExecutorTestNG {
    
    private boolean close = false;
    
    public class Admin implements Runnable {

        @Override
        public void run() {
            try { 
                Socket socket = new Socket("127.0.0.1", 49000);
                DataStreamCreator dataStreamCreator = DataStreamCreatorInit.dataStreamCreator(socket);
                dataStreamCreator.createDataStream();
                DataStream dataStream = DataStreamInit.dataStream(dataStreamCreator);
                long i = 0;
                while(!close) {
                    if(i == 0) {
                        String message = "{\"identifier\":\"admin\"}";
                        dataStream.dataOutputStream().writeUTF(message);
                        String getMessage = dataStream.dataInputStream().readUTF();
                        System.out.println("ADMIN GET MESSAGE: " + getMessage);
                        i++;
                    }
                }
            } 
            catch (IOException | DataStreamCreatorException ignore) { }   
        }   
    }
    
    public class Client implements Runnable {

        @Override
        public void run() {
            try { 
                Socket socket = new Socket("127.0.0.1", 49000);
                DataStreamCreator dataStreamCreator = DataStreamCreatorInit.dataStreamCreator(socket);
                dataStreamCreator.createDataStream();
                DataStream dataStream = DataStreamInit.dataStream(dataStreamCreator);
                long i = 0;
                while(!close) {
                    if(i == 0) {
                        String message = "{\"identifier\":\"android\"}";
                        dataStream.dataOutputStream().writeUTF(message);
                        String getMessage = dataStream.dataInputStream().readUTF();
                        System.out.println("CLIENT GET MESSAGE: " + getMessage);
                        i++;
                    }
                }
            } 
            catch (IOException | DataStreamCreatorException ignore) { }   
        }   
    }
    
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
    
    private ThreadManager initThreadManager() {
        HashMap<String,LightSearchThread> threads = new HashMap<>();
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
    
    private LoggerServer initLoggerServer() {
        LogDirectory logDir = LogDirectoryInit.logDirectory("logs");
        LoggerFile logFile = LoggerFileInit.loggerFile(logDir);
        LoggerWindow logWindow = LoggerWindowInit.loggerWindow();
        LoggerServer logServer = LoggerServerInit.loggerServer(logFile, logWindow);
        return logServer;
    }
    
    private HandlerExecutor initHandlerExecutor(ThreadManager threadManager) {
        HandlerExecutor handlerExecutor = HandlerExecutorInit.handlerExecutor(threadManager);
        
        return handlerExecutor;
    }
    
    private void adminTest(ServerSocket serverSocket, LightSearchListenerDTO listenerDTO) {
        try {
            LightSearchThread admin = LightSearchThreadInit.lightSearchThread(new Admin());
            admin.start();
            Socket clientSocket = serverSocket.accept();
            
            ConnectionIdentifier connectionIdentifier = ConnectionIdentifierInit.connectionIdentifier();
            ConnectionIdentifierResult connectionIdentifierResult = connectionIdentifier.identifyConnection(clientSocket);
            
            LightSearchServerDTO serverDTO = initDTO();
            assertNotNull(serverDTO, "ServerDTO is null!");
            
            LoggerServer logger = initLoggerServer();
            
            HandlerCreator handlerCreator = HandlerCreatorInit.handlerCreator(
                    connectionIdentifierResult, serverDTO, listenerDTO, logger);
            
            Handler handler = handlerCreator.getHandler();
            
            HandlerExecutor handlerExecutor = initHandlerExecutor(listenerDTO.threadManager());
            assertNotNull(handlerExecutor, "HandlerExecutor is null!");
            
            handlerExecutor.executeHandler(handler);
            
            System.out.println("ExecuteHandler: " + handler);
            System.out.println("HandlerExecutor execute handler: " + handler);
            System.out.println("ThreadManager: ");
            listenerDTO.threadManager().holder().getThreads().forEach((thread) -> {
                System.out.println("\t ThreadName: " + listenerDTO.threadManager().holder().getId(thread)
                + "; Thread: " + thread);
            });
            
        } catch (IOException | ConnectionIdentifierException ex) {
            System.out.println("CATCH! adminTest: " + ex.getMessage());
        }
    }
    
    private void clientTest(ServerSocket serverSocket, LightSearchListenerDTO listenerDTO) {
        try {
            LightSearchThread client = LightSearchThreadInit.lightSearchThread(new Client());
            client.start();
            Socket clientSocket = serverSocket.accept();
            
            ConnectionIdentifier connectionIdentifier = ConnectionIdentifierInit.connectionIdentifier();
            ConnectionIdentifierResult connectionIdentifierResult = connectionIdentifier.identifyConnection(clientSocket);
            
            LightSearchServerDTO serverDTO = initDTO();
            assertNotNull(serverDTO, "ServerDTO is null!");
            
            LoggerServer logger = initLoggerServer();
            
            HandlerCreator handlerCreator = HandlerCreatorInit.handlerCreator(
                    connectionIdentifierResult, serverDTO, listenerDTO, logger);
            
            Handler handler = handlerCreator.getHandler();
            
            HandlerExecutor handlerExecutor = initHandlerExecutor(listenerDTO.threadManager());
            assertNotNull(handlerExecutor, "HandlerExecutor is null!");
            
            handlerExecutor.executeHandler(handler);
            
            System.out.println("ExecuteHandler: " + handler);
            System.out.println("HandlerExecutor execute handler: " + handler);
            System.out.println("ThreadManager: ");
            listenerDTO.threadManager().holder().getThreads().forEach((thread) -> {
                System.out.println("\t ThreadName: " + listenerDTO.threadManager().holder().getId(thread)
                + "; Thread: " + thread);
            });
            
        } catch (IOException | ConnectionIdentifierException ex) {
            System.out.println("CATCH! clientTest: " + ex.getMessage());
        }
    }
    
    @Test
    public void executeHandler() {
        testBegin("HandlerExecutor", "executeHandler()");
        
        try(ServerSocket serverSocket = new ServerSocket(49000);) {
            close = false;
            
            LightSearchServerDTO serverDTO = initDTO();
            LightSearchListenerDTO listenerDTO = initListenerDTO(serverDTO);
            assertNotNull(listenerDTO, "ListenerDTO is null!");
            
            adminTest(serverSocket, listenerDTO);
            clientTest(serverSocket, listenerDTO);
            
            close = true;
            serverSocket.close();
            
            listenerDTO.threadManager().interruptAll(TimersIDEnum.REBOOT_TIMER_ID.stringValue());
            System.out.println("ThreadManager: ");
            listenerDTO.threadManager().holder().getThreads().forEach((thread) -> {
                System.out.println("\t ThreadName: " + listenerDTO.threadManager().holder().getId(thread)
                + "; Thread: " + thread);
            });
        }
        catch(IOException ex) { 
            System.out.println("CATCH! Message: " + ex.getMessage()); 
        }
        
        testEnd("HandlerExecutor", "executeHandler()");
    }
    
}
