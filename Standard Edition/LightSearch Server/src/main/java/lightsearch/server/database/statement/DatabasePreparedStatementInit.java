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

import lightsearch.server.database.DatabaseConnection;

/**
 *
 * @author ViiSE
 */
public class DatabasePreparedStatementInit {
    
    public static DatabasePreparedStatement databasePreparedStatementInsert(DatabaseConnection databaseConnection, 
            String tableName, String command, String dateTime, long lsCode, boolean state) {
        return new DatabasePreparedStatementInsertWin1251DefaultImpl(databaseConnection, tableName, command, dateTime, lsCode, state);
    }
    
    public static DatabasePreparedStatement databasePreparedStatementSelect(DatabaseConnection databaseConnection,
            String tableName, long lsCode, boolean state) {
        return new DatabasePreparedStatementSelectDefaultImpl(databaseConnection, tableName, lsCode, state);
    }
    
    public static DatabasePreparedStatement databasePreparedStatementUpdate(DatabaseConnection databaseConnection,
            String tableName, long lsCode, boolean state) {
        return new DatabasePreparedStatementUpdateDefaultImpl(databaseConnection, tableName, lsCode, state);
    }
}
