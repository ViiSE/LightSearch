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
package lightsearch.server.cmd.system.processor;

import lightsearch.server.checker.LightSearchChecker;
import lightsearch.server.cmd.result.CommandResult;
import lightsearch.server.cmd.system.SystemCommand;
import lightsearch.server.data.LightSearchServerDTO;
import lightsearch.server.log.LogMessageTypeEnum;
import lightsearch.server.message.MessageTimeAdder;
import lightsearch.server.message.MessageTimeAdderInit;
import lightsearch.server.message.result.ResultTypeMessageEnum;

/**
 * Обработчик команды очистки среднего времени замера блока программы LightSearch Server.
 * <p>
 * После того, как клиентские боты LightSearch Client Bot завершают свою работу, LightSearch Client Bot создает
 * системного бота, который подключается к серверу и вызывает эту команду. Тогда среднее время замера скорости блока
 * программы сбрасывается, и LightSearch Server готов к новому тестированию через программу
 * <a href=https://github.com/ViiSE/LightSearch/tree/master/Standard%20Edition/LightSearch%20Client%20Bot/javadoc>
 *     LightSearch Client Bot</a>.
 * @author ViiSE
 * @see lightsearch.server.message.MessageTimeAdder
 * @since 2.0
 */
public class ClearAverageTimeProcessorDebug extends AbstractProcessorSystem {
    
    public ClearAverageTimeProcessorDebug(LightSearchServerDTO serverDTO, 
            LightSearchChecker checker) {
        super(serverDTO, checker);
    }

    @Override
    public CommandResult apply(SystemCommand systemCommand) {
        
        MessageTimeAdder msgTimeAdder = MessageTimeAdderInit.messageTimeAdder();
        msgTimeAdder.clear();

        return super.commandResult("System", LogMessageTypeEnum.INFO, ResultTypeMessageEnum.TRUE, "OK", "");
    }
    
}
