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
 *
 * @author ViiSE
 */
public class CloseSoftCheckProcessorDebug extends AbstractProcessorClient {
    
    private final SoftCheckDebug softCheck;
    
    public CloseSoftCheckProcessorDebug(LightSearchServerDTO serverDTO, 
            LightSearchChecker checker, SoftCheckDebug softCheck) {
        super(serverDTO, checker);
        this.softCheck = softCheck;
    }
    
    @Override
    public CommandResult apply(ClientCommand clientCommand) {
        if(!super.checker.isNull(clientCommand.IMEI(), clientCommand.userIdentifier(), 
                clientCommand.cardCode(), clientCommand.data(), clientCommand.delivery())) {
            if(!serverDTO.blacklist().contains(clientCommand.IMEI())) {
                if(softCheck.closeSoftCheck()) {
                   
                    String logMessage = "Client " + clientCommand.IMEI() + 
                            " close SoftCheck," +
                            " user ident - " + clientCommand.userIdentifier()+ "," +
                            " card code - " + clientCommand.cardCode() + "," +
                            " data - " + clientCommand.data() + "," + 
                            " delivery type - " + clientCommand.delivery();
                
                    String result = 
                            "{\n"
                                + "\"IMEI\": \"" + clientCommand.IMEI() + "\"\n"
                                + "\"is_done\": \"True\"\n"
                                + "\"message\": \"Мягкий чек закрыт!\"\n"
                            + "}";

                    return super.commandResult(clientCommand.IMEI(), 
                            LogMessageTypeEnum.INFO, ResultTypeMessageEnum.TRUE, 
                            result, logMessage);        
                }
                else {
                    return super.commandResult(clientCommand.IMEI(), 
                            LogMessageTypeEnum.ERROR, ResultTypeMessageEnum.FALSE, 
                            "Невозможно закрыть мягкий чек. Скорее всего, он не был открыт. Попробуйте открыть его.", 
                            "Client " + clientCommand.IMEI() + " cannot close SoftCheck, "
                                    + "username - " + clientCommand.username()
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
