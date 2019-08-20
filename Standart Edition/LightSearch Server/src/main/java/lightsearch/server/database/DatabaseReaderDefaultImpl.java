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
package lightsearch.server.database;

import lightsearch.server.exception.DatabaseReaderException;
import lightsearch.server.database.statement.DatabasePreparedStatement;
import lightsearch.server.database.statement.DatabasePreparedStatementInit;
import lightsearch.server.database.statement.result.DatabaseStatementResultSetSelect;
import lightsearch.server.database.statement.result.DatabaseStatementResultSetSelectInit;
import lightsearch.server.database.statement.result.DatabaseStatementResultSetUpdate;
import lightsearch.server.database.statement.result.DatabaseStatementResultSetUpdateInit;
import lightsearch.server.exception.DatabaseStatementResultSetException;
import lightsearch.server.time.CurrentDateTimePattern;

/**
 *
 * @author ViiSE
 */
public class DatabaseReaderDefaultImpl implements DatabaseReader {

    private final DatabaseConnection databaseConnection;
    private final long lsCode;
    private final String tableName = DatabaseTableEnum.LS_RESPONSE.stringValue();
    private final String pattern = CurrentDateTimePattern.dateTimeInStandardFormWithMs();
    
    public DatabaseReaderDefaultImpl(DatabaseConnection databaseConnection, long lsCode) {
        this.databaseConnection = databaseConnection;
        this.lsCode = lsCode;
    }
    
    @Override
    public String read() throws DatabaseReaderException {
        DatabasePreparedStatement dbPSS = DatabasePreparedStatementInit.databasePreparedStatementSelect(databaseConnection,
                tableName, lsCode, false);
        DatabaseStatementResultSetSelect dbRSS = DatabaseStatementResultSetSelectInit.databaseStatementResultSetSelect(dbPSS, pattern);
        try { 
            dbRSS.exec();
            DatabasePreparedStatement dbPSU = DatabasePreparedStatementInit.databasePreparedStatementUpdate(databaseConnection, tableName, lsCode, true);
            DatabaseStatementResultSetUpdate dbRSU = DatabaseStatementResultSetUpdateInit.databaseStatementResultSetUpdate(dbPSU);
            dbRSU.exec();
            return dbRSS.result();
        } catch (DatabaseStatementResultSetException ex) {
            throw new DatabaseReaderException(ex.getMessage(), 
                    "Не удалось считать результат команды из базы данных. Обратитесь к администратору для устранения проблемы.");
        }
    }
}
