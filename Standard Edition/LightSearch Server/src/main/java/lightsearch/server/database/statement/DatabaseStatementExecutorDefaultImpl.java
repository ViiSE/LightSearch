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

import lightsearch.server.database.*;
import lightsearch.server.database.cmd.message.DatabaseCommandMessage;
import lightsearch.server.database.statement.result.DatabaseStatementResult;
import lightsearch.server.database.statement.result.DatabaseStatementResultInit;
import lightsearch.server.exception.DatabaseReaderException;
import lightsearch.server.exception.DatabaseStatementExecutorException;
import lightsearch.server.exception.DatabaseWriterException;

/**
 * Реализация интерфейса {@link lightsearch.server.database.statement.DatabaseStatementExecutor} по умолчанию.
 * <p>
 * Использует {@link lightsearch.server.database.DatabaseWriter} и {@link lightsearch.server.database.DatabaseReader}
 * для записи команды в таблицу базы данных и чтения результата команды из другой таблицы базы данных соответственно.
 * @author ViiSE
 * @see lightsearch.server.cmd.client.processor
 * @since 2.0.0
 */
public class DatabaseStatementExecutorDefaultImpl implements DatabaseStatementExecutor {

    private final DatabaseConnection databaseConnection;
    private final long lsCode;
    private final String dateTime;
    private final DatabaseCommandMessage databaseCommandMessage;
    
    public DatabaseStatementExecutorDefaultImpl(DatabaseConnection databaseConnection, 
            long lsCode, String dateTime, DatabaseCommandMessage databaseCommandMessage) {
        this.databaseConnection = databaseConnection;
        this.lsCode = lsCode;
        this.dateTime = dateTime;
        this.databaseCommandMessage = databaseCommandMessage;
    }
    
    @Override
    public DatabaseStatementResult exec() throws DatabaseStatementExecutorException {
        String message = databaseCommandMessage.message();
        DatabaseWriter dbWriter = DatabaseWriterInit.databaseWriter(databaseConnection, lsCode, dateTime, message);
        DatabaseReader dbReader = DatabaseReaderInit.databaseReader(databaseConnection, lsCode);
        try {
            dbWriter.write();
            String result = dbReader.read();
            return DatabaseStatementResultInit.databaseStatementResult(result);
        } catch (DatabaseWriterException ex) {
            throw new DatabaseStatementExecutorException(ex.getMessage(), ex.getMessageRU());
        } catch (DatabaseReaderException ex) {
            throw new DatabaseStatementExecutorException(ex.getMessage(), ex.getMessageRU());
        }
    }
    
}
