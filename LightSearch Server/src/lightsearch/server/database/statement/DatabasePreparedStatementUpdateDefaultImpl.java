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

import java.sql.PreparedStatement;
import java.sql.SQLException;
import lightsearch.server.database.DatabaseConnection;
import lightsearch.server.exception.DatabasePreparedStatementException;

/**
 *
 * @author ViiSE
 */
public class DatabasePreparedStatementUpdateDefaultImpl implements DatabasePreparedStatement {

    private final DatabaseConnection databaseConnection;
    private final String tableName;
    private final long lsCode;
    private final boolean state;
    
    public DatabasePreparedStatementUpdateDefaultImpl(DatabaseConnection databaseConnection,
            String tableName, long lsCode, boolean state) {
        this.databaseConnection = databaseConnection;
        this.tableName = tableName;
        this.lsCode = lsCode;
        this.state = state;
    }
    
    private String query() {
        return "UPDATE " + tableName +  
               " SET STATE = " + state +
               " WHERE LSCODE = " + lsCode;
    }
    
    @Override
    public PreparedStatement preparedStatement() throws DatabasePreparedStatementException {
        try {
            PreparedStatement ps = databaseConnection.connection().prepareStatement(query());
            return ps;
        } catch (SQLException ex) {
            throw new DatabasePreparedStatementException(ex.getMessage());
        }
    }
    
}
