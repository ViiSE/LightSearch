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

package lightsearch.server;

import lightsearch.server.data.AdminsService;
import lightsearch.server.data.BlacklistService;
import lightsearch.server.data.LightSearchServerService;
import lightsearch.server.identifier.DatabaseRecordIdentifier;
import lightsearch.server.identifier.DatabaseRecordIdentifierReader;
import lightsearch.server.identifier.DatabaseRecordIdentifierWriter;
import lightsearch.server.initialization.AdminsCreator;
import lightsearch.server.initialization.BlacklistCreator;
import lightsearch.server.initialization.CurrentServerDirectory;
import lightsearch.server.initialization.OsDetector;
import lightsearch.server.log.LogDirectory;
import lightsearch.server.log.LogMessageTypeEnum;
import lightsearch.server.log.LoggerServer;
import lightsearch.server.producer.identifier.DatabaseRecordIdentifierProducer;
import lightsearch.server.producer.identifier.DatabaseRecordIdentifierReaderProducer;
import lightsearch.server.time.CurrentDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class LightSearchServer {

    @Autowired private LightSearchServerService serverService;
    @Autowired private DatabaseRecordIdentifierReaderProducer identifierReaderProducer;
    @Autowired private DatabaseRecordIdentifierProducer identifierProducer;
    @Autowired private DatabaseRecordIdentifierWriter identifierWriter;

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(LightSearchServer.class, args);

        OsDetector osDetector = ctx.getBean("osDetectorDefault", OsDetector.class);
        CurrentServerDirectory currentServerDirectory =
                (CurrentServerDirectory) ctx.getBean("currentServerDirectoryFromFile", osDetector);

        AdminsService adminsService = ctx.getBean("adminsServiceDefault", AdminsService.class);
        AdminsCreator adminsCreator = (AdminsCreator) ctx.getBean(
                "adminsCreatorFromFile", currentServerDirectory, adminsService);
        adminsCreator.createAdmins();

        BlacklistService blacklistService = ctx.getBean("blacklistServiceDefault", BlacklistService.class);
        BlacklistCreator blacklistCreator =
                (BlacklistCreator) ctx.getBean("blacklistCreatorFromFile", currentServerDirectory, blacklistService);
        blacklistCreator.createBlacklist();

        LoggerServer logger = ctx.getBean("loggerServerDefault", LoggerServer.class);
        CurrentDateTime currentDateTime = ctx.getBean("currentDateTimeDefault", CurrentDateTime.class);
        LightSearchServerService serverService = ctx.getBean("lightSearchServerServiceDefault", LightSearchServerService.class);

        DatabaseRecordIdentifierReader identifierReader = (DatabaseRecordIdentifierReader)
                ctx.getBean("databaseRecordIdentifierReaderDefault", serverService);
        DatabaseRecordIdentifier identifier = (DatabaseRecordIdentifier)
                ctx.getBean("databaseRecordIdentifierDefault", identifierReader.read());

        logger.log(LogMessageTypeEnum.INFO, currentDateTime, "DatabaseRecordIdentifier read. Value: " +
                identifier.databaseRecordIdentifier());

        ctx.getBean("logDirectoryDefault", "logs", osDetector, currentServerDirectory);
    }
}
