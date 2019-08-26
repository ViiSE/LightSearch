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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import lightsearch.server.database.statement.DatabasePreparedStatement;
import lightsearch.server.exception.DatabasePreparedStatementException;
import lightsearch.server.exception.DatabaseStatementResultSetException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lightsearch.server.time.DateTimeComparator;
import lightsearch.server.time.DateTimeComparatorInit;

/**
 * Реализация интерфейса {@link lightsearch.server.database.statement.result.DatabaseStatementResultSetSelect} по
 * умолчанию.
 * <p>
 * LightSearch Server ждет ответа от програмы, реализующей бизнес-логику, 30 секунд. Если ответ в таблице за это время
 * не появился, то генерируется исключение {@link lightsearch.server.exception.DatabaseStatementResultSetException}.
 * @author ViiSE
 * @see lightsearch.server.time.DateTimeComparator
 * @see lightsearch.server.database.statement.DatabasePreparedStatement
 * @since 2.0
 */
public class DatabaseStatementResultSetSelectDefaultImpl implements DatabaseStatementResultSetSelect {

    private final DatabasePreparedStatement databasePreparedStatement;
    private final String pattern;
    private String result;
    private String code;
    
    public DatabaseStatementResultSetSelectDefaultImpl(DatabasePreparedStatement databasePreparedStatement, 
            String pattern) {
        this.databasePreparedStatement = databasePreparedStatement;
        this.pattern = pattern;
    }

    @Override
    public void exec() throws DatabaseStatementResultSetException {
        LocalDateTime dateTimeStop = LocalDateTime.now().plusSeconds(30);
        DateTimeComparator dateTimeComparator = DateTimeComparatorInit.dateTimeComparator(pattern);
        try {
            PreparedStatement ps = databasePreparedStatement.preparedStatement();
            while(dateTimeStop.isAfter(LocalDateTime.now())) {
                ResultSet rs = ps.executeQuery();
                if(rs.next()) {
                    String dateDoc = rs.getString("DDOC");
                    LocalDateTime nowMax = LocalDateTime.now().with(LocalTime.MAX);
                    LocalDateTime nowMin = LocalDateTime.now().with(LocalTime.MIN);
                    if(dateTimeComparator.isBefore(dateDoc, nowMax)
                        && dateTimeComparator.isAfter(dateDoc, nowMin)) {
                        result = rs.getString("CMDOUT");
                        code = rs.getString("KOD");
                        return;
                    }
                }
            }
        }
        catch(SQLException | DatabasePreparedStatementException ex) {
            throw new DatabaseStatementResultSetException(ex.getMessage());
        }
        throw new DatabaseStatementResultSetException("Request timed out");
    }

    @Override
    public String result() {
        return result;
    }

    @Override
    public String code() {
        return code;
    }
    
}
