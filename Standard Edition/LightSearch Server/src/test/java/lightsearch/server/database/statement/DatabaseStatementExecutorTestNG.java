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
import lightsearch.server.database.statement.result.DatabaseStatementResult;
import lightsearch.server.exception.CommandConverterException;
import lightsearch.server.exception.DatabaseStatementExecutorException;
import lightsearch.server.identifier.DatabaseRecordIdentifier;
import lightsearch.server.time.CurrentDateTimeInit;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import test.data.DataProviderCreator;

import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;
import static test.message.TestMessage.*;

/**
 *
 * @author ViiSE
 */
public class DatabaseStatementExecutorTestNG {
    
    private ClientCommand initClientCommand(String searchMessage) {
        try {
            ClientCommandConverter clientCmdConverter = ClientCommandConverterInit.clientCommandConverter();
            ClientCommand clientCmd;
            clientCmd = clientCmdConverter.convertToClientCommand(searchMessage);
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
    
    private DatabaseCommandMessage initDatabaseCommandMessage(String message) {
       ClientCommand clientCmd = initClientCommand(message);
       DatabaseCommandMessage dbCmdMessage = DatabaseCommandMessageInit.databaseCommandMessageSearch(
               clientCmd.command(), clientCmd.IMEI(), clientCmd.barcode(), clientCmd.sklad(), clientCmd.TK());
       assertNotNull(dbCmdMessage, "DatabaseCommandMessage is null!");
       
       return dbCmdMessage;
    }
    
    @Test
    @Parameters({"searchMessage", "usernameDb", "passwordDb"})
    public void exec(String message, String username, String password) {
        testBegin("DatabaseStatementExecutor", "exec()");
        
        try {
            DatabaseConnection connection =
                    DataProviderCreator.createDataProvider(DatabaseConnection.class, username, password);
            assertNotNull(connection, "Database connection is null!");

            LightSearchServerDTO serverDTO = DataProviderCreator.createDataProvider(LightSearchServerDTO.class);
            DatabaseRecordIdentifier identifier =
                    DataProviderCreator.createDataProvider(DatabaseRecordIdentifier.class, serverDTO);
            assertNotNull(identifier, "DatabaseRecordIdentifier is null!");
            
            String dateTime = CurrentDateTimeInit.currentDateTime().dateTimeInStandardFormat();
            assertNotNull(dateTime, "DateTime is null!");
            assertNotEquals(dateTime, "", "DateTime is null!");
            
            DatabaseCommandMessage dbCmdMessage = initDatabaseCommandMessage(message);
            
            DatabaseStatementExecutor dbStExec = DatabaseStatementExecutorInit.databaseStatementExecutor(connection,
                    identifier.next(), dateTime, dbCmdMessage);
            assertNotNull(dbStExec, "DatabaseStatementExecutor is null!");
            DatabaseStatementResult dbStRes = dbStExec.exec();
            
            System.out.println("DatabaseStatementResult.result: " + dbStRes.result());
        } catch (DatabaseStatementExecutorException ex) {
            catchMessage(ex);
        }
        
        testEnd("DatabaseStatementExecutor", "exec()");
    }
}
