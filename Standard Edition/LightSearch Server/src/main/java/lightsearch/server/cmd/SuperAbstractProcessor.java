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
package lightsearch.server.cmd;

import lightsearch.server.checker.LightSearchChecker;
import lightsearch.server.cmd.result.CommandResult;
import lightsearch.server.data.LightSearchServerDTO;
import lightsearch.server.log.LogMessageTypeEnum;
import lightsearch.server.message.result.ResultTypeMessageEnum;

/**
 * Абстрактный класс обработчика команды клиента LightSearch.
 * @author ViiSE
 * @since 1.0
 */
public abstract class SuperAbstractProcessor {

    protected final LightSearchServerDTO serverDTO;
    protected final LightSearchChecker checker;

    public SuperAbstractProcessor(LightSearchServerDTO serverDTO, LightSearchChecker checker) {
        this.serverDTO = serverDTO;
        this.checker = checker;
    }
    
    protected abstract CommandResult commandResult(String name, LogMessageTypeEnum type, ResultTypeMessageEnum resultValue, Object message, String logMessage);
}
