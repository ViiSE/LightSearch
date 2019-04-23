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
import lightsearch.server.database.cmd.message.DatabaseCommandMessage;
import lightsearch.server.database.cmd.message.DatabaseCommandMessageInit;
import lightsearch.server.database.statement.DatabaseStatementExecutor;
import lightsearch.server.database.statement.DatabaseStatementExecutorInit;
import lightsearch.server.exception.DatabaseStatementExecutorException;
import lightsearch.server.database.statement.result.DatabaseStatementResult;
import lightsearch.server.iterator.IteratorDatabaseRecord;
import lightsearch.server.time.CurrentDateTime;
import lightsearch.server.data.LightSearchServerDTO;
import lightsearch.server.log.LogMessageTypeEnum;
import lightsearch.server.message.result.ResultTypeMessageEnum;

/**
 *
 * @author ViiSE
 */
public class SearchProcessor extends AbstractProcessorClient {

    private final ClientDAO clientDAO;
    private final CurrentDateTime currentDateTime;
    private final IteratorDatabaseRecord iteratorDatabaseRecord;
    
    public SearchProcessor(LightSearchServerDTO serverDTO, LightSearchChecker checker,
            ClientDAO clientDAO, CurrentDateTime currentDateTime, 
            IteratorDatabaseRecord iteratorDatabaseRecord) {
        super(serverDTO, checker);
        this.clientDAO = clientDAO;
        this.currentDateTime = currentDateTime;
        this.iteratorDatabaseRecord = iteratorDatabaseRecord;
    }

    @Override
    public CommandResult apply(ClientCommand clientCommand) {
        if(!super.checker.isNull(clientCommand.IMEI(), clientCommand.barcode(), 
                clientCommand.sklad(), clientCommand.TK())) {
            if(!serverDTO.blacklist().contains(clientCommand.IMEI())) {
                try {
                    DatabaseCommandMessage dbCmdMessage = DatabaseCommandMessageInit.databaseCommandMessageSearch(
                            clientCommand.command(), clientCommand.IMEI(), 
                            clientCommand.barcode(), clientCommand.sklad(), clientCommand.TK());
                    
                    DatabaseStatementExecutor dbStatementExecutor = DatabaseStatementExecutorInit.databaseStatementExecutor(
                            clientDAO.databaseConnection(), iteratorDatabaseRecord.next(), 
                            currentDateTime.dateTimeInStandartFormat(), dbCmdMessage);
                    DatabaseStatementResult dbStatRes = dbStatementExecutor.exec();

                    String result = dbStatRes.result();

                    return super.commandResult(clientCommand.IMEI(), LogMessageTypeEnum.INFO, ResultTypeMessageEnum.TRUE, 
                            result, "");        
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
