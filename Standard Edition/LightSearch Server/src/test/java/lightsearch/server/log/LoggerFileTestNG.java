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
package lightsearch.server.log;

import lightsearch.server.time.CurrentDateTime;
import lightsearch.server.time.CurrentDateTimeInit;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class LoggerFileTestNG {
    
    @Test
    @Parameters({"logDirectory", "message"})
    public void writeLogFile(String logName, String message) {
        testBegin("LoggerFile", "writeLogFile()");

        LoggerFile loggerFile = LoggerFileInit.loggerFile(LogDirectoryInit.logDirectory(logName));

        CurrentDateTime currentDateTime = CurrentDateTimeInit.currentDateTime();
        assertNotNull(currentDateTime, "CurrentDateTime is null!");
        
        loggerFile.writeLogFile(LogMessageTypeEnum.INFO.stringValue(), currentDateTime, message);
        
        testEnd("LoggerFile", "writeLogFile()");
    }
}
