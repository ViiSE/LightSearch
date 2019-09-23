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

package lightsearch.daemon.type;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.testng.Assert.*;
import static test.ResourcesFilesPath.getResourcesFilesPath;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

public class DaemonWindowsDefaultImplTestNG {

    @Test
    @Parameters({"serverName", "openTest"})
    public void execute(String serverName, boolean openTest) {
        testBegin("DaemonWindowsDefaultImpl", "execute()");

        String lightSearchServerName = getResourcesFilesPath() + serverName;
        assertNotNull(lightSearchServerName, "LightSearch Server Name value is null!");
        assertNotEquals(lightSearchServerName, "", "LightSearch Server Name value is empty!");

        Daemon daemon = DaemonInit.daemonWindows(lightSearchServerName);
        assertNotNull(daemon, "Daemon is null!");

        if(openTest)
            daemon.execute();

        testEnd("DaemonWindowsDefaultImpl", "execute()");
    }
}
