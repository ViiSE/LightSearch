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
import lightsearch.server.producer.daemon.DaemonServerProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author ViiSE
 */
@Service("daemonServerCreatorDefault")
@Scope("prototype")
public class DaemonServerCreatorDefaultImpl implements DaemonServerCreator {

    private final OsDetector osDetector;
    private final String currentDirectory;

    @Autowired
    private DaemonServerProducer producer;

    public DaemonServerCreatorDefaultImpl(OsDetector osDetector, String currentDirectory) {
        this.osDetector = osDetector;
        this.currentDirectory = currentDirectory;
    }

    @Override
    public DaemonServer createDaemonServer() {
        if(osDetector.isWindows()) {
            return producer.getDaemonServerWindowsDefaultInstance(currentDirectory);
        }
        else return null;
    }
    
}
