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
package test.admin.panel;

import lightsearch.admin.panel.message.type.MessageChangeDatabase;
import lightsearch.admin.panel.message.type.MessageChangeDatabaseInit;
import static org.testng.Assert.*;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class MessageChangeDatabaseTestNG {
    
    @Test
    public void message() {
        testBegin("MessageChangeDatabase", "message()");
        
        String name = "name";
        assertNotNull(name, "Name is null!");
        assertFalse(name.isEmpty(), "Name is null!");
        
        String dbName = "dbName";
        assertNotNull(dbName, "Database name is null!");
        assertFalse(dbName.isEmpty(), "Database name is null!");
        
        String ip = "127.0.0.1";
        assertNotNull(ip, "IP is null!");
        assertFalse(ip.isEmpty(), "IP is null!");
        
        String port = "50000";
        assertNotNull(port, "Port is null!");
        assertFalse(port.isEmpty(), "Port is null!");
        
        MessageChangeDatabase msgChDb = 
                MessageChangeDatabaseInit.messageChangeDatabase(name, dbName, ip, port);
        assertNotNull(msgChDb, "MessageChangeDatabase is null!");
        
        String message = msgChDb.message();
        assertNotNull(message, "Message is null!");
        assertFalse(message.isEmpty(), "Message is null!");
        
        System.out.println("Message: " + message);
        
        testEnd("MessageChangeDatabase", "message()");
    }
}
