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

package lightsearch.server.cmd.client.processor;

import lightsearch.server.checker.LightSearchChecker;
import lightsearch.server.cmd.client.ClientCommand;
import lightsearch.server.cmd.result.ClientCommandResultCreator;
import lightsearch.server.data.LightSearchServerService;
import lightsearch.server.data.pojo.ClientCommandResult;
import lightsearch.server.database.cmd.message.DatabaseCommandMessage;
import lightsearch.server.database.statement.DatabaseStatementExecutor;
import lightsearch.server.database.statement.result.DatabaseStatementResult;
import lightsearch.server.exception.CheckerException;
import lightsearch.server.exception.CommandResultException;
import lightsearch.server.exception.DatabaseStatementExecutorException;
import lightsearch.server.identifier.DatabaseRecordIdentifier;
import lightsearch.server.log.LoggerServer;
import lightsearch.server.producer.checker.CommandCheckerProducer;
import lightsearch.server.producer.cmd.client.processor.ErrorClientCommandServiceProducer;
import lightsearch.server.producer.cmd.result.ClientCommandResultCreatorProducer;
import lightsearch.server.producer.database.DatabaseCommandMessageProducer;
import lightsearch.server.producer.database.DatabaseStatementExecutorProducer;
import lightsearch.server.time.CurrentDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static lightsearch.server.log.LogMessageTypeEnum.INFO;

@Component("openSoftCheckProcessor")
@Scope("prototype")
public class OpenSoftCheckProcessor implements ClientProcessor<ClientCommandResult> {

    private final LightSearchServerService serverService;
    private final LightSearchChecker checker;
    private final CurrentDateTime currentDateTime;
    private final DatabaseRecordIdentifier databaseRecordIdentifier;

    @Autowired private LoggerServer logger;
    @Autowired private ClientCommandResultCreatorProducer clientCommandResultCreatorProducer;
    @Autowired private DatabaseCommandMessageProducer dbCmdMsgProducer;
    @Autowired private DatabaseStatementExecutorProducer dbStateExecProducer;
    @Autowired private CommandCheckerProducer commandCheckerProducer;
    @Autowired private ErrorClientCommandServiceProducer errCmdServiceProducer;

    public OpenSoftCheckProcessor(
            LightSearchServerService serverService, LightSearchChecker checker, CurrentDateTime currentDateTime,
            DatabaseRecordIdentifier databaseRecordIdentifier) {
        this.serverService = serverService;
        this.checker = checker;
        this.currentDateTime = currentDateTime;
        this.databaseRecordIdentifier = databaseRecordIdentifier;
    }

    @SuppressWarnings("unchecked")
    @Override
    public ClientCommandResult apply(ClientCommand command) {
        try {
            commandCheckerProducer.getCommandCheckerClientOpenSoftCheckInstance(command, serverService, checker).check();
            serverService.clientsService().refreshTimeout(command.IMEI());

            DatabaseCommandMessage dbCmdMessage =
                    dbCmdMsgProducer.getDatabaseCommandMessageOpenSoftCheckDefaultWindowsJSONInstance(command);
            DatabaseStatementExecutor dbStatementExecutor =
                    dbStateExecProducer.getDatabaseStatementExecutorDefaultInstance(
                            databaseRecordIdentifier.next(), currentDateTime.dateTimeInStandardFormat(), dbCmdMessage);
            DatabaseStatementResult dbStatRes = dbStatementExecutor.exec();

            String result = dbStatRes.result();
            ClientCommandResultCreator commandResultCreator =
                    clientCommandResultCreatorProducer.getCommandResultCreatorClientJSONInstance(result);
            logger.log(OpenSoftCheckProcessor.class, INFO, "Client " + command.IMEI() + " open soft check: " +
                    "user identifier - " + command.userIdentifier());

            return commandResultCreator.createClientCommandResult();
        } catch (CommandResultException | DatabaseStatementExecutorException ex) {
            return errCmdServiceProducer.getErrorClientCommandServiceDefaultInstance().createErrorResult(
                    command.IMEI(), "Невозможно создать результат команды. Сообщение: " + ex.getMessage(), ex.getMessage());
        } catch (CheckerException ex) {
            return errCmdServiceProducer.getErrorClientCommandServiceDefaultInstance().createErrorResult(
                    command.IMEI() == null ? "Unknown" : command.IMEI(), ex.getMessage(), ex.getLogMessage());
        }
    }
}
