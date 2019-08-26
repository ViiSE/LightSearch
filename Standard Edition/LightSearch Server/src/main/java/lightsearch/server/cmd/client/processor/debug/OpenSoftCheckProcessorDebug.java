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

import lightsearch.server.cmd.client.processor.*;
import lightsearch.server.checker.LightSearchChecker;
import lightsearch.server.cmd.client.ClientCommand;
import lightsearch.server.cmd.result.CommandResult;
import lightsearch.server.data.LightSearchServerDTO;
import lightsearch.server.log.LogMessageTypeEnum;
import lightsearch.server.message.result.ResultTypeMessageEnum;

/**
 * Обработчик команды открытия мягкого чека для отладки.
 * <p>
 * Для симуляции мягкого чека используется {@link lightsearch.server.cmd.client.processor.debug.SoftCheckDebug}.
 * @author ViiSE
 * @since 2.0
 */
public class OpenSoftCheckProcessorDebug extends AbstractProcessorClient {

    private final SoftCheckDebug softCheck;
    
    public OpenSoftCheckProcessorDebug(LightSearchServerDTO serverDTO, 
            LightSearchChecker checker, SoftCheckDebug softCheck) {
        super(serverDTO, checker);
        this.softCheck = softCheck;
    }
    
    @Override
    public CommandResult apply(ClientCommand clientCommand) {
        if(!super.checker.isNull(clientCommand.IMEI(), clientCommand.userIdentifier(), clientCommand.cardCode())) {
            if(!serverDTO.blacklist().contains(clientCommand.IMEI())) {
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

                    return super.commandResult(clientCommand.IMEI(), 
                            LogMessageTypeEnum.INFO, ResultTypeMessageEnum.TRUE, 
                            result, logMessage);        
                }
                else {
                    return super.commandResult(clientCommand.IMEI(), 
                            LogMessageTypeEnum.ERROR, ResultTypeMessageEnum.FALSE, 
                            "Невозможно открыть мягкий чек. Скорее всего, он уже открыт. Попробуйте отменить или закрыть мягкий чек.", 
                            "Client " + clientCommand.IMEI() + " cannot open SoftCheck, "
                                    + "user ident - " + clientCommand.userIdentifier()+ ","
                                    + "card code - " + clientCommand.cardCode());
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
