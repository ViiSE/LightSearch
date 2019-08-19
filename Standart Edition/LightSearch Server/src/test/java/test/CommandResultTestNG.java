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

import lightsearch.server.cmd.result.CommandResult;
import lightsearch.server.cmd.result.CommandResultInit;
import lightsearch.server.log.LogMessageTypeEnum;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class CommandResultTestNG {
    
    @Test
    public void message() {
        testBegin("CommandResult", "message()");
        
        String message = "Hello world!";
        String logMessage = "Client required \"Hello World!\".";
        CommandResult cmdResult = CommandResultInit.commandResult(LogMessageTypeEnum.INFO, message, logMessage);
        
        System.out.println(cmdResult.message());
        
        testEnd("CommandResult", "message()");
    }
    
    @Test
    public void type() {
        testBegin("CommandResult", "type()");
        
        String message = "Hello world!";
        String logMessage = "Client required \"Hello World!\".";
        CommandResult cmdResult = CommandResultInit.commandResult(LogMessageTypeEnum.INFO, message, logMessage);
        
        System.out.println(cmdResult.type());
        
        testEnd("CommandResult", "type()");
    }
    
    @Test
    public void logMessage() {
        testBegin("CommandResult", "logMessage()");
        
        String message = "Hello world!";
        String logMessage = "Client required \"Hello World!\".";
        CommandResult cmdResult = CommandResultInit.commandResult(LogMessageTypeEnum.INFO, message, logMessage);
        
        System.out.println(cmdResult.logMessage());
        
        testEnd("CommandResult", "logMessage()");
    }
}
