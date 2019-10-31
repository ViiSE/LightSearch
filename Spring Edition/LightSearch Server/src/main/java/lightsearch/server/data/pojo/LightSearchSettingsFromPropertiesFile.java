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

package lightsearch.server.data.pojo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Component("lightSearchSettingsFromPropertiesFile")
@Scope("singleton")
public class LightSearchSettingsFromPropertiesFile {

    @Value("${lightsearch.server.settings.timeout.client-timeout}")
    private int timeoutLimit;
    @Value("${lightsearch.server.settings.restart.restart-time}")
    private LocalTime restartTime;
    @Value("${lightsearch.server.settings.restart.frequency}")
    private int frequency;
    @Value("${lightsearch.server.settings.timeout.reduce-value}")
    private int reduceValue;
    private static LocalDateTime restartDateTime;

    public int getTimeoutLimit() {
        return timeoutLimit;
    }

    public LocalTime getRestartTime() {
        return restartTime;
    }

    public int getFrequency() {
        return frequency;
    }

    public int getReduceValue() {
        return reduceValue;
    }

    public LocalDateTime getRestartDateTime() {
        if(restartDateTime == null)
            calculateRestartDateTime();
        return restartDateTime;
    }

    private void calculateRestartDateTime() {
        LocalDate date = LocalDate.now().plusDays(frequency);
        restartDateTime = LocalDateTime.of(date, restartTime);
    }
}