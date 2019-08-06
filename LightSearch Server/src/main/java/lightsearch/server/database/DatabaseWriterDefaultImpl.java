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

import lightsearch.server.exception.DatabaseWriterException;
import lightsearch.server.database.statement.DatabasePreparedStatement;
import lightsearch.server.database.statement.DatabasePreparedStatementInit;
import lightsearch.server.database.statement.result.DatabaseStatementResultSet;
import lightsearch.server.database.statement.result.DatabaseStatementResultSetUpdateInit;
import lightsearch.server.exception.DatabaseStatementResultSetException;

/**
 *
 * @author ViiSE
 */
public class DatabaseWriterDefaultImpl implements DatabaseWriter {

    private final DatabaseConnection databaseConnection;
    private final long lsCode;
    private final String dateTime;
    private final String command;
    private final String tableName = DatabaseTableEnum.LS_REQUEST.stringValue();
        
    public DatabaseWriterDefaultImpl(DatabaseConnection databaseConnection, long lsCode, 
            String dateTime, String command) {
        this.databaseConnection = databaseConnection;
        this.lsCode = lsCode;
        this.dateTime = dateTime;
        this.command = command;
    }

    @Override
    public void write() throws DatabaseWriterException {
        DatabasePreparedStatement dbPS = DatabasePreparedStatementInit.databasePreparedStatementInsert(databaseConnection,
                tableName, command, dateTime, lsCode, true);
        DatabaseStatementResultSet dbRS = DatabaseStatementResultSetUpdateInit.databaseStatementResultSetUpdate(dbPS);
        try {
            dbRS.exec();
        } catch (DatabaseStatementResultSetException ex) {
            throw new DatabaseWriterException(ex.getMessage(), 
                    "Не удалось записать команду в базу данных. Обратитесь к администратору для устранения проблемы.");
        }
    }
    
}
