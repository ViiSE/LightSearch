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

package lightsearch.updater.producer.apk;

import lightsearch.updater.LightSearchUpdater;
import lightsearch.updater.apk.APKSaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import static lightsearch.updater.message.TestMessage.testBegin;
import static lightsearch.updater.message.TestMessage.testEnd;
import static org.testng.Assert.assertNotNull;

@SpringBootTest(classes = LightSearchUpdater.class)
public class APKSaverProducerTestNG extends AbstractTestNGSpringContextTests {

    @Autowired
    private APKSaverProducer apkSaverProducer;

    @Test
    public void getAPKSaverDefaultInstance() {
        testBegin("APKSaverProducer", "getAPKSaverDefaultInstance()");

        assertNotNull(apkSaverProducer, "APKSaverProducer is null!");

        APKSaver apkSaver = apkSaverProducer.getAPKSaverDefaultInstance();
        assertNotNull(apkSaver, "APKSaver is null!");

        System.out.println("APKSaver: " + apkSaver);

        testEnd("APKSaverProducer", "getAPKSaverDefaultInstance()");
    }
}
