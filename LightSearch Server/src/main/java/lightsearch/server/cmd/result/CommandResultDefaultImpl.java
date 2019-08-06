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
package lightsearch.server.cmd.result;

import lightsearch.server.log.LogMessageTypeEnum;

/**
 *
 * @author ViiSE
 */
public class CommandResultDefaultImpl implements CommandResult {
    
    private final LogMessageTypeEnum type;
    private final String message;
    private final String logMessage;

    public CommandResultDefaultImpl(LogMessageTypeEnum type, String message,
            String logMessage) {
        this.type = type;
        this.message = message;
        this.logMessage = logMessage;
    }

    @Override
    public String message() {
        return message;
    }
    
    @Override
    public LogMessageTypeEnum type() {
        return type;
    }

    @Override
    public String logMessage() {
        return logMessage;
    }
}
