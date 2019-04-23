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

import lightsearch.server.cmd.client.ClientCommand;
import lightsearch.server.cmd.client.ClientCommandConverter;
import lightsearch.server.cmd.client.ClientCommandConverterInit;
import lightsearch.server.exception.CommandConverterException;
import lightsearch.server.data.LightSearchServerDatabaseDTO;
import lightsearch.server.data.LightSearchServerDatabaseDTOInit;
import lightsearch.server.database.DatabaseConnection;
import lightsearch.server.database.DatabaseConnectionCreator;
import lightsearch.server.database.DatabaseConnectionCreatorInit;
import lightsearch.server.database.cmd.message.DatabaseCommandMessage;
import lightsearch.server.database.cmd.message.DatabaseCommandMessageInit;
import lightsearch.server.exception.DatabaseConnectionCreatorException;
import lightsearch.server.database.statement.DatabasePreparedStatement;
import lightsearch.server.database.statement.DatabasePreparedStatementInit;
import lightsearch.server.database.statement.result.DatabaseStatementResultSetUpdate;
import lightsearch.server.database.statement.result.DatabaseStatementResultSetUpdateInit;
import lightsearch.server.exception.DatabaseStatementResultSetException;
import lightsearch.server.iterator.IteratorDatabaseRecord;
import lightsearch.server.iterator.IteratorDatabaseRecordInit;
import lightsearch.server.time.CurrentDateTime;
import lightsearch.server.time.CurrentDateTimeInit;
import static org.testng.Assert.*;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class DatabaseStatementResultSetUpdateTestNG {
    
    private ClientCommand initClientCommandConnection() {
        try {
            String message = "{"
                            + "\"command\":\"connect\","
                            + "\"IMEI\":\"123456789123456\""
//                            + "\"barcode\":\"5421354\""
//                            + "\"sklad\":\"null\""
//                            + "\"TK\":\"null\""
                        + "}";
            ClientCommandConverter clientCmdConverter = ClientCommandConverterInit.clientCommandConverter();
            ClientCommand clientCmd;
            clientCmd = clientCmdConverter.convertToClientCommand(message);
            assertNotNull(clientCmd, "Client Command is null!");
            assertNotNull(clientCmd.command(), "Client command is null!");
            assertNotNull(clientCmd.IMEI(), "Client IMEI is null!");
//            assertNotNull(clientCmd.barcode(), "Client barcode is null!");
//            assertNotNull(clientCmd.sklad(), "Client sklad is null!");
//            assertNotNull(clientCmd.TK(), "Client TK is null!");
//            assertFalse(clientCmd.IMEI().equals(""), "Client IMEI is null!");
//            assertFalse(clientCmd.sklad().equals(""), "Client sklad is null!");
//            assertFalse(clientCmd.TK().equals(""), "Client TK is null!");
            
            return clientCmd;
        } catch (CommandConverterException ex) {
            System.out.println("CATCH! " + ex.getMessage());
            return null;
        }
    }
    
    private String initCommandConnection() {
        ClientCommand clientCommand = initClientCommandConnection();
        DatabaseCommandMessage dbCmdMessage = DatabaseCommandMessageInit.databaseCommandMessageConnection(
                clientCommand.command(), clientCommand.IMEI());
        
        String command = dbCmdMessage.message();
        assertNotNull(command, "Command message is null!");
        
        return command;
    }
    
    @Test
    public void exec() {
        testBegin("DatabaseStatementResultSetUpdate", "exec()");
        
        IteratorDatabaseRecord iteratorDatabaseRecord = IteratorDatabaseRecordInit.iteratorDatabaseRecord(2);
        
        String dbIP = "127.0.0.1";
        String dbName = "/usr/db/db.fdb";
        int dbPort = 5432;
        String username = "admin";
        String password = "pass";
        LightSearchServerDatabaseDTO databaseDTO = LightSearchServerDatabaseDTOInit.lightSearchServerDatabaseDTO(dbIP, dbName, dbPort);
        DatabaseConnectionCreator dbConnCreator = DatabaseConnectionCreatorInit.databaseConnectionCreator(databaseDTO, username, password);
        try {
            DatabaseConnection databaseConnection = dbConnCreator.createFirebirdConnection();
            CurrentDateTime currDateTime = CurrentDateTimeInit.currentDateTime();
            
            String tableName = "LS_REQUEST";
            assertNotNull(tableName, "Table name is null!");
            
            String IMEI = "234812369785437";
            assertNotNull(IMEI, "IMEI is null!");
            
            boolean state = true;
            String command = initCommandConnection();
            String dateTime = currDateTime.dateTimeInStandartFormat();

            DatabasePreparedStatement dbPS = DatabasePreparedStatementInit.databasePreparedStatementInsert(databaseConnection, 
                    tableName, command, dateTime, iteratorDatabaseRecord.next(), state);
            DatabaseStatementResultSetUpdate dbRSUpdate = DatabaseStatementResultSetUpdateInit.databaseStatementResultSetUpdate(dbPS);
            dbRSUpdate.exec();
        } catch(DatabaseConnectionCreatorException |
                DatabaseStatementResultSetException ex) {
            System.out.println("CATCH! Message: " + ex.getMessage());
        }
        
        testEnd("DatabaseStatementResultSetUpdate", "exec()");
    }
}
