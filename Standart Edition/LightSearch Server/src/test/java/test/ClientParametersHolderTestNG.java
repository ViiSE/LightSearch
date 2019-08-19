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

import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import lightsearch.server.checker.LightSearchChecker;
import lightsearch.server.checker.LightSearchCheckerInit;
import lightsearch.server.cmd.client.ClientCommandCreator;
import lightsearch.server.cmd.client.ClientCommandCreatorInit;
import lightsearch.server.data.ClientDAO;
import lightsearch.server.data.ClientDAOInit;
import lightsearch.server.data.ClientParametersHolder;
import lightsearch.server.data.ClientParametersHolderInit;
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
public class ClientParametersHolderTestNG {
    
    private IteratorDatabaseRecord initIterator(LightSearchServerDTO serverDTO) {
        IteratorDatabaseRecordReader iteratorReader = IteratorDatabaseRecordReaderInit.iteratorDatabaseRecordReader(serverDTO);
        IteratorDatabaseRecord iterator = IteratorDatabaseRecordInit.iteratorDatabaseRecord(iteratorReader.read());
        return iterator;
    }
    
    private ThreadManager initThreadManager() {
        HashMap<String,LightSearchThread> threads = new HashMap<>();
        ThreadHolder holder = ThreadHolderInit.threadHolder(threads);
        ThreadManager threadManager = ThreadManagerInit.threadManager(holder);
        return threadManager;
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
    
    private ClientParametersHolder init() {
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
        
        
        ClientDAO clientDAO = ClientDAOInit.clientDAO();
        
        LightSearchListenerDTO listenerDTO = initListenerDTO(serverDTO);
        
        ClientCommandCreator clientCommandCreator = ClientCommandCreatorInit.clientCommandCreator(serverDTO, 
                listenerDTO, clientDAO);
        
        Socket clientSocket = new Socket();
        DataStreamCreator dataStreamCreator = DataStreamCreatorInit.dataStreamCreator(clientSocket);
        try {
            dataStreamCreator.createDataStream();
            DataStream dataStream = DataStreamInit.dataStream(dataStreamCreator);
            ClientParametersHolder clientParamHolder = ClientParametersHolderInit.clientParametersHolder(
                    clientSocket, dataStream, clientCommandCreator.createCommandHolder());

            return clientParamHolder;
        } catch(DataStreamCreatorException ex) {
            System.out.println("CATCH! Message: " + ex.getMessage());
            return null;
        }
    }
    
    @Test
    public void clientSocket() {
        testBegin("ClientParametersHolder", "clientSocket()");
        
        ClientParametersHolder clientParamHolder = init();
        assertNotNull(clientParamHolder.clientSocket(), "Client socket is null!");
        System.out.println(clientParamHolder.clientSocket());
        
        testEnd("ClientParametersHolder", "clientSocket()");
    }
    
    @Test
    public void dataStream() {
        testBegin("ClientParametersHolder", "dataStream()");
        
        ClientParametersHolder clientParamHolder = init();
        assertNotNull(clientParamHolder.dataStream(), "Data stream is null!");
        System.out.println(clientParamHolder.dataStream());
        
        testEnd("ClientParametersHolder", "dataStream()");
    }
    
    @Test
    public void commandHolder() {
        testBegin("ClientParametersHolder", "commandHolder()");
        
        ClientParametersHolder client = init();
        assertNotNull(client.commandHolder(), "Command holder is null!");
        System.out.println(client.commandHolder());
        
        testEnd("ClientParametersHolder", "commandHolder()");
    }
}
