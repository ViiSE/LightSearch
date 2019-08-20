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
package lightsearch.server.cmd.client.processor.debug;

import lightsearch.server.checker.LightSearchChecker;
import lightsearch.server.cmd.client.ClientCommand;
import lightsearch.server.cmd.client.processor.AbstractProcessorClient;
import lightsearch.server.cmd.result.CommandResult;
import lightsearch.server.data.ClientDAO;
import lightsearch.server.data.LightSearchServerDTO;
import lightsearch.server.log.LogMessageTypeEnum;
import lightsearch.server.message.result.ResultTypeMessageEnum;

/**
 *
 * @author ViiSE
 */
public class AuthenticationProcessorDebug extends AbstractProcessorClient {
    
    private final ClientDAO clientDAO;
    
    public AuthenticationProcessorDebug(LightSearchServerDTO serverDTO, 
            LightSearchChecker checker, ClientDAO clientDAO) {
        super(serverDTO, checker);
        this.clientDAO = clientDAO;
    }

    @Override
    public CommandResult apply(ClientCommand clientCommand) {
        if(!super.checker.isNull(clientCommand.username(), clientCommand.password(), 
                clientCommand.IMEI(), clientCommand.ip(), clientCommand.os(), 
                clientCommand.model(), clientCommand.userIdentifier())) {
            if(!serverDTO.blacklist().contains(clientCommand.IMEI())) {
                String ident;
                if(!clientCommand.username().equals("superAdmin")) {
                    if(clientCommand.userIdentifier().equals("0"))
                        return super.commandResult(clientCommand.IMEI(), LogMessageTypeEnum.ERROR, ResultTypeMessageEnum.FALSE, 
                        "Введите идентификатор пользователя!", null);
                    else
                        ident = clientCommand.userIdentifier();
                }
                else
                    ident = "007";
                
                String message = "IMEI - "  + clientCommand.IMEI()
                            + ", ip - "         + clientCommand.ip()
                            + ", os - "         + clientCommand.os()
                            + ", model - "      + clientCommand.model()
                            + ", username - "   + clientCommand.username()
                            + ", user ident - " + clientCommand.userIdentifier();

                serverDTO.clients().put(clientCommand.IMEI(), clientCommand.username());

                clientDAO.setIsFirst(false);
                clientDAO.setIMEI(clientCommand.IMEI());

                String result = 
                        "{\n"
                            + "\"IMEI\": \"" + clientCommand.IMEI() + "\",\n"
                            + "\"is_done\": \"True\",\n"
                            + "\"message\": \"Соединение установлено!\",\n"
                            + "\"ident\": \"" + ident + "\",\n"
                            + "\"sklad_list\": [\"Склад 1\", \"Склад 2\"],\n"
                            + "\"TK_list\": [\"ТК 1\"]\n"
                        + "}";

                return super.commandResult(clientCommand.IMEI(), LogMessageTypeEnum.INFO, 
                        ResultTypeMessageEnum.TRUE, result, 
                        "Client " + clientCommand.IMEI() + " connected: " + message);
            }
            else
                return super.commandResult(clientCommand.IMEI(), LogMessageTypeEnum.ERROR, ResultTypeMessageEnum.FALSE, 
                        "Извините, но вы находитесь в черном списке. Отключение от сервера.", null);
        }
        else
            return super.commandResult("Unknown", LogMessageTypeEnum.ERROR, ResultTypeMessageEnum.FALSE, 
                    "Неверный формат команды. Обратитесь к администратору для устранения ошибки. Вы были отключены от сервера", null);
    }
    
}
