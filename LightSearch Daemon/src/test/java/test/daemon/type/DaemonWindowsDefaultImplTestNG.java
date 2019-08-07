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

package test.daemon.type;

import lightsearch.daemon.type.Daemon;
import lightsearch.daemon.type.DaemonInit;
import org.testng.annotations.Test;

import static test.ResourcesFilesPath.getResourcesFilesPath;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;
import static org.testng.Assert.*;

public class DaemonWindowsDefaultImplTestNG {

    private final String lightSearchServerName = getResourcesFilesPath() + "LightSearch_Server.jar";

    @Test
    public void execute() {
        testBegin("DaemonWindowsDefaultImpl", "execute()");

        assertNotNull(lightSearchServerName, "LightSearch Server Name value is null!");
        assertFalse(lightSearchServerName.equals(""), "LightSearch Server Name value is empty!");

        Daemon daemon = DaemonInit.daemonWindows(lightSearchServerName);
        assertNotNull(daemon, "Daemon is null!");

        daemon.execute();

        testEnd("DaemonWindowsDefaultImpl", "execute()");
    }
}
