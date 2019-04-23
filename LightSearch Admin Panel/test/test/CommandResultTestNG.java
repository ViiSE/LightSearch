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

import lightsearch.admin.panel.cmd.result.CommandResult;
import lightsearch.admin.panel.cmd.result.CommandResultInit;
import lightsearch.admin.panel.exception.MessageParserException;
import lightsearch.admin.panel.message.parser.MessageParser;
import lightsearch.admin.panel.message.parser.MessageParserInit;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class CommandResultTestNG {
    
    private Object parseMessage;
    
    @BeforeTest
    public void setUpMethod() {
        String messageRaw = 
                    "{\n"
                        + "\"name\": \"admin\"\n"
                        + "\"isDone\": \"True\"\n"
                        + "\"message\": \"some message\"\n"
                        + "\"data\": [\n"
                                        + "{\n"
                                            +"\"name\": \"client1\"\n"
                                            + "\"IMEI\": \"123456789123456\"\n"
                                        + "}\n"
                                        + "{\n"
                                            +"\"name\": \"client2\"\n"
                                            + "\"IMEI\": \"123456123456789\"\n"
                                        + "}\n"
                                  + "]\n"
                  + "}\n";
        assertNotNull(messageRaw, "MessageRaw is null!");
        assertFalse(messageRaw.isEmpty(), "MessageRaw is null!");
        
        MessageParser messageParser = MessageParserInit.messageParser();
        assertNotNull(messageParser, "MessageParser is null!");
        
        try {
            parseMessage = messageParser.parse(messageRaw);
        }
        catch(MessageParserException ex) {
            System.out.println("CATCH! Message: " + ex.getMessage());
        }
        assertNotNull(parseMessage, "ParseMessage is null!");
    }

//        cmdRes.data();
//        cmdRes.message();

    
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
