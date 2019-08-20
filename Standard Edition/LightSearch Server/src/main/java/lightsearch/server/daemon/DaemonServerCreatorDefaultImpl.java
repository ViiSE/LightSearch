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
package lightsearch.server.daemon;

import lightsearch.server.initialization.OsDetector;

/**
 *
 * @author ViiSE
 */
public class DaemonServerCreatorDefaultImpl implements DaemonServerCreator {

    private final OsDetector osDetector;
    private final String currentDirectory;
    
    public DaemonServerCreatorDefaultImpl(OsDetector osDetector, String currentDirectory) {
        this.osDetector = osDetector;
        this.currentDirectory = currentDirectory;
    }

    @Override
    public DaemonServer createDaemonServer() {
        if(osDetector.isWindows()) {
            return DaemonServerInit.daemonServerWindows(currentDirectory);
        }
        else return null;
    }
    
}
