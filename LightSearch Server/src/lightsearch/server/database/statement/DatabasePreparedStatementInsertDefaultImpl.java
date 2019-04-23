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
public class DatabasePreparedStatementInsertDefaultImpl implements DatabasePreparedStatement {

    private final DatabaseConnection databaseConnection;
    private final String tableName;
    private final String command;
    private final String dateTime;
    private final long lsCode;
    private final boolean state;
        
    public DatabasePreparedStatementInsertDefaultImpl(DatabaseConnection databaseConnection, 
            String tableName, String command, String dateTime, long lsCode, boolean state) {
        this.databaseConnection = databaseConnection;
        this.tableName = tableName;
        this.command = command;
        this.dateTime = dateTime;
        this.lsCode = lsCode;
        this.state = state;
    }

    private String query() {
        return "INSERT INTO " + tableName 
                + " (LSCODE, DDOC, CMDIN, STATE) VALUES (?,?,?,?)";
    }
    
    @Override
    public PreparedStatement preparedStatement() throws DatabasePreparedStatementException {
        try {
            PreparedStatement ps = databaseConnection.connection().prepareStatement(query());
            ps.setLong(1, lsCode);
            ps.setString(2, dateTime);
            ps.setBytes(3, command.getBytes());
            ps.setBoolean(4, state);
            return ps;
        } catch (SQLException ex) {
            throw new DatabasePreparedStatementException(ex.getMessage());
        }
    }
}
