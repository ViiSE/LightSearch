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

/**
 * Реализация интерфейса {@link lightsearch.server.initialization.OsDetector} по умолчанию.
 * @author ViiSE
 * @since 1.0
 */
public class OsDetectorDefaultImpl implements OsDetector {

    @Override
    public boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("windows");
    }

    @Override
    public boolean isLinux() {
        return System.getProperty("os.name").toLowerCase().contains("linux");
    }

    @Override
    public boolean isMacOS() {
        return System.getProperty("os.name").toLowerCase().contains("mac");
    }
    
}
