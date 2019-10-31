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
@Component("clientTimeoutProcessor")
@Scope("prototype")
public class ClientTimeoutProcessor implements AdminProcessor<AdminCommandResult> {

    private final String propsDir;

    @Autowired private LoggerServer logger;
    @Autowired private ErrorAdminCommandServiceProducer errAdmCmdServiceProducer;
    @Autowired private CommandCheckerProducer cmdCheckerProducer;
    @Autowired private AdminCommandResultCreatorProducer admCmdResCrProducer;
    @Autowired private PropertiesLocalChangerProducer propsLocalChProducer;
    @Autowired private PropertiesReaderProducer propsReaderProducer;
    @Autowired private PropertiesWriterProducer propsWriterProducer;

    public ClientTimeoutProcessor(String currentDirectory) {
        this.propsDir = currentDirectory + "config/application.properties";
    }

    @Override
    synchronized public AdminCommandResult apply(AdminCommand command) {
        try {
            cmdCheckerProducer.getCommandCheckerAdminClientTimeoutInstance(command).check();

            Map<String, Property> propsMap = new HashMap<>() {{
                put("lightsearch.server.settings.timeout.client-timeout", new Property("%s=%s", command.clientTimeout()));
            }};
            PropertiesReader<List<String>> propsReader = propsReaderProducer.getPropertiesListStringReaderInstance(propsDir);
            List<String> chPropsList = propsLocalChProducer.getPropertiesLocalChangerDefaultInstance(propsMap, propsReader)
                    .getChangedProperties();
            propsWriterProducer.getPropertiesFileWriterInstance(propsDir, chPropsList, false).write();

            AdminCommandResultCreator commandResultCreator =
                    admCmdResCrProducer.getCommandResultCreatorAdminDefaultInstance(
                            ResultType.TRUE.stringValue(), "Значение тайм-аута клиента было изменено. " +
                                    "Для вступления в силу изменений перезагрузите сервер.", null, null);
            logger.log(ClientTimeoutProcessor.class, INFO, "Client timeout has been changed");
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
