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
import lightsearch.server.cmd.SuperAbstractProcessor;
import lightsearch.server.cmd.result.CommandResult;
import lightsearch.server.cmd.result.CommandResultInit;
import lightsearch.server.cmd.system.SystemCommand;
import lightsearch.server.data.LightSearchServerDTO;
import lightsearch.server.log.LogMessageTypeEnum;
import lightsearch.server.message.result.ResultTypeMessageEnum;

import java.util.function.Function;

/**
 * Абстрактный класс обработчика команд системного бота LightSearch Server.
 * <p>
 * Все обработчики команд системного бота должны наследоваться от данного класса.
 * @author ViiSE
 * @see lightsearch.server.cmd.system.SystemCommandCreator
 * @since 2.0.0
 */
public abstract class AbstractProcessorSystem extends SuperAbstractProcessor implements Function<SystemCommand, CommandResult> {
    
    public AbstractProcessorSystem(LightSearchServerDTO serverDTO, LightSearchChecker checker) {
        super(serverDTO, checker);
    }
    
    @Override
    protected CommandResult commandResult(String name, LogMessageTypeEnum type, 
            ResultTypeMessageEnum resultValue, Object message, String logMessage) {       
        return CommandResultInit.commandResult(type, "OK", logMessage);
    }
}
