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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lightsearch.server.data.LightSearchServerDTOInit;
import lightsearch.server.data.LightSearchServerDatabaseDTO;
import lightsearch.server.data.LightSearchServerDatabaseDTOInit;
import lightsearch.server.data.LightSearchServerSettingsDAOInit;
import lightsearch.server.database.DatabaseConnection;
import lightsearch.server.database.DatabaseConnectionCreator;
import lightsearch.server.database.DatabaseConnectionCreatorInit;
import lightsearch.server.database.DatabaseWriter;
import lightsearch.server.database.DatabaseWriterInit;
import lightsearch.server.exception.DatabaseConnectionCreatorException;
import lightsearch.server.exception.DatabaseWriterException;
import lightsearch.server.initialization.AdministratorsMapInit;
import lightsearch.server.initialization.ClientBlacklistInit;
import lightsearch.server.initialization.CurrentServerDirectory;
import lightsearch.server.initialization.CurrentServerDirectoryInit;
import lightsearch.server.initialization.DatabaseSettings;
import lightsearch.server.initialization.DatabaseSettingsInit;
import lightsearch.server.initialization.OsDetector;
import lightsearch.server.initialization.OsDetectorInit;
import lightsearch.server.initialization.ServerPort;
import lightsearch.server.initialization.ServerPortInit;
import lightsearch.server.initialization.ServerSettings;
import lightsearch.server.initialization.ServerSettingsInit;
import lightsearch.server.iterator.IteratorDatabaseRecord;
import lightsearch.server.iterator.IteratorDatabaseRecordInit;
import lightsearch.server.iterator.IteratorDatabaseRecordReader;
import lightsearch.server.iterator.IteratorDatabaseRecordReaderInit;
import lightsearch.server.time.CurrentDateTime;
import lightsearch.server.time.CurrentDateTimeInit;
import static org.testng.Assert.*;
import org.testng.annotations.Test;
import lightsearch.server.data.LightSearchServerDTO;
import lightsearch.server.data.LightSearchServerSettingsDAO;
import lightsearch.server.database.cmd.message.DatabaseCommandMessage;
import lightsearch.server.database.cmd.message.DatabaseCommandMessageInit;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class DatabaseWriterTestNG {
    
    private LightSearchServerDatabaseDTO initDatabaseDTO() {
        OsDetector osDetector = OsDetectorInit.osDetector();
        CurrentServerDirectory currentDirectory = CurrentServerDirectoryInit.currentDirectory(osDetector);
        DatabaseSettings dbSettings = DatabaseSettingsInit.databaseSettings(currentDirectory);
        String dbName = dbSettings.name();
        String dbIP = dbSettings.ip();
        int dbPort = dbSettings.port();
        LightSearchServerDatabaseDTO databaseDTO = LightSearchServerDatabaseDTOInit.lightSearchServerDatabaseDTO(dbIP, dbName, dbPort);
        
        return databaseDTO;
    }
    
    private DatabaseConnection initDatabaseConnection() {
        try {
            LightSearchServerDatabaseDTO databaseDTO = initDatabaseDTO();
            String username = "user";
            String password = "pass";
            
            DatabaseConnectionCreator connectionCreator = DatabaseConnectionCreatorInit.databaseConnectionCreator(databaseDTO,
                    username, password);
            DatabaseConnection connection = connectionCreator.createFirebirdConnection();
            return connection;
        } catch (DatabaseConnectionCreatorException ex) {
            System.out.println("CATCH " + ex.getMessage());
            return null;
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
    
    private IteratorDatabaseRecord initIterator(LightSearchServerDTO serverDTO) {
        IteratorDatabaseRecordReader iteratorReader = IteratorDatabaseRecordReaderInit.iteratorDatabaseRecordReader(serverDTO);
        IteratorDatabaseRecord iterator = IteratorDatabaseRecordInit.iteratorDatabaseRecord(iteratorReader.read());
        return iterator;
    }
    
    public String initMessageConnect() {
        String command        = "connect";
        String IMEI           = "123456789123456";
        String username       = "test";
        String userIdentifier = "007";
        DatabaseCommandMessage dbCmdMsg = DatabaseCommandMessageInit.databaseCommandMessageConnection(command, IMEI, username, userIdentifier);
        
        return dbCmdMsg.message();
    }
    
    public String initMessageSearch() {
        String command = "search";
        String IMEI    = "123456789123456";
        String barcode = "738592";
        String sklad   = "Склад 1";
        String TK      = "null";
        DatabaseCommandMessage dbCmdMsg = DatabaseCommandMessageInit.databaseCommandMessageSearch(command, IMEI, barcode, sklad, TK);
        
        return dbCmdMsg.message();
    }
    
    public String initMessageOpenSoftCheck() {
        String command  = "open_soft_check";
        String IMEI     = "123456789123456";
        String ident    = "007";
        String cardCode = "777";
        DatabaseCommandMessage dbCmdMsg = DatabaseCommandMessageInit.databaseCommandMessageOpenSoftCheck(command, IMEI, ident, cardCode);
        
        return dbCmdMsg.message();
    }
    
    public String initMessageCancelSoftCheck() {
        String command  = "cancel_soft_check";
        String IMEI     = "123456789123456";
        String ident    = "007";
        String cardCode = "777";
        DatabaseCommandMessage dbCmdMsg = DatabaseCommandMessageInit.databaseCommandMessageCancelSoftCheck(command, IMEI, ident, cardCode);
        
        return dbCmdMsg.message();
    }
    
    public String initMessageConfirmSoftCheckProducts() {
        String command  = "confirm_prod_sf";
        String IMEI     = "123456789123456";
        String ident    = "007";
        String cardCode = "777";
        String data     = "[{\"ID\":\"111111\",\"amount\":\"10\"},{\"ID\":\"222222\",\"amount\":\"20\"}]";
        DatabaseCommandMessage dbCmdMsg = DatabaseCommandMessageInit.databaseCommandMessageConfirmSoftCheckProducts(command, IMEI, ident, cardCode, data);
        
        return dbCmdMsg.message();
    }
    
    public String initMessageCloseSoftCheck() {
        String command  = "close_soft_check";
        String IMEI     = "123456789123456";
        String ident    = "007";
        String cardCode = "777";
        String delivery = "1";
        DatabaseCommandMessage dbCmdMsg = DatabaseCommandMessageInit.databaseCommandMessageCloseSoftCheck(command, IMEI, ident, cardCode, delivery);
        
        return dbCmdMsg.message();
    }
    
    @Test
    public void write() {
        testBegin("DatabaseWriter", "write()");
        
        try {
            DatabaseConnection databaseConnection = initDatabaseConnection();
            assertNotNull(databaseConnection, "Database connection is null!");
            
            LightSearchServerDTO serverDTO = initDTO();
            assertNotNull(serverDTO, "ServerDTO is null!");
            
            IteratorDatabaseRecord iterator = initIterator(serverDTO);
            assertNotNull(iterator, "Iterator is null!");
            
            CurrentDateTime currentDateTime = CurrentDateTimeInit.currentDateTime();
            String dateTime = currentDateTime.dateTimeInStandartFormat();
            
            String message = initMessageConnect();
//            String message = initMessageSearch();
//            String message = initMessageOpenSoftCheck();
//            String message = initMessageCancelSoftCheck();
//            String message = initMessageCloseSoftCheck();
//            String message = initMessageConfirmSoftCheckProducts();
            DatabaseWriter writer = DatabaseWriterInit.databaseWriter(databaseConnection, iterator.next(), dateTime, message);
            
            writer.write();
            
        } catch (DatabaseWriterException ex) {
            System.out.println("CATCH! " + ex.getMessage());
        }
        
        testEnd("DatabaseWriter", "write()");
    }
}
