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
package lightsearch.server.initialization;

import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class ServerSettingsTestNG {
    
    @Test
    public void rebootServerValue() {
        testBegin("ServerSettings", "rebootServerValue()");
        
        CurrentServerDirectory currentServerDirectory =
                CurrentServerDirectoryInit.currentDirectoryDebug(OsDetectorInit.osDetector());
        
        ServerSettings serverSettings = ServerSettingsInit.serverSettings(currentServerDirectory);
        int rebootServer = serverSettings.rebootServerValue();
        System.out.println("Reboot server value: " + rebootServer);
        
        assertFalse(rebootServer < 0, "Reboot server value is less than 0!");
        
        testEnd("ServerSettings", "rebootServerValue()");
    }
    
    @Test
    public void timeoutClientValue() {
        testBegin("ServerSettings", "timeoutClientValue()");
        
        CurrentServerDirectory currentServerDirectory =
                CurrentServerDirectoryInit.currentDirectoryDebug(OsDetectorInit.osDetector());
        
        ServerSettings serverSettings = ServerSettingsInit.serverSettings(currentServerDirectory);
        int timeoutClient = serverSettings.timeoutClientValue();
        System.out.println("Timeout client value: " + timeoutClient);
        
        assertFalse(timeoutClient < 0, "Timeout client value is less than 0!");
        
        testEnd("ServerSettings", "timeoutClientValue()");
    }
}
