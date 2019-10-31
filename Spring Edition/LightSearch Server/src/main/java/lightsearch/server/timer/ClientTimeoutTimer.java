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

package lightsearch.server.timer;

import lightsearch.server.constants.TimeoutConstants;
import lightsearch.server.data.LightSearchServerService;
import lightsearch.server.data.pojo.LightSearchSettingsFromPropertiesFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component("clientTimeoutTimer")
@EnableAsync
public class ClientTimeoutTimer {

    @Autowired private LightSearchServerService serverService;
    @Autowired private LightSearchSettingsFromPropertiesFile settings;
    @Autowired private TimeoutManagerService timeoutManagerService;

    @Async
    @Scheduled(fixedDelay = 1000)
    public void checkClients() {
        if(!(settings.getTimeoutLimit() == TimeoutConstants.TIMEOUT_OFF)) {
            timeoutManagerService.getTimeoutManager().refresh();
            timeoutManagerService.getTimeoutManager().check();
        }
    }
}
