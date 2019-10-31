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

package lightsearch.server.data;

import lightsearch.server.LightSearchServer;
import lightsearch.server.data.pojo.LightSearchSettingsFromPropertiesFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

@SpringBootTest(classes = LightSearchServer.class)
public class LightSearchSettingsFromPropertiesFileTestNG extends AbstractTestNGSpringContextTests {

    @Autowired
    private LightSearchSettingsFromPropertiesFile settings;

    @Test
    public void check_serialize() {
        testBegin("LightSearchSettingsFromPropertiesFile", "");

        System.out.println("Restart time:");
        System.out.println("\tInstance: " + settings.getRestartTime().getClass());
        System.out.println("\tValue: " + settings.getRestartTime().toString());

        System.out.println("Restart datetime:");
        System.out.println("\tInstance: " + settings.getRestartDateTime().getClass());
        System.out.println("\tValue: " + settings.getRestartDateTime().toString());

        System.out.println("Frequency:");
        System.out.println("\tValue: " + settings.getFrequency());

        System.out.println("Reduce value:");
        System.out.println("\tValue: " + settings.getReduceValue());

        System.out.println("Timeout limit:");
        System.out.println("\tValue: " + settings.getTimeoutLimit());

        testEnd("LightSearchSettingsFromPropertiesFile", "");
    }
}
