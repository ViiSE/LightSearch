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

import static org.testng.Assert.*;
import org.testng.annotations.*;

import lightsearch.server.initialization.CurrentServerDirectory;
import lightsearch.server.initialization.CurrentServerDirectoryInit;
import lightsearch.server.initialization.OsDetectorInit;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;
/**
 *
 * @author ViiSE
 */
public class CurrentServerDirectoryTestNG {

    @Test(groups = {"Initialization", "Server"})
    public void currentDirectory() {
        testBegin("CurrentServerDirectory", "currentDirectory()");
        
        CurrentServerDirectory currServerDir = CurrentServerDirectoryInit.currentDirectory(
                OsDetectorInit.osDetector());
        String currDir = currServerDir.currentDirectory();
        
        assertNotNull(currDir, "Current dir is null!");
        assertFalse(currDir.equals(""), "Current dir is \"\"!");
        
        String os = System.getProperty("os.name");
        if(os.startsWith("Windows"))
            assertTrue(currDir.endsWith("\\"), "Current dir not ends with \\!");
        else
            assertTrue(currDir.endsWith("/"), "Current dir not ends with /!");
        
        testEnd("CurrentServerDirectory", "currentDirectory()");
    }
    
}
