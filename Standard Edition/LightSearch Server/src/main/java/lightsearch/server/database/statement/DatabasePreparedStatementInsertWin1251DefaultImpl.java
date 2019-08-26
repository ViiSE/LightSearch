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

import java.io.UnsupportedEncodingException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import lightsearch.server.database.DatabaseConnection;
import lightsearch.server.exception.DatabasePreparedStatementException;

/**
 * Создает скомпилированный запрос для добавления команды в таблицу базы данных, совместимый с кодировкой windows-1251.
 * @author ViiSE
 * @since 2.0.0
 */
public class DatabasePreparedStatementInsertWin1251DefaultImpl implements DatabasePreparedStatement {

    private final DatabaseConnection databaseConnection;
    private final String tableName;
    private final String command;
    private final String dateTime;
    private final long lsCode;
    private final boolean state;
        
    public DatabasePreparedStatementInsertWin1251DefaultImpl(DatabaseConnection databaseConnection, 
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
            ps.setBytes(3, command.getBytes("windows-1251"));
            ps.setBoolean(4, state);
            return ps;
        } catch (SQLException | UnsupportedEncodingException ex) {
            throw new DatabasePreparedStatementException(ex.getMessage());
        }
    }
}
