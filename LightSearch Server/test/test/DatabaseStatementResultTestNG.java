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
import lightsearch.server.cmd.client.ClientCommand;
import lightsearch.server.cmd.client.ClientCommandConverter;
import lightsearch.server.cmd.client.ClientCommandConverterInit;
import lightsearch.server.exception.CommandConverterException;
import lightsearch.server.data.LightSearchServerDTOInit;
import lightsearch.server.data.LightSearchServerDatabaseDTO;
import lightsearch.server.data.LightSearchServerDatabaseDTOInit;
import lightsearch.server.data.LightSearchServerSettingsDAOInit;
import lightsearch.server.database.DatabaseConnection;
import lightsearch.server.database.DatabaseConnectionCreator;
import lightsearch.server.database.DatabaseConnectionCreatorInit;
import lightsearch.server.database.cmd.message.DatabaseCommandMessage;
import lightsearch.server.database.cmd.message.DatabaseCommandMessageInit;
import lightsearch.server.exception.DatabaseConnectionCreatorException;
import lightsearch.server.database.statement.DatabaseStatementExecutor;
import lightsearch.server.database.statement.DatabaseStatementExecutorInit;
import lightsearch.server.exception.DatabaseStatementExecutorException;
import lightsearch.server.database.statement.result.DatabaseStatementResult;
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
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class DatabaseStatementResultTestNG {
    
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
    
    private DatabaseConnection initConnection() {
        try {
            LightSearchServerDatabaseDTO databaseDTO = initDatabaseDTO();
            String username = "user";
            String password = "password";
            
            DatabaseConnectionCreator connectionCreator = 
                    DatabaseConnectionCreatorInit.databaseConnectionCreator(databaseDTO,
                            username, password);
            assertNotNull(connectionCreator, "DatabaseConnectionCreator is null!");
            DatabaseConnection connection = connectionCreator.createFirebirdConnection();
            
            return connection;
        } catch (DatabaseConnectionCreatorException ex) {
            System.out.println("CATCH! " + ex.getMessage());
            return null;
        }
    }
    
    private LightSearchServerDTO initDTO() {
        CurrentServerDirectory currentDirectory;
        currentDirectory = CurrentServerDirectoryInit.currentDirectory(
                OsDetectorInit.osDetector());
        Map<String,String> admins = 
                AdministratorsMapInit.administratorsMap(currentDirectory).administratorsMap();
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
        
        LightSearchServerDatabaseDTO databaseDTO = 
                LightSearchServerDatabaseDTOInit.lightSearchServerDatabaseDTO(
                        dbIP,
                        dbName,
                        dbPort
                );
        
        LightSearchServerSettingsDAO settingsDTO = 
                LightSearchServerSettingsDAOInit.settingsDAO(rebootServer, timeoutClient);
        
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
    
    private IteratorDatabaseRecord initIterator() {
        LightSearchServerDTO serverDTO = initDTO();
        IteratorDatabaseRecordReader iteratorReader = 
                IteratorDatabaseRecordReaderInit.iteratorDatabaseRecordReader(serverDTO);
        IteratorDatabaseRecord iterator = 
                IteratorDatabaseRecordInit.iteratorDatabaseRecord(iteratorReader.read());
        
        return iterator;
    }
    
    private String initDateTime() {
        CurrentDateTime currentDateTime = CurrentDateTimeInit.currentDateTime();
        String dateTimeInStandartFormat = currentDateTime.dateTimeInStandartFormat();
        return dateTimeInStandartFormat;
    }
    
    private ClientCommand initClientCommand() {
        try {
            String message = "{"
                            + "\"command\":\"search\","
                            + "\"IMEI\":\"123456789123456\","
                            + "\"barcode\":\"5421354\","
                            + "\"sklad\":\"null\","
                            + "\"TK\":\"null\""
                        + "}";
            ClientCommandConverter clientCmdConverter = ClientCommandConverterInit.clientCommandConverter();
            ClientCommand clientCmd;
            clientCmd = clientCmdConverter.convertToClientCommand(message);
            assertNotNull(clientCmd, "Client Command is null!");
            assertNotNull(clientCmd.command(), "Client command is null!");
            assertNotNull(clientCmd.IMEI(), "Client IMEI is null!");
            assertNotNull(clientCmd.barcode(), "Client barcode is null!");
            assertNotNull(clientCmd.sklad(), "Client sklad is null!");
            assertNotNull(clientCmd.TK(), "Client TK is null!");
            assertFalse(clientCmd.IMEI().equals(""), "Client IMEI is null!");
            assertFalse(clientCmd.sklad().equals(""), "Client sklad is null!");
            assertFalse(clientCmd.TK().equals(""), "Client TK is null!");
            
            return clientCmd;
        } catch (CommandConverterException ex) {
            System.out.println("CATCH! " + ex.getMessage());
            return null;
        }
    }
    
    private DatabaseCommandMessage initDatabaseCommandMessage() {
        ClientCommand clientCommand = initClientCommand();
        DatabaseCommandMessage dbCmdMessage = DatabaseCommandMessageInit.databaseCommandMessageConnection(
                clientCommand.command(), clientCommand.IMEI());
        assertNotNull(dbCmdMessage, "DatabaseCommandMessage is null!");
        
        return dbCmdMessage;
    }
    
    @Test
    public void result() {
        testBegin("DatabaseStatementResult", "result()");
        
        try {
            DatabaseConnection connection = initConnection();
            assertNotNull(connection, "Database connection is null!");
            
            IteratorDatabaseRecord iterator = initIterator();
            assertNotNull(iterator, "IteratorDatabaseRecord is null!");
            
            String dateTime = initDateTime();
            assertNotNull(dateTime, "DateTime is null!");
            assertFalse(dateTime.equals(""), "DateTime is null!");
            
            DatabaseCommandMessage dbCmdMessage = initDatabaseCommandMessage();
            
            DatabaseStatementExecutor dbStExec = DatabaseStatementExecutorInit.databaseStatementExecutor(
                    connection, iterator.next(), dateTime, dbCmdMessage);
            assertNotNull(dbStExec, "DatabaseStatementExecutor is null!");
            DatabaseStatementResult dbStRes = dbStExec.exec();
            
            System.out.println("DatabaseStatementResult.result: " + dbStRes.result());
        } catch (DatabaseStatementExecutorException ex) {
            System.out.println("CATCH! " + ex.getMessage());
        }
        
        testEnd("DatabaseStatementResult", "result()");
    }
}
