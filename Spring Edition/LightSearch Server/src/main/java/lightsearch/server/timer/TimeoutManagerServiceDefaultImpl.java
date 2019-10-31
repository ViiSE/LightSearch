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

import lightsearch.server.data.LightSearchServerService;
import lightsearch.server.data.pojo.LightSearchSettingsFromPropertiesFile;
import lightsearch.server.producer.timer.TimeoutManagerProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.objenesis.instantiator.sun.MagicInstantiator;
import org.springframework.stereotype.Service;

@Service("timeoutManagerServiceDefault")
public class TimeoutManagerServiceDefaultImpl implements TimeoutManagerService {

    @Autowired private TimeoutManagerProducer timeoutManagerProducer;
    @Autowired private LightSearchServerService serverService;
    @Autowired private LightSearchSettingsFromPropertiesFile settings;

    private TimeoutManager timeoutManager;

    @Override
    public TimeoutManager getTimeoutManager() {
        if(timeoutManager == null)
            initTimeoutManager();

        return timeoutManager;
    }

    @SuppressWarnings("unchecked")
    private void initTimeoutManager() {
        timeoutManager = timeoutManagerProducer
                .getTimeoutManagerReducerImpl(settings.getReduceValue(), serverService.clientsService());
    }
}
