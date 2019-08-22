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

import lightsearch.server.data.DatabaseRecordIdentifierWriterTimerCreatorDTO;
import lightsearch.server.identifier.DatabaseRecordIdentifier;
import lightsearch.server.identifier.DatabaseRecordIdentifierWriter;
import lightsearch.server.log.LoggerServer;
import lightsearch.server.producer.timer.DatabaseRecordIdentifierWriterTimerProducer;
import lightsearch.server.thread.ThreadManager;
import lightsearch.server.time.CurrentDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author ViiSE
 */
@Service("databaseRecordIdentifierWriterTimerCreatorDefault")
@Scope("prototype")
public class DatabaseRecordIdentifierWriterTimerCreatorDefaultImpl implements DatabaseRecordIdentifierWriterTimerCreator {

    private final LoggerServer loggerServer;
    private final CurrentDateTime currentDateTime;
    private final ThreadManager threadManager;
    private final DatabaseRecordIdentifierWriter databaseRecordIdentifierWriter;
    private final DatabaseRecordIdentifier databaseRecordIdentifier;
    private final long minutesToWrite;
    private final TimersIDEnum id;

    @Autowired
    private DatabaseRecordIdentifierWriterTimerProducer producer;

    public DatabaseRecordIdentifierWriterTimerCreatorDefaultImpl(
            LoggerServer loggerServer, CurrentDateTime currentDateTime, ThreadManager threadManager,
            DatabaseRecordIdentifierWriterTimerCreatorDTO identifierDbRecWriterTimerCrDTO) {
        this.loggerServer = loggerServer;
        this.currentDateTime = currentDateTime;
        this.threadManager = threadManager;
        this.databaseRecordIdentifierWriter = identifierDbRecWriterTimerCrDTO.databaseRecordIdentifierWriter();
        this.databaseRecordIdentifier = identifierDbRecWriterTimerCrDTO.databaseRecordIdentifier();
        this.minutesToWrite = identifierDbRecWriterTimerCrDTO.minutesToWrite();
        this.id = identifierDbRecWriterTimerCrDTO.id();
    }

    @Override
    public SuperDatabaseRecordIdentifierWriterTimer getTimer() {
        return producer.getDatabaseRecordIdentifierWriterTimerDefaultInstance(
                loggerServer, currentDateTime, threadManager, databaseRecordIdentifierWriter, databaseRecordIdentifier, minutesToWrite, id);
    }
}
