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
import lightsearch.server.database.cmd.message.DatabaseCommandMessageInit;
import lightsearch.server.database.statement.DatabaseStatementExecutor;
import lightsearch.server.database.statement.DatabaseStatementExecutorInit;
import lightsearch.server.exception.DatabaseStatementExecutorException;
import lightsearch.server.database.statement.result.DatabaseStatementResult;
import lightsearch.server.identifier.DatabaseRecordIdentifier;
import lightsearch.server.log.LogMessageTypeEnum;
import lightsearch.server.message.result.ResultTypeMessageEnum;
import lightsearch.server.time.CurrentDateTime;

/**
 * Обработчик команды отмены мягкого чека.
 * @author ViiSE
 * @since 2.0
 */
public class CancelSoftCheckProcessor extends AbstractProcessorClient {
    
    private final ClientDAO clientDAO;
    private final CurrentDateTime currentDateTime;
    private final DatabaseRecordIdentifier databaseRecordIdentifier;
    
    public CancelSoftCheckProcessor(LightSearchServerDTO serverDTO, LightSearchChecker checker,
            ClientDAO clientDAO, CurrentDateTime currentDateTime, 
            DatabaseRecordIdentifier databaseRecordIdentifier) {
        super(serverDTO, checker);
        this.clientDAO = clientDAO;
        this.currentDateTime = currentDateTime;
        this.databaseRecordIdentifier = databaseRecordIdentifier;
    }
    
    @Override
    public CommandResult apply(ClientCommand clientCommand) {
        if(!super.checker.isNull(clientCommand.IMEI(), clientCommand.userIdentifier(), 
                clientCommand.cardCode())) {
            if(!serverDTO.blacklist().contains(clientCommand.IMEI())) {
                try {
                    DatabaseCommandMessage dbCmdMessage = DatabaseCommandMessageInit.databaseCommandMessageCancelSoftCheck(
                            clientCommand.command(), clientCommand.IMEI(), 
                            clientCommand.userIdentifier(), clientCommand.cardCode());
                    
                    DatabaseStatementExecutor dbStatementExecutor = DatabaseStatementExecutorInit.databaseStatementExecutor(
                            clientDAO.databaseConnection(), databaseRecordIdentifier.next(),
                            currentDateTime.dateTimeInStandardFormat(), dbCmdMessage);
                    DatabaseStatementResult dbStatRes = dbStatementExecutor.exec();

                    String logMessage = "Client " + clientCommand.IMEI() + 
                            " cancel SoftCheck, user ident - " + clientCommand.userIdentifier()+
                            ", card code - " + clientCommand.cardCode();
                    
                    String result = dbStatRes.result();

                    return super.commandResult(clientCommand.IMEI(), LogMessageTypeEnum.INFO, ResultTypeMessageEnum.TRUE,
                            result, logMessage);        
                } catch(DatabaseStatementExecutorException ex) {
                    return super.commandResult(clientCommand.IMEI(), LogMessageTypeEnum.ERROR, ResultTypeMessageEnum.FALSE, 
                            ex.getMessageRU(), "Client " + clientCommand.IMEI() + " " + ex.getMessage());
                }    
            }
            else
                return super.commandResult(clientCommand.IMEI(), LogMessageTypeEnum.ERROR, ResultTypeMessageEnum.FALSE,
                        "Извините, но вы находитесь в черном списке. Отключение от сервера", null);
        }
        else
            return super.commandResult("Unknown", LogMessageTypeEnum.ERROR, ResultTypeMessageEnum.FALSE,
                    "Неверный формат команды. Обратитесь к администратору для устранения ошибки. Вы были отключены от сервера", null);
    }
}
