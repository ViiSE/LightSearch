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

package lightsearch.updater.apk;

import lightsearch.updater.LightSearchUpdater;
import lightsearch.updater.exception.APKException;
import lightsearch.updater.producer.apk.APKProducer;
import lightsearch.updater.producer.apk.APKSaverProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static lightsearch.updater.message.TestMessage.*;
import static org.testng.Assert.assertNotNull;

@SpringBootTest(classes = LightSearchUpdater.class)
public class APKSaverTestNG extends AbstractTestNGSpringContextTests {

    @Autowired
    private APKSaverProducer apkSaverProducer;

    @Autowired
    private APKProducer apkProducer;

    private APK apk;

    @BeforeClass
    @Parameters({"apkName", "apkVersion", "apkContent"})
    public void setUpClass(String apkName, String apkVersion, String apkContent) throws Exception {
        apk = apkProducer.getAPKDefaultInstance();
        assertNotNull(apk, "APK is null!");

        byte[] arr = apkContent.getBytes();
        InputStream inputStream = new ByteArrayInputStream(arr);
        apk.setContent(inputStream);
        apk.setVersion(apkVersion);
        apk.setAPKName(apkName);
    }

    @Test
    public void saveAPK() {
        testBegin("APKSaver", "saveAPK()");

        APKSaver apkSaver = apkSaverProducer.getAPKSaverDefaultInstance();
        try {
            apkSaver.saveAPK(apk);
        } catch (APKException ex) {
            catchMessage(ex);
        }

        testEnd("APKSaver", "saveAPK()");
    }
}
