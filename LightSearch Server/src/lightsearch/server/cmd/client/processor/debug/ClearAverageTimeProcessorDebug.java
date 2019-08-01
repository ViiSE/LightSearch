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
import lightsearch.server.message.MessageTimeAdder;
import lightsearch.server.message.MessageTimeAdderInit;
import lightsearch.server.message.result.ResultTypeMessageEnum;

/**
 *
 * @author ViiSE
 */
public class ClearAverageTimeProcessorDebug extends AbstractProcessorClient {
    
    public ClearAverageTimeProcessorDebug(LightSearchServerDTO serverDTO, 
            LightSearchChecker checker) {
        super(serverDTO, checker);
    }

    @Override
    public CommandResult apply(ClientCommand clientCommand) {
        
        MessageTimeAdder msgTimeAdder = MessageTimeAdderInit.messageTimeAdder();
        msgTimeAdder.clear();

        return super.commandResult(clientCommand.IMEI(), LogMessageTypeEnum.INFO, 
                ResultTypeMessageEnum.TRUE, "OK", "");
    }
    
}
