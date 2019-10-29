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
package lightsearch.server.cmd.admin.processor;

import lightsearch.server.checker.LightSearchChecker;
import lightsearch.server.cmd.admin.AdminCommand;
import lightsearch.server.cmd.result.AdminCommandResultCreator;
import lightsearch.server.cmd.result.ResultType;
import lightsearch.server.data.LightSearchServerService;
import lightsearch.server.data.pojo.AdminCommandResult;
import lightsearch.server.exception.CheckerException;
import lightsearch.server.exception.CommandResultException;
import lightsearch.server.initialization.CurrentServerDirectory;
import lightsearch.server.log.LoggerServer;
import lightsearch.server.producer.checker.CommandCheckerProducer;
import lightsearch.server.producer.cmd.admin.ErrorAdminCommandServiceProducer;
import lightsearch.server.producer.cmd.result.AdminCommandResultCreatorProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static lightsearch.server.log.LogMessageTypeEnum.INFO;

/**
 *
 * @author ViiSE
 */
@Component("changeDatabaseProcessor")
@Scope("prototype")
public class ChangeDatabaseProcessor implements AdminProcessor<AdminCommandResult> {

    private final LightSearchChecker checker;
    private final String applicationPropertiesDirectory;

    @Autowired private LoggerServer logger;
    @Autowired private ErrorAdminCommandServiceProducer errAdmCmdServiceProducer;
    @Autowired private CommandCheckerProducer cmdCheckerProducer;
    @Autowired private AdminCommandResultCreatorProducer admCmdResCrProducer;

    public ChangeDatabaseProcessor(LightSearchChecker checker, CurrentServerDirectory currentDirectory) {
        this.checker = checker;
        this.applicationPropertiesDirectory = currentDirectory.currentDirectory() + "config/application.properties";
    }

    @Override
    synchronized public AdminCommandResult apply(AdminCommand command) {
        try {
            cmdCheckerProducer.getCommandCheckerAdminChangeDatabaseInstance(command, checker).check();
            try (FileOutputStream fout = new FileOutputStream(applicationPropertiesDirectory, true);
                 BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fout))) {
                Path propsPath = Paths.get(applicationPropertiesDirectory);

                List<String> properties = Files.readAllLines(propsPath).stream().map(property -> {
                    String springDbUrlKey      = "spring.datasource.url";
                    String springDbUsernameKey = "spring.datasource.username";
                    String springDbPasswordKey = "spring.datasource.password";
                    if (property.contains(springDbUrlKey)) {
                        String urlDbType = "jdbc:firebirdsql://";
                        String urlProperties = "?encoding=win1251&amp;useUnicode=true&amp;characterEncoding=win1251";
                        return String.format("%s=%s%s:%s%s%s", springDbUrlKey, urlDbType, command.ip(), command.port(),
                                command.dbName(), urlProperties);
                    } else if(property.contains(springDbUsernameKey))
                        return String.format("%s=%s", springDbUsernameKey, command.username());
                    else if(property.contains(springDbPasswordKey))
                        return String.format("%s=%s", springDbPasswordKey, command.password());
                    else
                        return property;
                }).collect(Collectors.toList());

                for(String property: properties) {
                    bw.write(property);
                    bw.newLine();
                }
            } catch (IOException ex) {
                return errAdmCmdServiceProducer.getErrorAdminCommandServiceDefaultInstance()
                        .createErrorResult("Невозможно записать новые параметры в базу данных. Сообщение: " + ex.getMessage(),
                                "ChangeDatabaseProcessor: Cannot write datasource properties. Exception: " + ex.getMessage());
            }

            AdminCommandResultCreator commandResultCreator =
                    admCmdResCrProducer.getCommandResultCreatorAdminDefaultInstance(
                            ResultType.TRUE.stringValue(), "Параметры базы данных были изменены. Для вступления в силу изменений перезагрузите сервер.", null, null);
            logger.log(INFO, "Datasource has been changed");

            return commandResultCreator.createAdminCommandResult();
        } catch (CheckerException ex) {
            return errAdmCmdServiceProducer.getErrorAdminCommandServiceDefaultInstance()
                    .createErrorResult(ex.getMessage(), ex.getLogMessage());
        } catch (CommandResultException ex) {
            return errAdmCmdServiceProducer.getErrorAdminCommandServiceDefaultInstance()
                    .createErrorResult(ex.getMessage(), ex.getMessage());
        }
    }
}
