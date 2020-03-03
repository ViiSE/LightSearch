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
package lightsearch.server.cmd.result;

import lightsearch.server.log.LogMessageTypeEnum;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class CommandResultTestNG {
    
    @Test
    @Parameters({"message", "logMessage"})
    public void message(String message, String logMessage) {
        testBegin("CommandResult", "message()");
        
        CommandResult cmdResult = CommandResultInit.commandResult(LogMessageTypeEnum.INFO, message, logMessage);
        assertNotNull(cmdResult, "CommandResult is null!");
        
        System.out.println(cmdResult.message());
        
        testEnd("CommandResult", "message()");
    }
    
    @Test
    @Parameters({"message", "logMessage"})
    public void type(String message, String logMessage) {
        testBegin("CommandResult", "type()");
        
        CommandResult cmdResult = CommandResultInit.commandResult(LogMessageTypeEnum.INFO, message, logMessage);
        assertNotNull(cmdResult, "CommandResult is null!");

        System.out.println(cmdResult.type());
        
        testEnd("CommandResult", "type()");
    }
    
    @Test
    @Parameters({"message", "logMessage"})
    public void logMessage(String message, String logMessage) {
        testBegin("CommandResult", "logMessage()");
        
        CommandResult cmdResult = CommandResultInit.commandResult(LogMessageTypeEnum.INFO, message, logMessage);
        assertNotNull(cmdResult, "CommandResult is null!");

        System.out.println(cmdResult.logMessage());
        
        testEnd("CommandResult", "logMessage()");
    }
}
