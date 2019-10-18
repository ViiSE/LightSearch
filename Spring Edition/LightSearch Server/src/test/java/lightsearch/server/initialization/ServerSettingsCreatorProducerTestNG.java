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

package lightsearch.server.initialization;

import lightsearch.server.LightSearchServer;
import lightsearch.server.data.pojo.LightSearchSettings;
import lightsearch.server.producer.initialization.CurrentServerDirectoryProducer;
import lightsearch.server.producer.initialization.OsDetectorProducer;
import lightsearch.server.producer.initialization.ServerSettingsCreatorProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

@SpringBootTest(classes = LightSearchServer.class)
public class ServerSettingsCreatorProducerTestNG extends AbstractTestNGSpringContextTests {

    @Autowired private OsDetectorProducer osDetectorProducer;
    @Autowired private CurrentServerDirectoryProducer currentServerDirectoryProducer;
    @Autowired private ServerSettingsCreatorProducer settingsCreatorProducer;

    private ServerSettingsCreator settingsCreator;

    @BeforeClass
    public void setUpClass() {
        OsDetector osDetector = osDetectorProducer.getOsDetectorDefaultInstance();
        CurrentServerDirectory currentServerDirectory =
                currentServerDirectoryProducer.getCurrentServerDirectoryFromFileInstance(osDetector);
        settingsCreator = settingsCreatorProducer.getServerSettingsCreatorFromFileJSONInstance(currentServerDirectory);
    }

    @Test
    public void createSettings() {
        testBegin("ServerSettingsCreator", "createSettings()");

        settingsCreator.createSettings();
        System.out.println("Reboot time: " + LightSearchSettings.getRebootTime());
        System.out.println("Timeout client: " + LightSearchSettings.getTimeoutClient());

        testEnd("ServerSettingsCreator", "createSettings()");
    }
}
