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

import java.sql.SQLException;
import lightsearch.server.database.statement.DatabasePreparedStatement;
import lightsearch.server.exception.DatabasePreparedStatementException;
import lightsearch.server.exception.DatabaseStatementResultSetException;

/**
 *
 * @author ViiSE
 */
public class DatabaseStatementResultSetUpdateDefaultImpl implements DatabaseStatementResultSetUpdate {

    private final DatabasePreparedStatement databasePreparedStatement;
    
    public DatabaseStatementResultSetUpdateDefaultImpl(DatabasePreparedStatement databasePreparedStatement) {
        this.databasePreparedStatement = databasePreparedStatement;
    }
    
    @Override
    public void exec() throws DatabaseStatementResultSetException {
        try {
            databasePreparedStatement.preparedStatement().executeUpdate();
        }
        catch(SQLException | DatabasePreparedStatementException ex) {
            throw new DatabaseStatementResultSetException(ex.getMessage());
        }
    }    
}
