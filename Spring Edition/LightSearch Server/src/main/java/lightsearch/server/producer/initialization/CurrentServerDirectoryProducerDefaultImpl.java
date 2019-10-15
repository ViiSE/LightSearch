/*
 *  Copyright 2019 ViiSE.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package lightsearch.server.producer.initialization;

import lightsearch.server.initialization.CurrentServerDirectory;
import lightsearch.server.initialization.OsDetector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service("currentServerDirectoryProducerDefault")
public class CurrentServerDirectoryProducerDefaultImpl implements CurrentServerDirectoryProducer {

    private final String CURRENT_SERVER_DIRECTORY = "currentServerDirectoryFromFile";

    @Autowired
    private ApplicationContext ctx;

    @Override
    public CurrentServerDirectory getCurrentServerDirectoryFromFileInstance(OsDetector osDetector) {
        return (CurrentServerDirectory) ctx.getBean(CURRENT_SERVER_DIRECTORY, osDetector);
    }
}