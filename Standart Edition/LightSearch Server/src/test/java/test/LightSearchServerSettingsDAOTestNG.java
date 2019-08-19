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
package test;

import lightsearch.server.data.LightSearchServerSettingsDAOInit;
import static org.testng.Assert.*;
import org.testng.annotations.Test;
import lightsearch.server.data.LightSearchServerSettingsDAO;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class LightSearchServerSettingsDAOTestNG {
    
    @Test
    public void serverRebootValue() {
        testBegin("LightSearchServerSettingsDAO", "serverRebootValue()");
        
        int serverReboot = 0;
        int clientTimeout = 0;
        LightSearchServerSettingsDAO settingsDTO = LightSearchServerSettingsDAOInit.settingsDAO(serverReboot, clientTimeout);
        assertFalse(serverReboot < 0, "Server reboot value is less than 0!");
        System.out.println("serverRebootValue:" + settingsDTO.serverRebootValue());
        
        testEnd("LightSearchServerSettingsDAO", "serverRebootValue()");
    }

    @Test
    public void clientTimeoutValue() {
        testBegin("LightSearchServerSettingsDAO", "clientTimeoutValue()");
        
        int serverReboot = 0;
        int clientTimeout = 0;
        LightSearchServerSettingsDAO settingsDTO = LightSearchServerSettingsDAOInit.settingsDAO(serverReboot, clientTimeout);
        assertFalse(clientTimeout < 0, "Client timeout value is less than 0!");
        System.out.println("clientTimeoutValue:" + settingsDTO.clientTimeoutValue());
    
        testEnd("LightSearchServerSettingsDAO", "clientTimeoutValue()");
    }

    @Test
    public void setServerRebootValue() {
        testBegin("LightSearchServerSettingsDAO", "setServerRebootValue()");
        
        int serverReboot = 0;
        int clientTimeout = 0;
        LightSearchServerSettingsDAO settingsDTO = LightSearchServerSettingsDAOInit.settingsDAO(serverReboot, clientTimeout);
        int newServerRebootValue = 4;
        settingsDTO.setServerRebootValue(newServerRebootValue);
        assertFalse(serverReboot < 0, "server reboot value is less than 0!");
        System.out.println("newServerRebootValue:" + settingsDTO.serverRebootValue());
        
        testEnd("LightSearchServerSettingsDAO", "setServerRebootValue()");
    }

    @Test
    public void setClientTimeoutValue() {
        testBegin("LightSearchServerSettingsDAO", "setClientTimeoutValue()");
        
        int serverReboot = 0;
        int clientTimeout = 0;
        LightSearchServerSettingsDAO settingsDTO = LightSearchServerSettingsDAOInit.settingsDAO(serverReboot, clientTimeout);
        int newClientTimeoutValue = 30000;
        settingsDTO.setClientTimeoutValue(newClientTimeoutValue);
        assertFalse(clientTimeout < 0, "Client timeout value is less than 0!");
        System.out.println("newClientTimeoutValue:" + settingsDTO.clientTimeoutValue());
        
        testEnd("LightSearchServerSettingsDAO", "setClientTimeoutValue()");
    } 
}
