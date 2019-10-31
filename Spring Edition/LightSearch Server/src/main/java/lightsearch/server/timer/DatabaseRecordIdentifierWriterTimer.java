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

package lightsearch.server.timer;

import lightsearch.server.data.LightSearchServerService;
import lightsearch.server.exception.IdentifierException;
import lightsearch.server.identifier.DatabaseRecordIdentifier;
import lightsearch.server.log.LoggerServer;
import lightsearch.server.producer.identifier.DatabaseRecordIdentifierProducer;
import lightsearch.server.producer.identifier.DatabaseRecordIdentifierWriterProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static lightsearch.server.log.LogMessageTypeEnum.ERROR;
import static lightsearch.server.log.LogMessageTypeEnum.INFO;

@Component("databaseRecordIdentifierWriterTimer")
@EnableAsync
public class DatabaseRecordIdentifierWriterTimer {

    @Autowired private LoggerServer logger;
    @Autowired private DatabaseRecordIdentifierWriterProducer identifierWriterProducer;
    @Autowired private DatabaseRecordIdentifierProducer identifierProducer;
    @Autowired private LightSearchServerService serverService;

    @Async
    @Scheduled(fixedDelay = 1800000, initialDelay = 30000) // 30 minutes, 30 seconds
    public void writeDatabaseRecordIdentifier() {
        try {
            DatabaseRecordIdentifier identifier = identifierProducer.getDatabaseRecordIdentifierDefaultInstance();

            identifierWriterProducer.getDatabaseRecordIdentifierWriterDefaultInstance(serverService)
                    .write(identifier.databaseRecordIdentifier());

            logger.log(DatabaseRecordIdentifierWriterTimer.class, INFO, "Database record identifier write. Value: " +
                    identifier.databaseRecordIdentifier());
        } catch (IdentifierException ex) {
            logger.log(DatabaseRecordIdentifierWriterTimer.class, ERROR, "Cannot write database record identifier. " +
                    "Exception: " + ex.getMessage());
        }
    }
}
