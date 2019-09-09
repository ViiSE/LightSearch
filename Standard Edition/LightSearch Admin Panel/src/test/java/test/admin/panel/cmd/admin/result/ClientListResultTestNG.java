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
package test.admin.panel.cmd.admin.result;

import java.util.HashMap;
import java.util.Map;
import lightsearch.admin.panel.cmd.admin.result.ClientListResult;
import lightsearch.admin.panel.cmd.admin.result.ClientListResultInit;
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
public class ClientListResultTestNG {
    
    private Object data;
    private Map<String, String> clients;
    
    @BeforeTest
    public void setUpMethod() {
        String rawMessage = 
                "{\n"
                    + "\"data\": [\n"
                        + "{\n"
                            + "\"IMEI\": \"123456789123456\",\n"
                            + "\"username\": \"client1\"\n"
                        + "}\n"
                        + "{\n"
                            + "\"IMEI\": \"987654321987654\",\n"
                            + "\"username\": \"client2\"\n"
                        + "}\n"                
                    + "]"
                + "\n}";
        assertNotNull(rawMessage, "RawMessage is null!");
        assertFalse(rawMessage.isEmpty(), "RawMessage is null!");
        
        MessageParser msgParser = MessageParserInit.messageParser();
        assertNotNull(msgParser, "MessageParser is null!");
        
        try {
            Object parseMessage = msgParser.parse(rawMessage);
            assertNotNull(parseMessage, "ParseMessage is null!");
            
            CommandResult cmdRes = CommandResultInit.commandResult(parseMessage);
            assertNotNull(cmdRes, "CommandResult is null!");
            
            data = cmdRes.data();
            assertNotNull(data, "Data is null!");
        }
        catch(MessageParserException ex) {
            System.out.println("CATCH! Message: " + ex.getMessage());
        }
        
        clients = new HashMap<>();
        assertNotNull(clients, "Clients is null!");
    }
    
    @Test
    public void result() {
        testBegin("ClientListResult", "result()");
        
        ClientListResult clListRes = 
                ClientListResultInit.clientListResult(data, clients);
        assertNotNull(clListRes, "ClientListResult is null!");
        
        String result = clListRes.result();
        assertNotNull(result, "Result is null!");
        assertFalse(result.isEmpty(), "Result is null!");
        
        System.out.println("Result: ");
        System.out.println(result);
        System.out.println("Clients: " + clients);
        
        testEnd("ClientListResult", "result()");
    }
}
