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

import lightsearch.server.about.AppGreetings;
import lightsearch.server.about.EndStartupMessage;
import lightsearch.server.data.AdminsService;
import lightsearch.server.data.BlacklistService;
import lightsearch.server.data.LightSearchServerService;
import lightsearch.server.data.pojo.Client;
import lightsearch.server.identifier.DatabaseRecordIdentifier;
import lightsearch.server.identifier.DatabaseRecordIdentifierReader;
import lightsearch.server.initialization.*;
import lightsearch.server.log.LoggerServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import static lightsearch.server.log.LogMessageTypeEnum.INFO;

@SpringBootApplication
public class LightSearchServer {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(LightSearchServer.class, args);

        System.out.println(ctx.getBean("appGreetingsDefault", AppGreetings.class).greetings());

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

        ctx.getBean("logDirectoryDefault", "logs", osDetector, currentServerDirectory);

        LoggerServer logger = ctx.getBean("loggerServerDefault", LoggerServer.class);
        LightSearchServerService serverService = ctx.getBean("lightSearchServerServiceDefault", LightSearchServerService.class);
        
        DatabaseRecordIdentifierReader identifierReader = (DatabaseRecordIdentifierReader)
                ctx.getBean("databaseRecordIdentifierReaderDefault", serverService);
        DatabaseRecordIdentifier identifier = (DatabaseRecordIdentifier)
                ctx.getBean("databaseRecordIdentifierDefault", identifierReader.read());

        logger.log(LightSearchServer.class, INFO, "Database record identifier read. Value: " +
                identifier.databaseRecordIdentifier());

        // FIXME: 31.10.2019 MAYBE IN FUTURE
//        ServerSettingsCreator settingsCreator = (ServerSettingsCreator) ctx.getBean(
//                "serverSettingsCreatorFromFileJSON", currentServerDirectory);
//        settingsCreator.createSettings();

        System.out.println(ctx.getBean("endStartupMessageDefault", EndStartupMessage.class).message());
    }
}
