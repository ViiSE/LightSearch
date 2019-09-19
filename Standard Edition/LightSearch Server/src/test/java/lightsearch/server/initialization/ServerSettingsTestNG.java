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

import static org.testng.Assert.*;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class ServerSettingsTestNG {
    
    @Test(groups = {"Initialization", "Server"})
    public void rebootServerValue() {
        testBegin("ServerSettings", "rebootServerValue()");
        
        CurrentServerDirectory currentServerDirectory = CurrentServerDirectoryInit.currentDirectory(
                OsDetectorInit.osDetector());
        
        ServerSettings serverSettings = ServerSettingsInit.serverSettings(currentServerDirectory);
        int rebootServer = serverSettings.rebootServerValue();
        
        assertFalse(rebootServer < 0, "Reboot server value is less than 0!");
        
        testEnd("ServerSettings", "rebootServerValue()");
    }
    
    @Test(groups = {"Initialization", "Server"})
    public void timeoutClientValue() {
        testBegin("ServerSettings", "timeoutClientValue()");
        
        CurrentServerDirectory currentServerDirectory = CurrentServerDirectoryInit.currentDirectory(
                OsDetectorInit.osDetector());
        
        ServerSettings serverSettings = ServerSettingsInit.serverSettings(currentServerDirectory);
        int timeoutClient = serverSettings.timeoutClientValue();
        
        assertFalse(timeoutClient < 0, "Timeout client value is less than 0!");
        
        testEnd("ServerSettings", "timeoutClientValue()");
    }
}
