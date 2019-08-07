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
package test.bot.settings;

import lightsearch.client.bot.settings.CurrentDirectory;
import lightsearch.client.bot.settings.CurrentDirectoryInit;
import lightsearch.client.bot.settings.OsDetector;
import lightsearch.client.bot.settings.OsDetectorInit;
import static org.testng.Assert.*;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class CurrentDirectoryTestNG {
    
    @Test
    public void currentDirectory() {
        testBegin("CurrentDirectory", "currentDirectory()");
        
        OsDetector osDetector = OsDetectorInit.osDetector();
        assertNotNull(osDetector, "OsDetector is null!");
        
        CurrentDirectory curDir = CurrentDirectoryInit.currentDirectory(osDetector);
        assertNotNull(curDir, "CurrentDirectory is null!");
        System.out.println("currentDirectory(): " + curDir.currentDirectory());
        
        testEnd("CurrentDirectory", "currentDirectory()");
    }
}
