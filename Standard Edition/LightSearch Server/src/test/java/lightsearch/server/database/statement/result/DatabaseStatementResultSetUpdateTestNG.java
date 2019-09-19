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
package lightsearch.server.database.statement.result;

import lightsearch.server.cmd.client.ClientCommand;
import lightsearch.server.cmd.client.ClientCommandConverter;
import lightsearch.server.cmd.client.ClientCommandConverterInit;
import lightsearch.server.data.LightSearchServerDTO;
import lightsearch.server.database.DatabaseConnection;
import lightsearch.server.database.cmd.message.DatabaseCommandMessage;
import lightsearch.server.database.cmd.message.DatabaseCommandMessageInit;
import lightsearch.server.database.statement.DatabasePreparedStatement;
import lightsearch.server.database.statement.DatabasePreparedStatementInit;
import lightsearch.server.exception.CommandConverterException;
import lightsearch.server.exception.DatabaseStatementResultSetException;
import lightsearch.server.identifier.DatabaseRecordIdentifier;
import lightsearch.server.time.CurrentDateTime;
import lightsearch.server.time.CurrentDateTimeInit;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import test.data.DataProviderCreator;

import static org.testng.Assert.assertNotNull;
import static test.message.TestMessage.*;

/**
 *
 * @author ViiSE
 */
public class DatabaseStatementResultSetUpdateTestNG {

    private ClientCommand initClientCommandConnection(String message) {
        try {
            ClientCommandConverter clientCmdConverter = ClientCommandConverterInit.clientCommandConverter();
            ClientCommand clientCmd = clientCmdConverter.convertToClientCommand(message);
            assertNotNull(clientCmd, "Client Command is null!");
            assertNotNull(clientCmd.command(), "Client command is null!");
            assertNotNull(clientCmd.IMEI(), "Client IMEI is null!");
            assertNotNull(clientCmd.username(), "Client username is null!");
            assertNotNull(clientCmd.userIdentifier(), "Client user identifier is null!");

            return clientCmd;
        } catch (CommandConverterException ex) {
            catchMessage(ex);
            return null;
        }
    }

    private String initCommandConnection(String message) {
        ClientCommand clientCommand = initClientCommandConnection(message);
        DatabaseCommandMessage dbCmdMessage =
                DatabaseCommandMessageInit.databaseCommandMessageConnection(
                        clientCommand.command(), clientCommand.IMEI(), clientCommand.username(),
                        clientCommand.userIdentifier());

        String command = dbCmdMessage.message();
        assertNotNull(command, "Command message is null!");

        return command;
    }

    @Test
    @Parameters({"usernameDb", "passwordDb", "connectMessage", "lsRequestTable", "IMEI", "state"})
    public void exec(String username, String password, String message, String tableName, String IMEI, boolean state) {
        testBegin("DatabaseStatementResultSetUpdate", "exec()");

        LightSearchServerDTO serverDTO = DataProviderCreator.createDataProvider(LightSearchServerDTO.class);
        DatabaseRecordIdentifier identifier =
                DataProviderCreator.createDataProvider(DatabaseRecordIdentifier.class, serverDTO);

        try {
            DatabaseConnection connection = DataProviderCreator.createDataProvider(DatabaseConnection.class, username, password);
            assertNotNull(connection, "DatabaseConnection is null!");
            CurrentDateTime currDateTime = CurrentDateTimeInit.currentDateTime();

            assertNotNull(tableName, "Table name is null!");
            assertNotNull(IMEI, "IMEI is null!");

            String command = initCommandConnection(message);
            String dateTime = currDateTime.dateTimeInStandardFormat();

            DatabasePreparedStatement dbPS = DatabasePreparedStatementInit.databasePreparedStatementInsert(
                    connection, tableName, command, dateTime, identifier.next(), state);
            DatabaseStatementResultSetUpdate dbRSUpdate =
                    DatabaseStatementResultSetUpdateInit.databaseStatementResultSetUpdate(dbPS);
            dbRSUpdate.exec();
        } catch (DatabaseStatementResultSetException ex) {
            catchMessage(ex);
        }

        testEnd("DatabaseStatementResultSetUpdate", "exec()");
    }
}
