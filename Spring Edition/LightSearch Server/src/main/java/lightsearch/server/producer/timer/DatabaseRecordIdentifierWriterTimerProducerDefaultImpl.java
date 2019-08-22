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

package lightsearch.server.producer.timer;

import lightsearch.server.identifier.DatabaseRecordIdentifier;
import lightsearch.server.identifier.DatabaseRecordIdentifierWriter;
import lightsearch.server.log.LoggerServer;
import lightsearch.server.thread.ThreadManager;
import lightsearch.server.time.CurrentDateTime;
import lightsearch.server.timer.SuperDatabaseRecordIdentifierWriterTimer;
import lightsearch.server.timer.TimersIDEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service("databaseRecordIdentifierWriterTimerProducerDefault")
public class DatabaseRecordIdentifierWriterTimerProducerDefaultImpl implements DatabaseRecordIdentifierWriterTimerProducer {

    private final String DB_IDENTIFIER_REC_WRITER_TIMER = "databaseRecordIdentifierWriterTimerDefault";

    @Autowired
    private ApplicationContext ctx;

    @Override
    public SuperDatabaseRecordIdentifierWriterTimer getDatabaseRecordIdentifierWriterTimerDefaultInstance(
            LoggerServer loggerServer, CurrentDateTime currentDateTime, ThreadManager threadManager,
            DatabaseRecordIdentifierWriter databaseRecordIdentifierWriter, DatabaseRecordIdentifier databaseRecordIdentifier,
            long minutesToWrite, TimersIDEnum id) {
        return (SuperDatabaseRecordIdentifierWriterTimer) ctx.getBean(DB_IDENTIFIER_REC_WRITER_TIMER, loggerServer,
                currentDateTime, threadManager, databaseRecordIdentifierWriter, databaseRecordIdentifier, minutesToWrite, id);
    }
}
