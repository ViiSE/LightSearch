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

import lightsearch.server.iterator.IteratorDatabaseRecord;
import lightsearch.server.iterator.IteratorDatabaseRecordWriter;
import lightsearch.server.log.LoggerServer;
import lightsearch.server.thread.ThreadManager;
import lightsearch.server.time.CurrentDateTime;
import lightsearch.server.time.DateTimeComparator;
import lightsearch.server.time.DateTimeComparatorInit;

/**
 * Абстрактный класс таймера итератора записи базы данных.
 * <p>
 * Итератор записи базы данных - уникальный идентификатор, который записывается в поле LSCODE таблицы LS_REQUEST. Это же
 * значение записывается в поле LSCODE таблицы LS_RESPONSE, со стороны программы, реализующей бизнес-логику.
 * <p>
 * В LS_REQUEST записываются команды, пришедшие от клиента, а из LS_RESPONSE считываются результат этих команд. Для того,
 * чтобы идентифицировать команду с соответствующим результатом, используется итератор записи базы данных.
 * <p>
 * Значение итератора записи базы данных записывается в файл. При старте LightSearch Server это значение считывается, и
 * затем снова итерируется при каждом обращении в базу.
 * <p>
 * Для задания времени записи итератора базы данных в файл в минутах предусмотрено поле {@link #minutesToWrite}. Через
 * каждое значение поля {@link #minutesToWrite} будет производиться запись значения итератора в файл.
 * @author ViiSE
 * @see lightsearch.server.iterator.IteratorDatabaseRecord
 * @since 2.0
 */
public abstract class SuperIteratorDatabaseRecordWriterTimer extends SuperTimer {
    
    private final IteratorDatabaseRecordWriter iteratorDatabaseRecordWriter;
    private final IteratorDatabaseRecord iteratorDatabaseRecord;
    private final long minutesToWrite;
    private final DateTimeComparator dateTimeComparator = DateTimeComparatorInit.dateTimeComparator(null);
    
    public SuperIteratorDatabaseRecordWriterTimer(LoggerServer loggerServer, 
            CurrentDateTime currentDateTime, ThreadManager threadManager, 
            IteratorDatabaseRecordWriter iteratorDatabaseRecordWriter,
            IteratorDatabaseRecord iteratorDatabaseRecord, long minutesToWrite, TimersIDEnum id) {
        super(loggerServer, currentDateTime, threadManager, id);
        this.iteratorDatabaseRecordWriter = iteratorDatabaseRecordWriter;
        this.iteratorDatabaseRecord = iteratorDatabaseRecord;
        this.minutesToWrite = minutesToWrite;
    }
    
    public IteratorDatabaseRecordWriter iteratorDatabaseRecordWriter() {
        return iteratorDatabaseRecordWriter;
    }
    
    public IteratorDatabaseRecord iteratorDatabaseRecord() {
        return iteratorDatabaseRecord;
    }
    
    public long minutesToWrite() {
        return minutesToWrite;
    }
    
    public DateTimeComparator dateTimeComparator() {
        return dateTimeComparator;
    }
}
