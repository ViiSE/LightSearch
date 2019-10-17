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
import lightsearch.server.identifier.DatabaseRecordIdentifierReader;
import lightsearch.server.identifier.DatabaseRecordIdentifierWriter;
import lightsearch.server.log.LogMessageTypeEnum;
import lightsearch.server.log.LoggerServer;
import lightsearch.server.producer.identifier.DatabaseRecordIdentifierProducer;
import lightsearch.server.producer.identifier.DatabaseRecordIdentifierReaderProducer;
import lightsearch.server.time.CurrentDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component("databaseRecordIdentifierWriterTimer")
@EnableAsync
public class DatabaseRecordIdentifierWriterTimer {

    private boolean isRead = false;
    private DatabaseRecordIdentifier identifier;

    @Autowired private LoggerServer logger;
    @Autowired private CurrentDateTime currentDateTime;
    @Autowired private LightSearchServerService serverService;
    @Autowired private DatabaseRecordIdentifierReaderProducer identifierReaderProducer;
    @Autowired private DatabaseRecordIdentifierProducer identifierProducer;
    @Autowired private DatabaseRecordIdentifierWriter identifierWriter;

    @Async
    @Scheduled(fixedRate = 600000)
    public void writeDatabaseRecordIdentifier() {
        if(!isRead)
            read();

        try {
            identifierWriter.write(identifier.databaseRecordIdentifier());
        } catch (IdentifierException ex) {
            logger.log(LogMessageTypeEnum.ERROR, currentDateTime, "Cannot write database record identifier. " +
                    "Exception: " + ex.getMessage());
        }
    }

    private void read() {
        DatabaseRecordIdentifierReader identifierReader =
                identifierReaderProducer.getDatabaseRecordIdentifierReaderDefaultInstance(serverService);
        identifier = identifierProducer.getDatabaseRecordIdentifierDefaultInstance(identifierReader.read());

        logger.log(LogMessageTypeEnum.INFO, currentDateTime, "DatabaseRecordIdentifier read. Value: " +
                identifier.databaseRecordIdentifier());

        isRead = true;
    }
}
