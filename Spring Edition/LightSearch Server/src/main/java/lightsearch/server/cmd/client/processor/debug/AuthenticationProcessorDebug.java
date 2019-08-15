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
import lightsearch.server.cmd.client.processor.ProcessorClient;
import lightsearch.server.cmd.result.CommandResult;
import lightsearch.server.data.ClientDAO;
import lightsearch.server.data.LightSearchServerDTO;
import lightsearch.server.log.LogMessageTypeEnum;
import lightsearch.server.message.result.ResultTypeMessageEnum;
import lightsearch.server.producer.cmd.client.CommandResultClientCreatorProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 *
 * @author ViiSE
 */
@Component("authenticationProcessorClientDebug")
@Scope("prototype")
public class AuthenticationProcessorDebug implements ProcessorClient {

    private final LightSearchChecker checker;
    private final List<String> blacklist;
    private final Map<String, String> clients;
    private final ClientDAO clientDAO;

    @Autowired
    private CommandResultClientCreatorProducer cmdResClCrProducer;

    public AuthenticationProcessorDebug(LightSearchServerDTO serverDTO, LightSearchChecker checker, ClientDAO clientDAO) {
        this.checker = checker;
        this.blacklist = serverDTO.blacklist();
        this.clients = serverDTO.clients();
        this.clientDAO = clientDAO;
    }

    @Override
    public CommandResult apply(ClientCommand clientCommand) {
        if(!checker.isNull(clientCommand.username(), clientCommand.password(),
                clientCommand.IMEI(), clientCommand.ip(), clientCommand.os(), 
                clientCommand.model(), clientCommand.userIdentifier())) {
            if(!blacklist.contains(clientCommand.IMEI())) {
                String ident;
                if(!clientCommand.username().equals("superAdmin")) {
                    if(clientCommand.userIdentifier().equals("0"))
                        return commandResult(clientCommand.IMEI(), LogMessageTypeEnum.ERROR, ResultTypeMessageEnum.FALSE,
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

                clients.put(clientCommand.IMEI(), clientCommand.username());

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

                return commandResult(clientCommand.IMEI(), LogMessageTypeEnum.INFO,
                        ResultTypeMessageEnum.TRUE, result, 
                        "Client " + clientCommand.IMEI() + " connected: " + message);
            }
            else
                return commandResult(clientCommand.IMEI(), LogMessageTypeEnum.ERROR, ResultTypeMessageEnum.FALSE,
                        "Извините, но вы находитесь в черном списке. Отключение от сервера.", null);
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
