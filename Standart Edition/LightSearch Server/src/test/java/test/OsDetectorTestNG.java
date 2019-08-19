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

import lightsearch.server.initialization.OsDetector;
import lightsearch.server.initialization.OsDetectorInit;
import static org.testng.Assert.*;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class OsDetectorTestNG {
    
    @Test(groups = {"Initialization", "Server"})
    public void isWindows() {
        testBegin("OsDetector", "isWindow()");
        
        OsDetector osDetector = OsDetectorInit.osDetector();
        assertTrue(osDetector.isWindows() ||
                   osDetector.isLinux()   ||
                   osDetector.isMacOS()     , "Not supported OS!");
        
        testEnd("OsDetector", "isWindow()");
    }
}