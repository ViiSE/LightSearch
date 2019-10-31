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

import lightsearch.server.data.pojo.LightSearchSettingsFromPropertiesFile;
import lightsearch.server.log.LoggerServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.restart.RestartEndpoint;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static lightsearch.server.log.LogMessageTypeEnum.INFO;

@Component("restartTimer")
@EnableAsync
public class RestartTimer {

    @Autowired private LoggerServer logger;
    @Autowired private RestartEndpoint restartEndpoint;
    @Autowired private LightSearchSettingsFromPropertiesFile settings;

    @Async
    @Scheduled(fixedDelay = 1000, initialDelay = 30000)
    public void restart() {
        if(settings.getRestartDateTime().isBefore(LocalDateTime.now())) {//LightSearchSettingsFromJSONFile.getRebootDateTime().isBefore(LocalDateTime.now())) {
            logger.log(RestartTimer.class, INFO, "Server restarted");
            restartEndpoint.restart();
        }
    }

}