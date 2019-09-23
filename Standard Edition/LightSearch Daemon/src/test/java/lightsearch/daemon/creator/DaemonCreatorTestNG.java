/*
 * Copyright 2019 ViiSE.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package lightsearch.daemon.creator;

import lightsearch.daemon.os.detector.OsDetector;
import lightsearch.daemon.os.detector.OsDetectorInit;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;


public class DaemonCreatorTestNG {

    private OsDetector osDetector;

    @BeforeClass
    public void setUpClass() {
        osDetector = OsDetectorInit.osDetector();
    }

    @Test
    @Parameters({"serverName"})
    public void createDaemon(String serverName) {
        testBegin("DaemonCreator", "createDaemon");

        assertNotNull(osDetector, "OSDetector is null!");
        DaemonCreator daemonCreator = DaemonCreatorInit.daemonCreator(osDetector);
        assertNotNull(daemonCreator, "DaemonCreator is null!");

        System.out.println("DaemonCreator.createDaemon(): " + daemonCreator.createDaemon(serverName));
        testEnd("DaemonCreator", "createDaemon");
    }
}
