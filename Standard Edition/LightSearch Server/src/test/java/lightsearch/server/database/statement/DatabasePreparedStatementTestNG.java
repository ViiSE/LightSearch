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
package lightsearch.server.database.statement;

import lightsearch.server.cmd.client.ClientCommand;
import lightsearch.server.cmd.client.ClientCommandConverter;
import lightsearch.server.cmd.client.ClientCommandConverterInit;
import lightsearch.server.data.LightSearchServerDTO;
import lightsearch.server.database.DatabaseConnection;
import lightsearch.server.database.cmd.message.DatabaseCommandMessage;
import lightsearch.server.database.cmd.message.DatabaseCommandMessageInit;
import lightsearch.server.exception.CommandConverterException;
import lightsearch.server.exception.DatabasePreparedStatementException;
import lightsearch.server.identifier.DatabaseRecordIdentifier;
import lightsearch.server.time.CurrentDateTimeInit;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import test.data.DataProviderCreator;

import java.sql.PreparedStatement;

import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;
import static test.message.TestMessage.*;

/**
 *
 * @author ViiSE
 */
public class DatabasePreparedStatementTestNG {
    
    private ClientCommand initClientCommand(String searchMessage) {
        try {
            ClientCommandConverter clientCmdConverter = ClientCommandConverterInit.clientCommandConverter();
            ClientCommand clientCmd = clientCmdConverter.convertToClientCommand(searchMessage);
            assertNotNull(clientCmd, "Client Command is null!");
            assertNotNull(clientCmd.command(), "Client command is null!");
            assertNotNull(clientCmd.IMEI(), "Client IMEI is null!");
            assertNotNull(clientCmd.barcode(), "Client barcode is null!");
            assertNotNull(clientCmd.sklad(), "Client sklad is null!");
            assertNotNull(clientCmd.TK(), "Client TK is null!");
            assertNotEquals(clientCmd.IMEI(), "", "Client IMEI is null!");
            assertNotEquals(clientCmd.sklad(), "", "Client sklad is null!");
            assertNotEquals(clientCmd.TK(), "", "Client TK is null!");
            
            return clientCmd;
        } catch (CommandConverterException ex) {
            catchMessage(ex);
            return null;
        }
    }
    
    private String initCommand(String message) {
        ClientCommand clientCommand = initClientCommand(message);
        DatabaseCommandMessage dbCmdMessage = DatabaseCommandMessageInit.databaseCommandMessageSearch(
                clientCommand.command(), clientCommand.IMEI(), clientCommand.barcode(),
                clientCommand.sklad(), clientCommand.TK());
        
        String command = dbCmdMessage.message();
        assertNotNull(command, "Command message is null!");
        
        return command;
    }
    
    private void databasePreparedStatementInsert(
            DatabaseConnection connection, String tableNameREQUEST, String command, String dateTime, long lsCode, boolean state) {
        try {
            System.out.println("DatabasePreparedStatementInsert: ");
            DatabasePreparedStatement dbPrepSt = DatabasePreparedStatementInit.databasePreparedStatementInsert(
                    connection, tableNameREQUEST, command, dateTime, lsCode, state);
            
            System.out.println("DatabasePreparedStatementInsert: ");
            PreparedStatement insertPrepSt = dbPrepSt.preparedStatement();
            System.out.println("DatabasePreparedStatementInsert.preparedStatement(): " + insertPrepSt);
        
        } catch (DatabasePreparedStatementException ex) {
            System.out.println("CATCH! " + ex.getMessage());
        }
    }
    
    private void databasePreparedStatementSelect(DatabaseConnection connection, 
            String tableNameRESPONSE, long lsCode, boolean state) {
        try {
            System.out.println("DatabasePreparedStatementSelect: ");
            DatabasePreparedStatement dbPrepSt = DatabasePreparedStatementInit.databasePreparedStatementSelect(
                    connection, tableNameRESPONSE, lsCode, state);
            
            System.out.println("DatabasePreparedStatementSelect: ");
            PreparedStatement selectPrepSt = dbPrepSt.preparedStatement();
            System.out.println("DatabasePreparedStatementSelect.preparedStatement(): " + selectPrepSt);
        
        } catch (DatabasePreparedStatementException ex) {
            System.out.println("CATCH! " + ex.getMessage());
        }
    }
    
    private void databasePreparedStatementUpdate(DatabaseConnection connection, 
            String tableNameRESPONSE, long lsCode, boolean state) {
        try {
            System.out.println("DatabasePreparedStatementUpdate: ");
            DatabasePreparedStatement dbPrepSt = DatabasePreparedStatementInit.databasePreparedStatementUpdate(
                    connection, tableNameRESPONSE, lsCode, state);
            
            System.out.println("DatabasePreparedStatementUpdate: ");
            PreparedStatement updatePrepSt = dbPrepSt.preparedStatement();
            System.out.println("DatabasePreparedStatementUpdate.preparedStatement(): " + updatePrepSt);
        
        } catch (DatabasePreparedStatementException ex) {
            System.out.println("CATCH! " + ex.getMessage());
        }
    }
    
    @Test
    @Parameters({"searchMessage", "usernameDb", "passwordDb", "lsRequestTable", "lsResponseTable"})
    public void preparedStatement(
            String message, String username, String password, String lsRequestTable, String lsResponseTable) {
        testBegin("DatabasePreparedStatement", "preparedStatement()");
        
        DatabaseConnection connection = DataProviderCreator.createDataProvider(DatabaseConnection.class, username, password);
        assertNotNull(connection, "Database connection is null!");

        assertNotNull(lsRequestTable, "Table Name REQUEST is null!");
        assertNotEquals(lsRequestTable, "", "Table Name REQUEST is null!");

        assertNotNull(lsResponseTable, "Table Name RESPONSE is null!");
        assertNotEquals(lsResponseTable, "", "Table Name RESPONSE is null!");

        String command = initCommand(message);
        String dateTime = CurrentDateTimeInit.currentDateTime().dateTimeInStandardFormat();
        boolean stateREQUEST = true;
        boolean stateRESPONSE_select = false;
        boolean stateRESPONSE_update = true;

        LightSearchServerDTO serverDTO = DataProviderCreator.createDataProvider(LightSearchServerDTO.class);
        long lsCode = DataProviderCreator.createDataProvider(DatabaseRecordIdentifier.class, serverDTO).next();

        databasePreparedStatementInsert(connection, lsRequestTable, command, dateTime, lsCode, stateREQUEST);
        databasePreparedStatementSelect(connection, lsResponseTable, lsCode, stateRESPONSE_select);
        databasePreparedStatementUpdate(connection, lsResponseTable, lsCode, stateRESPONSE_update);
        
        testEnd("DatabasePreparedStatement", "preparedStatement()");
    }
}
