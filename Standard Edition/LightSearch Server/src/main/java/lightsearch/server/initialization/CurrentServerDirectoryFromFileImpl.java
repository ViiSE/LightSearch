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
package lightsearch.server.initialization;

import java.io.File;
import java.net.URISyntaxException;

import lightsearch.server.LightSearchServer;

/**
 * Реализация интерфейса {@link lightsearch.server.initialization.CurrentServerDirectory} по умолчанию.
 * <p>
 * Если не удастся получить текущую директорию, то сгенерируется исключение {@link java.lang.RuntimeException}.
 * <p>
 * Для определения текущей операционной системы используется интерфейс
 * {@link lightsearch.server.initialization.OsDetector}.
 * @author ViiSE
 * @since 1.0.0
 */
public class CurrentServerDirectoryFromFileImpl implements CurrentServerDirectory {

    private final OsDetector osDetector;
    
    public CurrentServerDirectoryFromFileImpl(OsDetector osDetector) {
        this.osDetector = osDetector;
    }
    
    @Override
    public String currentDirectory() {

        String currentDirectory;
        try {
            currentDirectory = new File(LightSearchServer.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath();
            currentDirectory = currentDirectory.replaceAll("LightSearch_Server.jar", "");

            if(osDetector.isWindows())
                currentDirectory = currentDirectory + "\\";

        } catch (URISyntaxException ex) {
            throw new RuntimeException("Error: " + ex.getMessage());
        }
        return currentDirectory;
    }
}
