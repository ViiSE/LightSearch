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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import lightsearch.server.log.LogDirectory;
import lightsearch.server.log.LogDirectoryInit;
import lightsearch.server.log.LogMessageTypeEnum;
import lightsearch.server.log.LoggerFile;
import lightsearch.server.log.LoggerFileInit;
import lightsearch.server.log.LoggerServer;
import lightsearch.server.log.LoggerServerInit;
import lightsearch.server.log.LoggerWindow;
import lightsearch.server.log.LoggerWindowInit;
import lightsearch.server.time.CurrentDateTime;
import lightsearch.server.time.CurrentDateTimeInit;
import static org.testng.Assert.*;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class LoggerServerTestNG {
    
    @Test
    public void log() {
        testBegin("LoggerServer", "log()");
        
        LogDirectory logDirectory = LogDirectoryInit.logDirectory("logs");
        LoggerFile loggerFile = LoggerFileInit.loggerFile(logDirectory);
        LoggerWindow loggerWindow = LoggerWindowInit.loggerWindow();
        CurrentDateTime currentDateTime = CurrentDateTimeInit.currentDateTime();
        
        assertNotNull(currentDateTime, "CurrentDateTime is null!");
        
        LoggerServer loggerServer = LoggerServerInit.loggerServer(loggerFile, loggerWindow);
        loggerServer.log(LogMessageTypeEnum.INFO, currentDateTime, "HelloWorld!");
        
        testEnd("LoggerServer", "log()");
    }
}
