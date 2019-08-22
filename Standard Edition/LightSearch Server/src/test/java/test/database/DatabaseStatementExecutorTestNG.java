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
package test.database;

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
import lightsearch.server.identifier.DatabaseRecordIdentifier;
import lightsearch.server.identifier.DatabaseRecordIdentifierInit;
import lightsearch.server.identifier.DatabaseRecordIdentifierReader;
import lightsearch.server.identifier.DatabaseRecordIdentifierReaderInit;
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
public class DatabaseStatementExecutorTestNG {
    
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
            
            DatabaseConnectionCreator connectionCreator = DatabaseConnectionCreatorInit.databaseConnectionCreator(databaseDTO,
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
    
    private DatabaseRecordIdentifier initIdentifier() {
        LightSearchServerDTO serverDTO = initDTO();
        DatabaseRecordIdentifierReader identifierReader = DatabaseRecordIdentifierReaderInit.databaseRecordIdentifierReader(serverDTO);
        DatabaseRecordIdentifier identifier = DatabaseRecordIdentifierInit.databaseRecordIdentifier(identifierReader.read());
        
        return identifier;
    }
    
    private String initDateTime() {
        CurrentDateTime currentDateTime = CurrentDateTimeInit.currentDateTime();
        return currentDateTime.dateTimeInStandardFormat();
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
       ClientCommand clientCmd = initClientCommand();
       DatabaseCommandMessage dbCmdMessage = DatabaseCommandMessageInit.databaseCommandMessageSearch(
               clientCmd.command(), clientCmd.IMEI(), clientCmd.barcode(), clientCmd.sklad(), clientCmd.TK());
       assertNotNull(dbCmdMessage, "DatabaseCommandMessage is null!");
       
       return dbCmdMessage;
    }
    
    @Test
    public void exec() {
        testBegin("DatabaseStatementExecutor", "exec()");
        
        try {
            DatabaseConnection connection = initConnection();
            assertNotNull(connection, "Database connection is null!");
            
            DatabaseRecordIdentifier identifier = initIdentifier();
            assertNotNull(identifier, "DatabaseRecordIdentifier is null!");
            
            String dateTime = initDateTime();
            assertNotNull(dateTime, "DateTime is null!");
            assertFalse(dateTime.equals(""), "DateTime is null!");
            
            DatabaseCommandMessage dbCmdMessage = initDatabaseCommandMessage();
            
            DatabaseStatementExecutor dbStExec = DatabaseStatementExecutorInit.databaseStatementExecutor(connection,
                    identifier.next(), dateTime, dbCmdMessage);
            assertNotNull(dbStExec, "DatabaseStatementExecutor is null!");
            DatabaseStatementResult dbStRes = dbStExec.exec();
            
            System.out.println("DatabaseStatementResult.result: " + dbStRes.result());
        } catch (DatabaseStatementExecutorException ex) {
            System.out.println("CATCH! " + ex.getMessage());
        }
        
        testEnd("DatabaseStatementExecutor", "exec()");
    }
}
