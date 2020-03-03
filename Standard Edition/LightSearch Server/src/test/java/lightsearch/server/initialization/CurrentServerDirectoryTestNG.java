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

import static org.testng.Assert.*;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;
/**
 *
 * @author ViiSE
 */
public class CurrentServerDirectoryTestNG {

    @Test
    public void currentDirectory() {
        testBegin("CurrentServerDirectory", "currentDirectory()");
        
        CurrentServerDirectory currServerDir =
                CurrentServerDirectoryInit.currentDirectoryDebug(OsDetectorInit.osDetector());
        String currDir = currServerDir.currentDirectory();
        System.out.println("Current dir: " + currDir);

        assertNotNull(currDir, "Current dir is null!");
        assertNotEquals(currDir, "", "Current dir is \"\"!");
        
        String os = System.getProperty("os.name");
        if(os.startsWith("Windows"))
            assertTrue(currDir.endsWith("\\"), "Current dir not ends with \\!");
        else
            assertTrue(currDir.endsWith("/"), "Current dir not ends with /!");
        
        testEnd("CurrentServerDirectory", "currentDirectory()");
    }
    
}
