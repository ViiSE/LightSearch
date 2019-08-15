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

package lightsearch.server.data;

import lightsearch.server.iterator.IteratorDatabaseRecord;
import lightsearch.server.iterator.IteratorDatabaseRecordWriter;
import lightsearch.server.log.LoggerServer;
import lightsearch.server.thread.ThreadManager;
import lightsearch.server.time.CurrentDateTime;
import lightsearch.server.timer.TimersIDEnum;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("iteratorDatabaseRecordWriterTimerCreatorDTODefault")
@Scope("prototype")
public class IteratorDatabaseRecordWriterTimerCreatorDTODefaultImpl implements IteratorDatabaseRecordWriterTimerCreatorDTO {

    private final IteratorDatabaseRecordWriter iteratorDatabaseRecordWriter;
    private final IteratorDatabaseRecord iteratorDatabaseRecord;
    private final long minutesToWrite;
    private final TimersIDEnum id;

    public IteratorDatabaseRecordWriterTimerCreatorDTODefaultImpl(
            IteratorDatabaseRecord iteratorDatabaseRecord, IteratorDatabaseRecordWriter iteratorDatabaseRecordWriter,
            long minutesToWrite, TimersIDEnum id) {
        this.iteratorDatabaseRecordWriter = iteratorDatabaseRecordWriter;
        this.iteratorDatabaseRecord = iteratorDatabaseRecord;
        if(minutesToWrite < 0)
            this.minutesToWrite = 0;
        else
            this.minutesToWrite = minutesToWrite;
        this.id = id;
    }

    @Override
    public IteratorDatabaseRecordWriter iteratorDatabaseRecordWriter() {
        return iteratorDatabaseRecordWriter;
    }

    @Override
    public IteratorDatabaseRecord iteratorDatabaseRecord() {
        return iteratorDatabaseRecord;
    }

    @Override
    public long minutesToWrite() {
        return minutesToWrite;
    }

    @Override
    public TimersIDEnum id() {
        return id;
    }
}
