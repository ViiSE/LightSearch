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
import lightsearch.server.data.LightSearchServerDTO;
import lightsearch.server.log.LogMessageTypeEnum;
import lightsearch.server.message.result.ResultTypeMessageEnum;
import lightsearch.server.producer.cmd.client.CommandResultClientCreatorProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *
 * @author ViiSE
 */
@Component("openSoftCheckProcessorClientDebug")
@Scope("prototype")
public class OpenSoftCheckProcessorDebug implements ProcessorClient {

    private final LightSearchChecker checker;
    private final List<String> blacklist;
    private final SoftCheckDebug softCheck;

    @Autowired
    private CommandResultClientCreatorProducer cmdResClCrProducer;

    public OpenSoftCheckProcessorDebug(LightSearchServerDTO serverDTO, LightSearchChecker checker, SoftCheckDebug softCheck) {
        this.checker = checker;
        this.blacklist = serverDTO.blacklist();
        this.softCheck = softCheck;
    }
    
    @Override
    public CommandResult apply(ClientCommand clientCommand) {
        if(!checker.isNull(clientCommand.IMEI(), clientCommand.userIdentifier(), clientCommand.cardCode())) {
            if(!blacklist.contains(clientCommand.IMEI())) {
                if(softCheck.openSoftCheck()) {
                    String logMessage = "Client " + clientCommand.IMEI() + 
                            " open SoftCheck," +
                            " user ident - " + clientCommand.userIdentifier()+ "," + 
                            " card code - " + clientCommand.cardCode();
                
                    String result = 
                            "{\n"
                                + "\"IMEI\": \"" + clientCommand.IMEI() + "\",\n"
                                + "\"is_done\": \"True\",\n"
                                + "\"message\": \"Мягкий чек открыт.\"\n"
                            + "}";

                    return commandResult(clientCommand.IMEI(),
                            LogMessageTypeEnum.INFO, ResultTypeMessageEnum.TRUE, 
                            result, logMessage);        
                }
                else {
                    return commandResult(clientCommand.IMEI(),
                            LogMessageTypeEnum.ERROR, ResultTypeMessageEnum.FALSE, 
                            "Невозможно открыть мягкий чек. Скорее всего, он уже открыт. Попробуйте отменить или закрыть мягкий чек.", 
                            "Client " + clientCommand.IMEI() + " cannot open SoftCheck, "
                                    + "user ident - " + clientCommand.userIdentifier()+ ","
                                    + "card code - " + clientCommand.cardCode());
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
