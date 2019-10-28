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

package lightsearch.server.cmd.client.processor.test;

import lightsearch.server.checker.LightSearchChecker;
import lightsearch.server.cmd.client.ClientCommand;
import lightsearch.server.cmd.client.processor.ClientProcessor;
import lightsearch.server.cmd.result.ClientCommandResultCreator;
import lightsearch.server.data.BlacklistService;
import lightsearch.server.data.ClientsService;
import lightsearch.server.data.LightSearchServerService;
import lightsearch.server.data.pojo.Client;
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

@Component("loginProcessorTest")
@Scope("prototype")
public class LoginProcessorTest implements ClientProcessor<ClientCommandResult> {

    private final ClientsService<String, Client> clientsService;
    private final BlacklistService blacklistService;
    private final LightSearchChecker checker;
    private final CurrentDateTime currentDateTime;
    private final DatabaseRecordIdentifier databaseRecordIdentifier;

    @Autowired private LoggerServer logger;
    @Autowired private ClientCommandResultCreatorProducer clientCommandResultCreatorProducer;
    @Autowired private DatabaseCommandMessageProducer dbCmdMsgProducer;
    @Autowired private DatabaseStatementExecutorProducer dbStateExecProducer;
    @Autowired private CommandCheckerProducer commandCheckerProducer;
    @Autowired private ErrorClientCommandServiceProducer errorCommandServiceProducer;

    @SuppressWarnings("unchecked")
    public LoginProcessorTest(
            LightSearchServerService serverService, LightSearchChecker checker, CurrentDateTime currentDateTime,
            DatabaseRecordIdentifier databaseRecordIdentifier) {
        this.clientsService = serverService.clientsService();
        this.blacklistService = serverService.blacklistService();
        this.checker = checker;
        this.currentDateTime = currentDateTime;
        this.databaseRecordIdentifier = databaseRecordIdentifier;
    }

    @Override
    public ClientCommandResult apply(ClientCommand command) {
        try {
            commandCheckerProducer.getCommandCheckerClientAuthorizationInstance(command, blacklistService, checker).check();

            DatabaseCommandMessage dbCmdMessage =
                    dbCmdMsgProducer.getDatabaseCommandMessageConnectionDefaultWindowsJSONInstance(command);

            DatabaseStatementExecutor dbStatementExecutor =
                    dbStateExecProducer.getDatabaseStatementExecutorH2TestInstance(
                            databaseRecordIdentifier.next(), currentDateTime.dateTimeInStandardFormat(), dbCmdMessage);

            DatabaseStatementResult dbStatRes = dbStatementExecutor.exec();

            ClientCommandResultCreator commandResultCreator =
                    clientCommandResultCreatorProducer.getCommandResultCreatorClientJSONInstance(dbStatRes.result());
            logger.log(INFO, "Client connected:\n" + "IMEI - " + command.IMEI() +
                    ", ip - " + command.ip() + ", os - " + command.os() + ", model - " + command.model() +
                    ", username - " + command.username() + ", user ident - " + command.userIdentifier());
            clientsService.clients().put(command.IMEI(), new Client(command.IMEI(), command.username()));
            return commandResultCreator.createClientCommandResult();
        } catch (CommandResultException | DatabaseStatementExecutorException ex) {
            return errorCommandServiceProducer.getErrorClientCommandServiceDefaultInstance().createErrorResult(command.IMEI(),
                    "Невозможно создать результат команды. Сообщение: " + ex.getMessage(), ex.getMessage());
        } catch (CheckerException ex) {
            return errorCommandServiceProducer.getErrorClientCommandServiceDefaultInstance().createErrorResult(
                    command.IMEI() == null ? "Unknown" : command.IMEI(), ex.getMessage(), ex.getLogMessage());
        }
    }
}
