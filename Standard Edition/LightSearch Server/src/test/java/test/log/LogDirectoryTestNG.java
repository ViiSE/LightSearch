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
package test.log;

import lightsearch.server.initialization.OsDetector;
import lightsearch.server.initialization.OsDetectorInit;
import lightsearch.server.log.LogDirectory;
import lightsearch.server.log.LogDirectoryInit;
import static org.testng.Assert.*;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class LogDirectoryTestNG {
    
    @Test(groups = {"Initialization", "Server"})
    public void logDirectory() {
        testBegin("LogDirectory", "logDirectory()");
        
        String logName = "logs";
        assertFalse(logName.contains("\\"), "Log Name contains \\ !");
        assertFalse(logName.contains("~"),  "Log Name contains ~ !");
        assertFalse(logName.contains("%"),  "Log Name contains % !");
        assertFalse(logName.contains("&"),  "Log Name contains & !");
        assertFalse(logName.contains("*"),  "Log Name contains * !");
        assertFalse(logName.contains("{"),  "Log Name contains { !");
        assertFalse(logName.contains("}"),  "Log Name contains { !");
        assertFalse(logName.contains(":"),  "Log Name contains : !");
        assertFalse(logName.contains("<"),  "Log Name contains < !");
        assertFalse(logName.contains(">"),  "Log Name contains > !");
        assertFalse(logName.contains("?"),  "Log Name contains ? !");
        assertFalse(logName.contains("/"),  "Log Name contains / !");
        assertFalse(logName.contains("+"),  "Log Name contains + !");
        assertFalse(logName.contains("|"),  "Log Name contains | !");
        assertFalse(logName.contains("\""), "Log Name contains \" !");
        
        
        LogDirectory logDirectory = LogDirectoryInit.logDirectory(logName);
        String logDir = logDirectory.logDirectory();
        assertNotNull(logDir, "Log directory is null!");
        assertFalse(logDir.equals(""), "Log directory is null!");
        
        OsDetector osDetector = OsDetectorInit.osDetector();
        if(osDetector.isWindows())
            assertTrue(logDir.endsWith("\\"), "Log directory not ends with \\!");
        else if(osDetector.isLinux() || osDetector.isMacOS())
            assertTrue(logDir.endsWith("/"), "Log directory not ends with /!");
        
        System.out.println(logDir);
        
        testEnd("LogDirectory", "logDirectory()");
    }
}
