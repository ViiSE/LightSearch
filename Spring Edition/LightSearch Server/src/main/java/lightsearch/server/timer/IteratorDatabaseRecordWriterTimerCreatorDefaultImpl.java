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

import lightsearch.server.data.IteratorDatabaseRecordWriterTimerCreatorDTO;
import lightsearch.server.iterator.IteratorDatabaseRecord;
import lightsearch.server.iterator.IteratorDatabaseRecordWriter;
import lightsearch.server.log.LoggerServer;
import lightsearch.server.producer.timer.IteratorDatabaseRecordWriterTimerProducer;
import lightsearch.server.thread.ThreadManager;
import lightsearch.server.time.CurrentDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author ViiSE
 */
@Service("iteratorDatabaseRecordWriterTimerCreatorDefault")
@Scope("prototype")
public class IteratorDatabaseRecordWriterTimerCreatorDefaultImpl implements IteratorDatabaseRecordWriterTimerCreator {

    private final LoggerServer loggerServer;
    private final CurrentDateTime currentDateTime;
    private final ThreadManager threadManager;
    private final IteratorDatabaseRecordWriter iteratorDatabaseRecordWriter;
    private final IteratorDatabaseRecord iteratorDatabaseRecord;
    private final long minutesToWrite;
    private final TimersIDEnum id;

    @Autowired
    private IteratorDatabaseRecordWriterTimerProducer producer;

    public IteratorDatabaseRecordWriterTimerCreatorDefaultImpl(
            LoggerServer loggerServer, CurrentDateTime currentDateTime, ThreadManager threadManager,
            IteratorDatabaseRecordWriterTimerCreatorDTO iteratorDbRecWriterTimerCrDTO) {
        this.loggerServer = loggerServer;
        this.currentDateTime = currentDateTime;
        this.threadManager = threadManager;
        this.iteratorDatabaseRecordWriter = iteratorDbRecWriterTimerCrDTO.iteratorDatabaseRecordWriter();
        this.iteratorDatabaseRecord = iteratorDbRecWriterTimerCrDTO.iteratorDatabaseRecord();
        this.minutesToWrite = iteratorDbRecWriterTimerCrDTO.minutesToWrite();
        this.id = iteratorDbRecWriterTimerCrDTO.id();
    }

    @Override
    public SuperIteratorDatabaseRecordWriterTimer getTimer() {
        return producer.getIteratorDatabaseRecordWriterTimerDefaultInstance(
                loggerServer, currentDateTime, threadManager, iteratorDatabaseRecordWriter, iteratorDatabaseRecord, minutesToWrite, id);
    }
}
