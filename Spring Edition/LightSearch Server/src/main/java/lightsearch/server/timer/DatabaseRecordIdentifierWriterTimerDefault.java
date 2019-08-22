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

import lightsearch.server.exception.IdentifierException;
import lightsearch.server.identifier.DatabaseRecordIdentifier;
import lightsearch.server.identifier.DatabaseRecordIdentifierWriter;
import lightsearch.server.log.LogMessageTypeEnum;
import lightsearch.server.log.LoggerServer;
import lightsearch.server.producer.time.DateTimeComparatorProducer;
import lightsearch.server.thread.ThreadManager;
import lightsearch.server.time.CurrentDateTime;
import lightsearch.server.time.DateTimeComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 *
 * @author ViiSE
 */
@Component("databaseRecordIdentifierWriterTimerDefault")
@Scope("prototype")
public class DatabaseRecordIdentifierWriterTimerDefault extends SuperDatabaseRecordIdentifierWriterTimer {

    private final String ID;

    @Autowired
    private DateTimeComparatorProducer producer;

    @Autowired
    public DatabaseRecordIdentifierWriterTimerDefault(LoggerServer loggerServer,
                                                      CurrentDateTime currentDateTime, ThreadManager threadManager,
                                                      DatabaseRecordIdentifierWriter databaseRecordIdentifierWriter,
                                                      DatabaseRecordIdentifier databaseRecordIdentifier, long minutesToWrite, TimersIDEnum id) {
        super(loggerServer, currentDateTime, threadManager,
                databaseRecordIdentifierWriter, databaseRecordIdentifier, minutesToWrite, id);
        ID = super.id().stringValue();
    }

    @Override
    public void run() {
        DateTimeComparator dateTimeComparator = producer.getDateTimeComparatorDefaultInstance(null);

        while(super.threadManager().holder().getThread(ID).isWorked()) {
            LocalDateTime dateTimeToWrite = LocalDateTime.now().plusMinutes(super.minutesToWrite());
            boolean isDone = false;
            while(!isDone) {
                try { Thread.sleep(1000); } catch(InterruptedException ignored) {}
                if(dateTimeComparator.isBefore(dateTimeToWrite, LocalDateTime.now())) {
                    try {
                        super.identifierDatabaseRecordWriter().write(super.identifierDatabaseRecord().databaseRecordIdentifier());
                        isDone = true;
                    } catch (IdentifierException ex) {
                        super.loggerServer().log(LogMessageTypeEnum.ERROR, super.currentDateTime(), ex.getMessage());
                        isDone = true;
                    }
                }
                dateTimeToWrite = null;
            }
        }
        super.threadManager().holder().getThread(ID).setIsDone(true);
    }
    
}
