/*
 *  Copyright 2019 ViiSE.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package lightsearch.server.database.statement;


import lightsearch.server.database.DatabaseReader;
import lightsearch.server.database.DatabaseWriter;
import lightsearch.server.database.cmd.message.DatabaseCommandMessage;
import lightsearch.server.database.statement.result.DatabaseStatementResult;
import lightsearch.server.exception.DatabaseReaderException;
import lightsearch.server.exception.DatabaseStatementExecutorException;
import lightsearch.server.exception.DatabaseWriterException;
import lightsearch.server.producer.database.DatabaseReaderProducer;
import lightsearch.server.producer.database.DatabaseStatementResultProducer;
import lightsearch.server.producer.database.DatabaseWriterProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author ViiSE
 */
@Component("databaseStatementExecutorH2Test")
@Scope("prototype")
public class DatabaseStatementExecutorH2TestImpl implements DatabaseStatementExecutor {

    private final long lsCode;
    private final String dateTime;
    private final DatabaseCommandMessage databaseCommandMessage;

    @Autowired private DatabaseWriterProducer dbWriterProducer;
    @Autowired private DatabaseReaderProducer dbReaderProducer;
    @Autowired private DatabaseStatementResultProducer dbStatementResProducer;

    public DatabaseStatementExecutorH2TestImpl(long lsCode, String dateTime, DatabaseCommandMessage databaseCommandMessage) {
        this.lsCode = lsCode;
        this.dateTime = dateTime;
        this.databaseCommandMessage = databaseCommandMessage;
    }
    
    @Override
    public DatabaseStatementResult exec() throws DatabaseStatementExecutorException {
        String message = databaseCommandMessage.message();
        DatabaseWriter dbWriter = dbWriterProducer.getDatabaseWriterH2TestInstance(lsCode, dateTime, message);
        DatabaseReader dbReader = dbReaderProducer.getDatabaseReaderH2TestInstance(lsCode);
        try {
            dbWriter.write();
            String result = dbReader.read();
            return dbStatementResProducer.getDatabaseStatementResultDefaultInstance(result);
        } catch (DatabaseWriterException ex) {
            throw new DatabaseStatementExecutorException(ex.getMessage(), ex.getMessageRU());
        } catch (DatabaseReaderException ex) {
            throw new DatabaseStatementExecutorException(ex.getMessage(), ex.getMessageRU());
        }
    }
    
}
