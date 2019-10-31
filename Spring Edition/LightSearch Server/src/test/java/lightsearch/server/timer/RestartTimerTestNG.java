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

import lightsearch.server.LightSearchServer;
import lightsearch.server.data.pojo.LightSearchSettingsFromJSONFile;
import lightsearch.server.data.pojo.LightSearchSettingsFromPropertiesFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalTime;

import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

@SpringBootTest(classes = LightSearchServer.class)
public class RestartTimerTestNG extends AbstractTestNGSpringContextTests {

    @Autowired private RestartTimer restartTimer;

    @BeforeClass
    public void setUpClass() {
//        LightSearchSettingsFromJSONFile settings = new LightSearchSettingsFromJSONFile();
//        settings.setRebootTime(LocalTime.now().plusMinutes(1));
//        settings.setFrequency(1);
//        LightSearchSettingsFromPropertiesFile settings = new LightSearchSettingsFromPropertiesFile();
//        settings.
    }

    @Test
    public void restart() throws InterruptedException {
        testBegin("RestartTimer", "restart()");

        Thread.sleep(1);
        //restartTimer.restart();

        testEnd("RestartTimer", "restart()");
    }
}
