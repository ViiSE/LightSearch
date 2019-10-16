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
import lightsearch.server.data.BlacklistService;
import lightsearch.server.data.ClientsService;
import lightsearch.server.data.LightSearchServerService;
import lightsearch.server.data.pojo.ClientCommandResult;
import lightsearch.server.database.cmd.message.DatabaseCommandMessage;
import lightsearch.server.database.statement.DatabaseStatementExecutor;
import lightsearch.server.database.statement.result.DatabaseStatementResult;
import lightsearch.server.exception.CommandResultException;
import lightsearch.server.exception.DatabaseStatementExecutorException;
import lightsearch.server.identifier.DatabaseRecordIdentifier;
import lightsearch.server.log.LoggerServer;
import lightsearch.server.producer.cmd.result.CommandResultCreatorProducer;
import lightsearch.server.producer.database.DatabaseCommandMessageProducer;
import lightsearch.server.producer.database.DatabaseStatementExecutorProducer;
import lightsearch.server.time.CurrentDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static lightsearch.server.log.LogMessageTypeEnum.ERROR;
import static lightsearch.server.log.LogMessageTypeEnum.INFO;

@Component("authenticationProcessorClient")
@Scope("prototype")
public class AuthenticationProcessor implements ClientProcessor<ClientCommandResult> {

    private final ClientsService<String, String> clientsService;
    private final BlacklistService blacklistService;
    private final LightSearchChecker checker;
    private final CurrentDateTime currentDateTime;
    private final DatabaseRecordIdentifier databaseRecordIdentifier;

    @Autowired private LoggerServer logger;
    @Autowired private CommandResultCreatorProducer commandResultCreatorProducer;
    @Autowired private DatabaseCommandMessageProducer dbCmdMsgProducer;
    @Autowired private DatabaseStatementExecutorProducer dbStateExecProducer;

    @SuppressWarnings("unchecked")
    public AuthenticationProcessor(
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
        if(!checker.isNull(command.username(), command.password(), command.IMEI(), command.ip(), command.os(),
                command.model(), command.userIdentifier())) {
            if(!blacklistService.blacklist().contains(command.IMEI())) {
                try {
                    DatabaseCommandMessage dbCmdMessage =
                            dbCmdMsgProducer.getDatabaseCommandMessageConnectionDefaultWindowsJSONInstance(command);

                    DatabaseStatementExecutor dbStatementExecutor =
                            dbStateExecProducer.getDatabaseStatementExecutorDefaultInstance(
                                    databaseRecordIdentifier.next(),
                                    currentDateTime.dateTimeInStandardFormat(), dbCmdMessage);

                    DatabaseStatementResult dbStatRes = dbStatementExecutor.exec();

                    String message = "IMEI - " + command.IMEI()
                        + ", ip - "            + command.ip()
                        + ", os - "            + command.os()
                        + ", model - "         + command.model()
                        + ", username - "      + command.username()
                        + ", user ident - "    + command.userIdentifier();

                    String result = dbStatRes.result();

                    ClientCommandResultCreator commandResultCreator =
                            commandResultCreatorProducer.getCommandResultCreatorClientJSONInstance(result);
                    logger.log(INFO, currentDateTime, "Client connected:\n" + message);
                    clientsService.clients().put(command.IMEI(), command.username());
                    return commandResultCreator.createClientCommandResult();
                } catch (CommandResultException | DatabaseStatementExecutorException ex) {
                    logger.log(ERROR, currentDateTime, ex.getMessage());
                    return createErrorResult(command.IMEI(), "Невозможно создать результат команды. " +
                            "Обратитесь к администратору для устранения проблемы.");
                }
            } else {
                logger.log(ERROR, currentDateTime, "Client " + command.IMEI() + " in the blacklist.");
                return createErrorResult(command.IMEI(), "Извините, но вы находитесь в черном списке.");
            }
        } else {
            logger.log(ERROR, currentDateTime, "Authentication: unknown client.");
            return createErrorResult("Unknown", "Неверный формат команды. " +
                    "Обратитесь к администратору для устранения ошибки.");
        }
    }

    private ClientCommandResult createErrorResult(String IMEI, String message) {
        ClientCommandResultCreator commandResultCreatorError =
                commandResultCreatorProducer.getCommandResultCreatorClientErrorInstance(IMEI, message);
        try {
            return commandResultCreatorError.createClientCommandResult();
        } catch (CommandResultException ignore) {
            // never happened
            return null;
        }
    }
}
