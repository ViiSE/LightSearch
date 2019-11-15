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
                (CurrentServerDirectory) ctx.getBean("currentServerDirectoryFromFile");

//        AdminsService adminsService = ctx.getBean("adminsServiceDefault", AdminsService.class);
//        AdminsCreator adminsCreator = (AdminsCreator) ctx.getBean(
//                "adminsCreatorFromFile", currentServerDirectory, adminsService);
//        adminsCreator.createAdmins();

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

        serverService.clientsService().addClient("111111111111111", new Client("111111111111111", "client 1"));
        serverService.clientsService().addClient("222222222222222", new Client("222222222222222", "client 2"));
        serverService.clientsService().addClient("333333333333333", new Client("333333333333333", "client 3"));
        serverService.clientsService().addClient("444444444444444", new Client("444444444444444", "client 4"));
        serverService.clientsService().addClient("555555555555555", new Client("555555555555555", "client 5"));
        serverService.clientsService().addClient("666666666666666", new Client("666666666666666", "client 6"));
        serverService.clientsService().addClient("777777777777777", new Client("777777777777777", "client 7"));
        serverService.clientsService().addClient("888888888888888", new Client("888888888888888", "client 8"));
        serverService.clientsService().addClient("999999999999999", new Client("999999999999999", "client 9"));
        serverService.clientsService().addClient("111", new Client("111", "client 10"));
        serverService.clientsService().addClient("222", new Client("222", "client 11"));
        serverService.clientsService().addClient("333", new Client("333", "client 12"));
        serverService.clientsService().addClient("444", new Client("444", "client 13"));
        serverService.clientsService().addClient("555", new Client("555", "client 14"));
        serverService.clientsService().addClient("666", new Client("666", "client 15"));
        serverService.clientsService().addClient("777", new Client("777", "client 16"));
        serverService.clientsService().addClient("888", new Client("888", "client 17"));
        serverService.clientsService().addClient("999", new Client("999", "client 18"));
        serverService.clientsService().addClient("11", new Client("11", "client 19"));
        serverService.clientsService().addClient("22", new Client("22", "client 20"));
        serverService.clientsService().addClient("33", new Client("33", "client 21"));
        serverService.clientsService().addClient("44", new Client("44", "client 22"));
        serverService.clientsService().addClient("55", new Client("55", "client 23"));
        serverService.clientsService().addClient("66", new Client("66", "client 24"));
        serverService.clientsService().addClient("77", new Client("77", "client 25"));
        serverService.clientsService().addClient("88", new Client("88", "client 26"));
        serverService.clientsService().addClient("99", new Client("99", "client 27"));

        logger.log(LightSearchServer.class, INFO, "Database record identifier read. Value: " +
                identifier.databaseRecordIdentifier());

        // FIXME: 31.10.2019 MAYBE IN FUTURE
        // ServerSettingsCreator settingsCreator = (ServerSettingsCreator) ctx.getBean(
        // "serverSettingsCreatorFromFileJSON", currentServerDirectory);
        // settingsCreator.createSettings();

        System.out.println(ctx.getBean("endStartupMessageDefault", EndStartupMessage.class).message());
    }
}
