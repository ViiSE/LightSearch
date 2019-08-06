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

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lightsearch.server.checker.LightSearchChecker;
import lightsearch.server.checker.LightSearchCheckerInit;
import lightsearch.server.cmd.client.ClientCommandCreator;
import lightsearch.server.cmd.client.ClientCommandCreatorInit;
import lightsearch.server.data.ClientDAO;
import lightsearch.server.data.ClientDAOInit;
import lightsearch.server.data.ClientHandlerDTO;
import lightsearch.server.data.ClientHandlerDTOInit;
import lightsearch.server.data.ClientParametersHolder;
import lightsearch.server.data.ClientParametersHolderInit;
import lightsearch.server.data.LightSearchListenerDTO;
import lightsearch.server.data.LightSearchListenerDTOInit;
import lightsearch.server.data.LightSearchServerDTOInit;
import lightsearch.server.data.LightSearchServerDatabaseDTO;
import lightsearch.server.data.LightSearchServerDatabaseDTOInit;
import lightsearch.server.data.LightSearchServerSettingsDAOInit;
import lightsearch.server.data.ThreadParametersHolder;
import lightsearch.server.data.ThreadParametersHolderInit;
import lightsearch.server.data.stream.DataStream;
import lightsearch.server.data.stream.DataStreamCreator;
import lightsearch.server.data.stream.DataStreamCreatorInit;
import lightsearch.server.data.stream.DataStreamInit;
import lightsearch.server.exception.DataStreamCreatorException;
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
import lightsearch.server.thread.LightSearchThread;
import lightsearch.server.thread.LightSearchThreadID;
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
public class ClientHandlerDTOTestNG {
    
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
    
    private LightSearchServerDTO initDTO() {
        Map<String, String> admins = new HashMap<>();
        Map<String, String> clients = new HashMap<>();
        List<String> blacklist = new ArrayList<>();
        
        String dbIP = "127.0.0.1";
        String dbName = "example_db";
        int dbPort = 8080;
        LightSearchServerDatabaseDTO databaseDTO = LightSearchServerDatabaseDTOInit.lightSearchServerDatabaseDTO(dbIP, dbName, dbPort);
        
        int serverReboot = 0;
        int clientTimeout = 0;
        LightSearchServerSettingsDAO settingsDTO = LightSearchServerSettingsDAOInit.settingsDAO(serverReboot, clientTimeout);
        
        OsDetector osDetector = OsDetectorInit.osDetector();
        CurrentServerDirectory currentDirectory = CurrentServerDirectoryInit.currentDirectory(osDetector);
        
        LightSearchServerDTO serverDTO = LightSearchServerDTOInit.LightSearchServerDTO(admins, clients, 
                blacklist, databaseDTO, 0, settingsDTO, currentDirectory.currentDirectory());
        
        return serverDTO;
    }
    
    private ClientParametersHolder initClientParamHolder(LightSearchServerDTO serverDTO,
            ClientDAO clientDAO) {
        LightSearchListenerDTO listenerDTO = initListenerDTO(serverDTO);
        
        ClientCommandCreator clientCommandCreator = ClientCommandCreatorInit.clientCommandCreator(serverDTO,
                listenerDTO, clientDAO);
        
        Socket adminSocket = new Socket();
        DataStreamCreator dataStreamCreator = DataStreamCreatorInit.dataStreamCreator(adminSocket);
        try {
            dataStreamCreator.createDataStream();
        } catch(DataStreamCreatorException ex) {
            System.out.println("CATCH! Message: " + ex.getMessage());
        }
        
        DataStream dataStream = DataStreamInit.dataStream(dataStreamCreator);
        ClientParametersHolder clientParamHolder = ClientParametersHolderInit.clientParametersHolder(
                    adminSocket, dataStream, clientCommandCreator.createCommandHolder());
        
        return clientParamHolder;

    }
    
    private String initIdentifier() {
        return "android";
    }
    
    private String initId(LightSearchServerDTO serverDTO) {
        IteratorDatabaseRecord iterator = initIterator(serverDTO);
        assertNotNull(iterator, "Iterator is null!");
        
        String identifier = initIdentifier();
        assertNotNull(identifier, "Identifier is null!");
        
        String id = LightSearchThreadID.createID(identifier, iterator.next());
        
        return id;
    }
    
    private ClientHandlerDTO initClientHandlerDTO() {
        LightSearchServerDTO serverDTO = initDTO();
        assertNotNull(serverDTO, "ServerDTO is null!");
        
        ClientDAO clientDAO = ClientDAOInit.clientDAO();
        assertNotNull(clientDAO, "ClientDAO is null!");
        
        ClientParametersHolder clientParamHolder = initClientParamHolder(serverDTO, clientDAO);
        assertNotNull(clientParamHolder, "ClientParametersHolder is null!");
        
        String id = initId(serverDTO);
        assertNotNull(id, "Thread ID is null!");
        
        ThreadParametersHolder threadParametersHolder = ThreadParametersHolderInit.threadParametersHolder(id);
        assertNotNull(threadParametersHolder, "ThreadParametersHolder is null!");
        
        CurrentDateTime currentDateTime = CurrentDateTimeInit.currentDateTime();
        assertNotNull(currentDateTime, "CurrentDateTime is null!");
        
        ThreadManager threadManager = initThreadManager();
        assertNotNull(threadManager, "ThreadManager is null!");
        
        ClientHandlerDTO clientHandlerDTO = ClientHandlerDTOInit.clientHandlerDTO(
                clientParamHolder, threadParametersHolder, currentDateTime, threadManager, clientDAO);
        
        return clientHandlerDTO;
    }
    
    @Test
    public void clientDAO() {
        testBegin("ClientHandlerDTO", "clientDAO()");
        
        ClientHandlerDTO clientHandlerDTO = initClientHandlerDTO();
        System.out.println("clientHandlerDTO.clientDAO():  " + clientHandlerDTO.clientDAO());
        
        testEnd("ClientHandlerDTO", "clientDAO()");
    }
    
    @Test
    public void clientParametersHolder() {
        testBegin("ClientHandlerDTO", "clientParametersHolder()");
        
        ClientHandlerDTO clientHandlerDTO = initClientHandlerDTO();
        System.out.println("clientHandlerDTO.clientParametersHolder():  " + clientHandlerDTO.clientParametersHolder());
        
        testEnd("ClientHandlerDTO", "clientParametersHolder()");
    }
    
    @Test
    public void currentDateTime() {
        testBegin("ClientHandlerDTO", "currentDateTime()");
        
        ClientHandlerDTO clientHandlerDTO = initClientHandlerDTO();
        System.out.println("clientHandlerDTO.currentDateTime():  " + clientHandlerDTO.currentDateTime());
        
        testEnd("ClientHandlerDTO", "currentDateTime()");
    }
    
    @Test
    public void threadManager() {
        testBegin("ClientHandlerDTO", "threadManager()");
        
        ClientHandlerDTO clientHandlerDTO = initClientHandlerDTO();
        System.out.println("clientHandlerDTO.threadManager():  " + clientHandlerDTO.threadManager());
        
        testEnd("ClientHandlerDTO", "threadManager()");
    }
    
    @Test
    public void threadParametersHolder() {
        testBegin("ClientHandlerDTO", "threadParametersHolder()");
        
        ClientHandlerDTO clientHandlerDTO = initClientHandlerDTO();
        System.out.println("clientHandlerDTO.threadParametersHolder():  " + clientHandlerDTO.threadParametersHolder());
        
        testEnd("ClientHandlerDTO", "threadParametersHolder()");
    }
}
