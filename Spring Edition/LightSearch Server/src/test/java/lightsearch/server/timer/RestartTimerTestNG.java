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
import lightsearch.server.data.pojo.LightSearchSettings;
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
        LightSearchSettings settings = new LightSearchSettings();
        settings.setRebootTime(LocalTime.parse("15:47"));
        settings.setFrequency(1);
    }

    @Test
    public void restart() throws InterruptedException {
        testBegin("RestartTimer", "restart()");

        Thread.sleep(2000000);
        //restartTimer.restart();

        testEnd("RestartTimer", "restart()");
    }
}
