/* 
 * Copyright 2019 ViiSE.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package lightsearch.server.cmd.client.processor;

import lightsearch.server.checker.LightSearchChecker;
import lightsearch.server.cmd.client.ClientCommand;
import lightsearch.server.cmd.result.CommandResult;
import lightsearch.server.data.ClientDAO;
import lightsearch.server.data.LightSearchServerDTO;
import lightsearch.server.database.cmd.message.DatabaseCommandMessage;
import lightsearch.server.database.statement.DatabaseStatementExecutor;
import lightsearch.server.database.statement.result.DatabaseStatementResult;
import lightsearch.server.exception.DatabaseStatementExecutorException;
import lightsearch.server.identifier.DatabaseRecordIdentifier;
import lightsearch.server.log.LogMessageTypeEnum;
import lightsearch.server.message.result.ResultTypeMessageEnum;
import lightsearch.server.producer.cmd.client.CommandResultClientCreatorProducer;
import lightsearch.server.producer.database.DatabaseCommandMessageProducer;
import lightsearch.server.producer.database.DatabaseConnectionCreatorProducer;
import lightsearch.server.producer.database.DatabaseStatementExecutorProducer;
import lightsearch.server.time.CurrentDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *
 * @author ViiSE
 */
@Component("closeSoftCheckProcessorClient")
@Scope("prototype")
public class CloseSoftCheckProcessor implements ProcessorClient {

    private final LightSearchChecker checker;
    private final List<String> blacklist;
    private final ClientDAO clientDAO;
    private final CurrentDateTime currentDateTime;
    private final DatabaseRecordIdentifier databaseRecordIdentifier;

    @Autowired private CommandResultClientCreatorProducer cmdResClCrProducer;
    @Autowired private DatabaseConnectionCreatorProducer dbConnCrProducer;
    @Autowired private DatabaseCommandMessageProducer dbCmdMsgProducer;
    @Autowired private DatabaseStatementExecutorProducer dbStateExecProducer;

    public CloseSoftCheckProcessor(LightSearchServerDTO serverDTO, LightSearchChecker checker, ClientDAO clientDAO,
                                   CurrentDateTime currentDateTime, DatabaseRecordIdentifier databaseRecordIdentifier) {
        this.checker = checker;
        this.blacklist = serverDTO.blacklist();
        this.clientDAO = clientDAO;
        this.currentDateTime = currentDateTime;
        this.databaseRecordIdentifier = databaseRecordIdentifier;
    }
    
    @Override
    public CommandResult apply(ClientCommand clientCommand) {
        if(!checker.isNull(clientCommand.IMEI(), clientCommand.userIdentifier(),
                clientCommand.cardCode(), clientCommand.delivery())) {
            if(!blacklist.contains(clientCommand.IMEI())) {
                try {
                    DatabaseCommandMessage dbCmdMessage = dbCmdMsgProducer.getDatabaseCommandMessageCloseSoftCheckDefaultWindowsJSONInstance(
                            clientCommand.command(), clientCommand.IMEI(),
                            clientCommand.userIdentifier(), clientCommand.cardCode(),
                            clientCommand.delivery());
                    
                    DatabaseStatementExecutor dbStatementExecutor = dbStateExecProducer.getDatabaseStatementExecutorDefaultInstance(
                            clientDAO.databaseConnection(), databaseRecordIdentifier.next(),
                            currentDateTime.dateTimeInStandartFormat(), dbCmdMessage);
                    DatabaseStatementResult dbStatRes = dbStatementExecutor.exec();

                    String logMessage = "Client " + clientCommand.IMEI() + " close SoftCheck, "
                            + "user ident - " + clientCommand.userIdentifier()
                            + ", card code - " + clientCommand.cardCode();
                    
                    String result = dbStatRes.result();

                    return commandResult(clientCommand.IMEI(), LogMessageTypeEnum.INFO, ResultTypeMessageEnum.TRUE,
                            result, logMessage);        
                } catch(DatabaseStatementExecutorException ex) {
                    return commandResult(clientCommand.IMEI(), LogMessageTypeEnum.ERROR, ResultTypeMessageEnum.FALSE,
                            ex.getMessageRU(), "Client " + clientCommand.IMEI() + " " + ex.getMessage());
                }    
            }
            else
                return commandResult(clientCommand.IMEI(), LogMessageTypeEnum.ERROR, ResultTypeMessageEnum.FALSE,
                        "Извините, но вы находитесь в черном списке. Отключение от сервера", null);
        }
        else
            return commandResult("Unknown", LogMessageTypeEnum.ERROR, ResultTypeMessageEnum.FALSE,
                    "Неверный формат команды. Обратитесь к администратору для устранения ошибки. Вы были отключены от сервера", null);
    }

    private CommandResult commandResult(String name, LogMessageTypeEnum type, ResultTypeMessageEnum resultValue, Object message, String logMessage) {
        return cmdResClCrProducer.getCommandResultClientCreatorDefaultInstance(name, type, resultValue, message, logMessage)
                .createCommandResult();
    }
}
