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
package lightsearch.server.timer;

import lightsearch.server.identifier.DatabaseRecordIdentifier;
import lightsearch.server.identifier.DatabaseRecordIdentifierWriter;
import lightsearch.server.log.LoggerServer;
import lightsearch.server.thread.ThreadManager;
import lightsearch.server.time.CurrentDateTime;
import lightsearch.server.time.DateTimeComparator;
import lightsearch.server.time.DateTimeComparatorInit;

/**
 * Абстрактный класс таймера идентификатора записи базы данных.
 * <p>
 * Идентификатор записи базы данных - уникальный идентификатор, который записывается в поле LSCODE таблицы LS_REQUEST. Это же
 * значение записывается в поле LSCODE таблицы LS_RESPONSE, со стороны программы, реализующей бизнес-логику.
 * <p>
 * В LS_REQUEST записываются команды, пришедшие от клиента, а из LS_RESPONSE считываются результат этих команд. Для того,
 * чтобы идентифицировать команду с соответствующим результатом, используется идентификатор записи базы данных.
 * <p>
 * Значение идентификатора записи базы данных записывается в файл. При старте LightSearch Server это значение считывается,
 * и затем снова инкрементируется при каждом обращении в базу.
 * <p>
 * Для задания времени записи идентификатора базы данных в файл в минутах предусмотрено поле {@link #minutesToWrite}.
 * Через каждое значение поля {@link #minutesToWrite} будет производится запись значения идентификатора в файл.
 * @author ViiSE
 * @see DatabaseRecordIdentifier
 * @since 2.0
 */
public abstract class SuperDatabaseRecordIdentifierWriterTimer extends SuperTimer {
    
    private final DatabaseRecordIdentifierWriter databaseRecordIdentifierWriter;
    private final DatabaseRecordIdentifier databaseRecordIdentifier;
    private final long minutesToWrite;
    private final DateTimeComparator dateTimeComparator = DateTimeComparatorInit.dateTimeComparator(null);
    
    public SuperDatabaseRecordIdentifierWriterTimer(LoggerServer loggerServer,
                                                    CurrentDateTime currentDateTime, ThreadManager threadManager,
                                                    DatabaseRecordIdentifierWriter databaseRecordIdentifierWriter,
                                                    DatabaseRecordIdentifier databaseRecordIdentifier, long minutesToWrite, TimersIDEnum id) {
        super(loggerServer, currentDateTime, threadManager, id);
        this.databaseRecordIdentifierWriter = databaseRecordIdentifierWriter;
        this.databaseRecordIdentifier = databaseRecordIdentifier;
        this.minutesToWrite = minutesToWrite;
    }
    
    public DatabaseRecordIdentifierWriter databaseRecordIdentifierWriter() {
        return databaseRecordIdentifierWriter;
    }
    
    public DatabaseRecordIdentifier databaseIdentifierRecord() {
        return databaseRecordIdentifier;
    }
    
    public long minutesToWrite() {
        return minutesToWrite;
    }
    
    public DateTimeComparator dateTimeComparator() {
        return dateTimeComparator;
    }
}
