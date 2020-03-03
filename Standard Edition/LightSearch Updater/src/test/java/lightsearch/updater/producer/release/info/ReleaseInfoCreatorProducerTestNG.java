/*
 *  Copyright 2019 ViiSE
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package lightsearch.updater.producer.release.info;

import lightsearch.updater.LightSearchUpdater;
import lightsearch.updater.release.info.ReleaseInfoCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import static lightsearch.updater.message.TestMessage.testBegin;
import static lightsearch.updater.message.TestMessage.testEnd;
import static org.testng.Assert.assertNotNull;

@SpringBootTest(classes = LightSearchUpdater.class)
public class ReleaseInfoCreatorProducerTestNG extends AbstractTestNGSpringContextTests {

    @Autowired
    private ReleaseInfoCreatorProducer releaseInfoCreatorProducer;

    @Test
    public void getReleaseInfoCreatorJSONAppUpdaterInstance() {
        testBegin("ReleaseInfoCreatorProducer", "getReleaseInfoCreatorJSONAppUpdaterInstance()");

        assertNotNull(releaseInfoCreatorProducer, "ReleaseInfoCreatorProducer is null!");

        ReleaseInfoCreator releaseInfoCreator = releaseInfoCreatorProducer.getReleaseInfoCreatorJSONAppUpdaterInstance();
        assertNotNull(releaseInfoCreator, "ReleaseInfoCreator is null!");

        System.out.println("ReleaseInfoCreator: " + releaseInfoCreator);

        testEnd("ReleaseInfoCreatorProducer", "getReleaseInfoCreatorJSONAppUpdaterInstance()");
    }
}
