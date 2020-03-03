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

import lightsearch.server.data.LightSearchServerDTO;
import lightsearch.server.database.DatabaseConnection;
import lightsearch.server.database.statement.DatabasePreparedStatement;
import lightsearch.server.database.statement.DatabasePreparedStatementInit;
import lightsearch.server.exception.DatabaseStatementResultSetException;
import lightsearch.server.identifier.DatabaseRecordIdentifier;
import lightsearch.server.time.CurrentDateTimePattern;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import test.data.DataProviderCreator;

import static org.testng.Assert.assertNotNull;
import static test.message.TestMessage.*;

/**
 *
 * @author ViiSE
 */
public class DatabaseStatementResultSetSelectTestNG {
    
    @Test
    @Parameters({"usernameDb", "passwordDb", "state", "lsResponseTable"})
    public void exec(String username, String password, boolean state, String tableName) {
        testBegin("DatabaseStatementResultSetSelect", "exec()");

        LightSearchServerDTO serverDTO = DataProviderCreator.createDataProvider(LightSearchServerDTO.class);
        DatabaseRecordIdentifier identifier =
                DataProviderCreator.createDataProvider(DatabaseRecordIdentifier.class, serverDTO);

        DatabaseConnection connection = DataProviderCreator.createDataProvider(DatabaseConnection.class, username, password);
        assertNotNull(connection, "Database connection is null!");

        String pattern = CurrentDateTimePattern.DATE_TIME_IN_STANDARD_FORM_WITH_MS.getPattern();
        assertNotNull(tableName, "Table name is null!");
            
        DatabasePreparedStatement dbPS = DatabasePreparedStatementInit.databasePreparedStatementSelect(
                connection, tableName, identifier.next(), state);
        DatabaseStatementResultSetSelect dbRSSelect =
                DatabaseStatementResultSetSelectInit.databaseStatementResultSetSelect(dbPS, pattern);
        try {
            dbRSSelect.exec();
        } catch (DatabaseStatementResultSetException ex) {
            catchMessage(ex);
        }

        testEnd("DatabaseStatementResultSetSelect", "exec()");
    }
}
