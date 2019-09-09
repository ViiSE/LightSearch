package test.admin.panel.message;

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

import lightsearch.admin.panel.message.type.MessageTimeoutServer;
import lightsearch.admin.panel.message.type.MessageTimeoutServerInit;
import static org.testng.Assert.*;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class MessageTimeoutServerTestNG {
    
    @Test
    public void message() {
        testBegin("MessageTimeoutServer", "message()");
        
        String name = "name";
        assertNotNull(name, "Name is null!");
        assertFalse(name.isEmpty(), "Name is null!");
        
        String timeoutValue = "8";
        assertNotNull(timeoutValue, "TimeoutValue is null!");
        assertFalse(timeoutValue.isEmpty(), "TimeoutValue is null!");
        
        MessageTimeoutServer msgToutServer = 
                MessageTimeoutServerInit.messageTimeoutServer(name, timeoutValue);
        assertNotNull(msgToutServer, "MessageTimeoutServer is null!");
        
        String message = msgToutServer.message();
        assertNotNull(message, "Message is null!");
        assertFalse(message.isEmpty(), "Message is null!");
        
        System.out.println("Message: " + message);
        
        testEnd("MessageTimeoutServer", "message()");
    }
}
