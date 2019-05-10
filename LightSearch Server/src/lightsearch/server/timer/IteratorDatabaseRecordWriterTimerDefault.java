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

import java.time.LocalDateTime;
import lightsearch.server.iterator.IteratorDatabaseRecord;
import lightsearch.server.iterator.IteratorDatabaseRecordWriter;
import lightsearch.server.exception.IteratorException;
import lightsearch.server.log.LogMessageTypeEnum;
import lightsearch.server.log.LoggerServer;
import lightsearch.server.thread.ThreadManager;
import lightsearch.server.time.CurrentDateTime;

/**
 *
 * @author ViiSE
 */
public class IteratorDatabaseRecordWriterTimerDefault extends SuperIteratorDatabaseRecordWriterTimer {
    
    private final String ID;
    
    public IteratorDatabaseRecordWriterTimerDefault(LoggerServer loggerServer, 
            CurrentDateTime currentDateTime, ThreadManager threadManager,
            IteratorDatabaseRecordWriter iteratorDatabaseRecordWriter,
            IteratorDatabaseRecord iteratorDatabaseRecord, long minutesToWrite, TimersIDEnum id) {
        super(loggerServer, currentDateTime, threadManager,
                iteratorDatabaseRecordWriter, iteratorDatabaseRecord, minutesToWrite, id);
        ID = super.id().stringValue();
    }

    @Override
    public void run() {
        while(super.threadManager().holder().getThread(ID).isWorked()) {
            LocalDateTime dateTimeToWrite = LocalDateTime.now().plusMinutes(super.minutesToWrite());
            boolean isDone = false;
            while(!isDone) {
                if(super.dateTimeComparator().isBefore(dateTimeToWrite, LocalDateTime.now())) {
                    try {
                        //System.out.println("I AM WRITING NOW!");
                        super.iteratorDatabaseRecordWriter().write(super.iteratorDatabaseRecord().iteratorDatabaseRecord());
                        isDone = true;
                    } catch (IteratorException ex) {
                        super.loggerServer().log(LogMessageTypeEnum.ERROR, super.currentDateTime(), ex.getMessage());
                        isDone = true;
                    }
                }
            }
        }
        super.threadManager().holder().getThread(ID).setIsDone(true);
    }
    
}
