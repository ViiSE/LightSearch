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
import lightsearch.updater.producer.apk.APKProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import static lightsearch.updater.message.TestMessage.*;
import static org.testng.Assert.assertNotNull;

@SpringBootTest(classes = LightSearchUpdater.class)
public class APKTestNG extends AbstractTestNGSpringContextTests {

    @Autowired
    private APKProducer apkProducer;

    @Test
    @Parameters({"apkName"})
    public void setAndGetAPKName(String apkName) {
        testBegin("APK", "setAPKName(), getAPKName()");

        APK apk = apkProducer.getAPKDefaultInstance();
        assertNotNull(apk, "APK is null!");
        apk.setAPKName(apkName);
        assertNotNull(apk.getAPKName(), "APK name is null!");
        System.out.println("apk.getAPKName(): " + apk.getAPKName());

        testEnd("APK", "setAPKName(), getAPKName()");
    }

    @Parameters({"apkVersion"})
    @Test
    public void setAndGetVersion(String apkVersion) {
        testBegin("APK", "setVersion(), getVersion()");

        APK apk = apkProducer.getAPKDefaultInstance();
        assertNotNull(apk, "APK is null!");
        apk.setVersion(apkVersion);
        assertNotNull(apk.getVersion(), "APK version is null!");
        System.out.println("apk.getVersion(): " + apk.getVersion());

        testEnd("APK", "setVersion(), getVersion()");
    }

    @Parameters({"apkContent"})
    @Test
    public void setAndGetContent(String apkContent) {
        testBegin("APK", "setContent(), getContent()");

        APK apk = apkProducer.getAPKDefaultInstance();
        assertNotNull(apk, "APK is null!");

        assertNotNull(apkContent, "Data is null!");

        InputStream stream = new ByteArrayInputStream(apkContent.getBytes());
        assertNotNull(stream, "Stream is null!");

        try {
            apk.setContent(stream);
            assertNotNull(apk.getContent(), "APK content is null!");
            System.out.println("apk.getContent(): " + Arrays.toString(apk.getContent()));
        }
        catch (IOException ex) {
            catchMessage(ex);
        }

        testEnd("APK", "setContent(), getContent()");
    }
}
