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
package lightsearch.admin.panel.cmd.result;

import lightsearch.admin.panel.cmd.result.CommandResult;
import lightsearch.admin.panel.cmd.result.CommandResultInit;
import lightsearch.admin.panel.exception.MessageParserException;
import lightsearch.admin.panel.message.parser.MessageParser;
import lightsearch.admin.panel.message.parser.MessageParserInit;
import static org.testng.Assert.*;
import static test.message.TestMessage.*;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 *
 * @author ViiSE
 */
public class CommandResultTestNG {
    
    private Object parseMessage;
    
    @BeforeClass
    @Parameters({"message"})
    public void setUpMethod(String message) {
        assertNotNull(message, "MessageRaw is null!");
        assertFalse(message.isEmpty(), "MessageRaw is null!");
        
        MessageParser messageParser = MessageParserInit.messageParser();
        assertNotNull(messageParser, "MessageParser is null!");
        
        try {
            parseMessage = messageParser.parse(message);
        } catch(MessageParserException ex) {
            catchMessage(ex);
        }
        assertNotNull(parseMessage, "ParseMessage is null!");
    }
    
    @Test
    public void name() {
        testBegin("CommandResult", "name()");
        
        CommandResult cmdRes = CommandResultInit.commandResult(parseMessage);
        assertNotNull(cmdRes, "CommandResult is null!");
        
        String name = cmdRes.name();
        assertNotNull(name, "Name is null!");
        assertFalse(name.isEmpty(), "Name is null!");
        
        System.out.println("CommandResult: name: " + name);
        
        testEnd("CommandResult", "name()");
    }
    
    @Test
    public void isDone() {
        testBegin("CommandResult", "isDone()");
        
        CommandResult cmdRes = CommandResultInit.commandResult(parseMessage);
        assertNotNull(cmdRes, "CommandResult is null!");
        
        String isDone = cmdRes.isDone();
        assertNotNull(isDone, "IsDone is null!");
        assertFalse(isDone.isEmpty(), "IsDone is null!");
        
        System.out.println("CommandResult: isDone: " + isDone);
        
        testEnd("CommandResult", "isDone()");
    }
    
    @Test
    public void message() {
        testBegin("CommandResult", "message()");
        
        CommandResult cmdRes = CommandResultInit.commandResult(parseMessage);
        assertNotNull(cmdRes, "CommandResult is null!");
        
        String message = cmdRes.message();
        assertNotNull(message, "Message is null!");
        assertFalse(message.isEmpty(), "Message is null!");
        
        System.out.println("CommandResult: message: " + message);
        
        testEnd("CommandResult", "message()");
    }
    
    @Test
    public void data() {
        testBegin("CommandResult", "data()");
        
        CommandResult cmdRes = CommandResultInit.commandResult(parseMessage);
        assertNotNull(cmdRes, "CommandResult is null!");
        
        Object data = cmdRes.data();
        assertNotNull(data, "Data is null!");
        
        System.out.println("CommandResult: data: " + data);
        
        testEnd("CommandResult", "data()");
    }
}
