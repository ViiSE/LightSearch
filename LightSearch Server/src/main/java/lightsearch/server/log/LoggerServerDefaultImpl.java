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
package lightsearch.server.log;

import lightsearch.server.time.CurrentDateTime;

/**
 *
 * @author ViiSE
 */
public class LoggerServerDefaultImpl implements LoggerServer {

    private final LoggerFile loggerFile;
    private final LoggerWindow loggerWindow;
    
    public LoggerServerDefaultImpl(LoggerFile loggerFile, LoggerWindow loggerWindow) {
        this.loggerFile = loggerFile;
        this.loggerWindow = loggerWindow;
    }

    @Override
    synchronized public void log(LogMessageTypeEnum type, CurrentDateTime currentDateTime, String message) {
        loggerFile.writeLogFile(type.stringValue(), currentDateTime, message);
        loggerWindow.printLog(type.stringValue(), currentDateTime, message);
    }
    
}
