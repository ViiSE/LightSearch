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

import lightsearch.server.initialization.CurrentServerDirectory;
import lightsearch.server.initialization.CurrentServerDirectoryInit;
import lightsearch.server.initialization.OsDetectorInit;
import lightsearch.server.initialization.OsDetector;

/**
 * Реализация интерфейса {@link lightsearch.server.log.LogDirectory} по умолчанию.
 * <p>
 * Директория для логирования определяется как {@code текущая_директория/директория_лога}.
 * @author ViiSE
 * @since 1.0.0
 */
public class LogDirectoryDefaultImpl implements LogDirectory {

    private final String logDirectoryName;
    
    public LogDirectoryDefaultImpl(String logDirectoryName) {
        this.logDirectoryName = logDirectoryName.trim();
    }
    
    @Override
    public String logDirectory() {
        OsDetector osDetector = OsDetectorInit.osDetector();
        CurrentServerDirectory currentServerDirectory = CurrentServerDirectoryInit.currentDirectory(
                osDetector);
        String loggerDirectory = currentServerDirectory.currentDirectory();
        
        if(osDetector.isWindows())
            loggerDirectory += logDirectoryName + "\\";
        else if(osDetector.isLinux() || osDetector.isMacOS())
            loggerDirectory += logDirectoryName + "/";
        
        return loggerDirectory;
    }    
}
