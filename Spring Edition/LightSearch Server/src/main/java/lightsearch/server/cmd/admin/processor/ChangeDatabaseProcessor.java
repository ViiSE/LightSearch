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
import lightsearch.server.data.pojo.AdminCommandResult;
import lightsearch.server.data.pojo.Property;
import lightsearch.server.exception.CheckerException;
import lightsearch.server.exception.CommandResultException;
import lightsearch.server.exception.PropertiesException;
import lightsearch.server.exception.WriterException;
import lightsearch.server.log.LoggerServer;
import lightsearch.server.producer.checker.CommandCheckerProducer;
import lightsearch.server.producer.cmd.admin.ErrorAdminCommandServiceProducer;
import lightsearch.server.producer.cmd.result.AdminCommandResultCreatorProducer;
import lightsearch.server.producer.properties.PropertiesLocalChangerProducer;
import lightsearch.server.producer.properties.PropertiesReaderProducer;
import lightsearch.server.producer.properties.PropertiesWriterProducer;
import lightsearch.server.properties.PropertiesReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static lightsearch.server.log.LogMessageTypeEnum.INFO;

/**
 *
 * @author ViiSE
 */
@Component("changeDatabaseProcessor")
@Scope("prototype")
public class ChangeDatabaseProcessor implements AdminProcessor<AdminCommandResult> {

    private final LightSearchChecker checker;
    private final String propsDir;

    @Autowired private LoggerServer logger;
    @Autowired private ErrorAdminCommandServiceProducer errAdmCmdServiceProducer;
    @Autowired private CommandCheckerProducer cmdCheckerProducer;
    @Autowired private AdminCommandResultCreatorProducer admCmdResCrProducer;
    @Autowired private PropertiesLocalChangerProducer propsLocalChProducer;
    @Autowired private PropertiesReaderProducer propsReaderProducer;
    @Autowired private PropertiesWriterProducer propsWriterProducer;

    public ChangeDatabaseProcessor(LightSearchChecker checker, String currentDirectory) {
        this.checker = checker;
        this.propsDir = currentDirectory + "config/application.properties";
    }

    @Override
    synchronized public AdminCommandResult apply(AdminCommand command) {
        try {
            cmdCheckerProducer.getCommandCheckerAdminChangeDatabaseInstance(command, checker).check();

            // FIXME: 31.10.2019 NEED UNIVERSAL METHOD FOR ALL CLASS, BUT NEED ALSO LS_REQUEST REPOSITORY MAKE UNIVERSAL (FOR ALL DATABASES)
            // FIXME: 31.10.2019 NEED HIBERNATE?
            Map<String, Property> propsMap = new HashMap<>() {{
                put("spring.datasource.url", new Property("%s=%s%s:%s%s%s",
                        "jdbc:firebirdsql://", command.ip(), command.port(), command.dbName(),
                        "?encoding=win1251&amp;useUnicode=true&amp;characterEncoding=win1251"));
                put("spring.datasource.username", new Property("%s=%s", command.username()));
                put("spring.datasource.password", new Property("%s=%s", command.password()));
            }};
            PropertiesReader<List<String>> propsReader = propsReaderProducer.getPropertiesListStringReaderInstance(propsDir);
            List<String> chPropsList = propsLocalChProducer.getPropertiesLocalChangerDefaultInstance(propsMap, propsReader)
                    .getChangedProperties();
            propsWriterProducer.getPropertiesFileWriterInstance(propsDir, chPropsList, false).write();
            AdminCommandResultCreator commandResultCreator =
                    admCmdResCrProducer.getCommandResultCreatorAdminDefaultInstance(ResultType.TRUE.stringValue(),
                            "Параметры базы данных были изменены. Для вступления в силу изменений перезагрузите сервер.", null, null);
            logger.log(ChangeDatabaseProcessor.class, INFO, "Datasource has been changed");
            return commandResultCreator.createAdminCommandResult();
        } catch (PropertiesException | WriterException | CheckerException ex) {
            return errAdmCmdServiceProducer.getErrorAdminCommandServiceDefaultInstance()
                    .createErrorResult(ex.getMessage(), ex.getLogMessage());
        } catch (CommandResultException ex) {
            return errAdmCmdServiceProducer.getErrorAdminCommandServiceDefaultInstance()
                    .createErrorResult(ex.getMessage(), ex.getMessage());
        }
    }
}
