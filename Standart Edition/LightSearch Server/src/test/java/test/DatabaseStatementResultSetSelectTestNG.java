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

import lightsearch.server.data.LightSearchServerDatabaseDTO;
import lightsearch.server.data.LightSearchServerDatabaseDTOInit;
import lightsearch.server.database.DatabaseConnection;
import lightsearch.server.database.DatabaseConnectionCreator;
import lightsearch.server.database.DatabaseConnectionCreatorInit;
import lightsearch.server.exception.DatabaseConnectionCreatorException;
import lightsearch.server.database.statement.DatabasePreparedStatement;
import lightsearch.server.database.statement.DatabasePreparedStatementInit;
import lightsearch.server.database.statement.result.DatabaseStatementResultSetSelect;
import lightsearch.server.database.statement.result.DatabaseStatementResultSetSelectInit;
import lightsearch.server.exception.DatabaseStatementResultSetException;
import lightsearch.server.iterator.IteratorDatabaseRecord;
import lightsearch.server.iterator.IteratorDatabaseRecordInit;
import lightsearch.server.time.CurrentDateTimePattern;
import static org.testng.Assert.*;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class DatabaseStatementResultSetSelectTestNG {
    
    @Test
    public void exec() {
        testBegin("DatabaseStatementResultSetSelect", "exec()");
        
        IteratorDatabaseRecord iterator = IteratorDatabaseRecordInit.iteratorDatabaseRecord(0);
        
        String dbIP = "127.0.0.1";
        String dbName = "dbName";
        int dbPort = 5432;
        String username = "admin";
        String password = "pass";
        LightSearchServerDatabaseDTO databaseDTO = 
                LightSearchServerDatabaseDTOInit.lightSearchServerDatabaseDTO(dbIP,
                        dbName, dbPort);
        DatabaseConnectionCreator dbConnCreator =
                DatabaseConnectionCreatorInit.databaseConnectionCreator(databaseDTO,
                        username, password);
        String pattern = CurrentDateTimePattern.dateTimeInStandartFormWithMs();
        try {
            DatabaseConnection databaseConnection = dbConnCreator.createFirebirdConnection();
            String tableName = "LS_RESPONSE";
            assertNotNull(tableName, "Table name is null!");
                        
            boolean state = false;
            
            DatabasePreparedStatement dbPS = 
                    DatabasePreparedStatementInit.databasePreparedStatementSelect(databaseConnection,
                            tableName, iterator.next(), state);
            DatabaseStatementResultSetSelect dbRSSelect = 
                    DatabaseStatementResultSetSelectInit.databaseStatementResultSetSelect(dbPS,
                            pattern);
            dbRSSelect.exec();
        } catch (DatabaseStatementResultSetException |
                 DatabaseConnectionCreatorException ex) {
            System.out.println("CATCH! Message: " + ex.getMessage());
        }
        
        testEnd("DatabaseStatementResultSetSelect", "exec()");
    }
}
