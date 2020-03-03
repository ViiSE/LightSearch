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
package lightsearch.server.data;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class LightSearchServerSettingsDAOTestNG {
    
    @Test
    @Parameters({"serverReboot", "clientTimeout"})
    public void serverRebootValue(int serverReboot, int clientTimeout) {
        testBegin("LightSearchServerSettingsDAO", "serverRebootValue()");

        LightSearchServerSettingsDAO settingsDTO = LightSearchServerSettingsDAOInit.settingsDAO(serverReboot, clientTimeout);
        assertFalse(serverReboot < 0, "Server reboot value is less than 0!");
        System.out.println("serverRebootValue:" + settingsDTO.serverRebootValue());
        
        testEnd("LightSearchServerSettingsDAO", "serverRebootValue()");
    }

    @Test
    @Parameters({"serverReboot", "clientTimeout"})
    public void clientTimeoutValue(int serverReboot, int clientTimeout) {
        testBegin("LightSearchServerSettingsDAO", "clientTimeoutValue()");

        LightSearchServerSettingsDAO settingsDTO = LightSearchServerSettingsDAOInit.settingsDAO(serverReboot, clientTimeout);
        assertFalse(clientTimeout < 0, "Client timeout value is less than 0!");
        System.out.println("clientTimeoutValue:" + settingsDTO.clientTimeoutValue());
    
        testEnd("LightSearchServerSettingsDAO", "clientTimeoutValue()");
    }

    @Test
    @Parameters({"serverReboot", "clientTimeout", "newServerReboot"})
    public void setServerRebootValue(int serverReboot, int clientTimeout, int newServerReboot) {
        testBegin("LightSearchServerSettingsDAO", "setServerRebootValue()");

        LightSearchServerSettingsDAO settingsDTO = LightSearchServerSettingsDAOInit.settingsDAO(serverReboot, clientTimeout);

        settingsDTO.setServerRebootValue(newServerReboot);
        assertFalse(serverReboot < 0, "server reboot value is less than 0!");
        System.out.println("newServerRebootValue:" + settingsDTO.serverRebootValue());
        
        testEnd("LightSearchServerSettingsDAO", "setServerRebootValue()");
    }

    @Test
    @Parameters({"serverReboot", "clientTimeout", "newClientTimeout"})
    public void setClientTimeoutValue(int serverReboot, int clientTimeout, int newClientTimeout) {
        testBegin("LightSearchServerSettingsDAO", "setClientTimeoutValue()");
        
        LightSearchServerSettingsDAO settingsDTO = LightSearchServerSettingsDAOInit.settingsDAO(serverReboot, clientTimeout);
        settingsDTO.setClientTimeoutValue(newClientTimeout);
        assertFalse(clientTimeout < 0, "Client timeout value is less than 0!");
        System.out.println("newClientTimeoutValue:" + settingsDTO.clientTimeoutValue());
        
        testEnd("LightSearchServerSettingsDAO", "setClientTimeoutValue()");
    } 
}
