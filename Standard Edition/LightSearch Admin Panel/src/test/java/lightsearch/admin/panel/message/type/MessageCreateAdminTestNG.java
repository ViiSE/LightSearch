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
package lightsearch.admin.panel.message.type;

import lightsearch.admin.panel.message.type.MessageCreateAdmin;
import lightsearch.admin.panel.message.type.MessageCreateAdminInit;
import static org.testng.Assert.*;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class MessageCreateAdminTestNG {
    
    @Test
    public void message() {
        testBegin("MessageCreateAdmin", "message()");
        
        String name = "name";
        assertNotNull(name, "Name is null!");
        assertFalse(name.isEmpty(), "Name is null!");
        
        
        String adminName = "adminName";
        assertNotNull(adminName, "Admin name is null!");
        assertFalse(adminName.isEmpty(), "Admin name is null!");
        
        String pass = "password";
        assertNotNull(pass, "Password is null!");
        assertFalse(pass.isEmpty(), "Password is null!");
        
        MessageCreateAdmin msgCrAdmin = 
                MessageCreateAdminInit.messageCreateAdmin(name, adminName, pass);
        assertNotNull(msgCrAdmin, "MessageCreateAdmin is null!");
        
        String message = msgCrAdmin.message();
        assertNotNull(message, "Message is null!");
        assertFalse(message.isEmpty(), "Message is null!");
        
        System.out.println("Message: " + message);
        
        testEnd("MessageCreateAdmin", "message()");
    }
}
