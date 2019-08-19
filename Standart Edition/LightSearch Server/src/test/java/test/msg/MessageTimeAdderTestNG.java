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
package test.msg;

import lightsearch.server.message.MessageTimeAdder;
import lightsearch.server.message.MessageTimeAdderInit;
import static org.testng.Assert.*;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class MessageTimeAdderTestNG {
    
    @Test
    public void add() {
        testBegin("MessageTimeAdder", "add()");
        
        MessageTimeAdder msgTimeAdder = MessageTimeAdderInit.messageTimeAdder();
        
        msgTimeAdder.add(25);
        System.out.println("avg: " + msgTimeAdder.averageTime());
        assertFalse(msgTimeAdder.averageTime() == 20, "First value is missing!");
        
        msgTimeAdder.add(15);
        System.out.println("avg: " + msgTimeAdder.averageTime());
        assertTrue(msgTimeAdder.averageTime() == 15, "Second value is not missing!");
        
        msgTimeAdder.add(35);
        System.out.println("avg: " + msgTimeAdder.averageTime());
        assertTrue(msgTimeAdder.averageTime() == 25, "Second value is not missing!");
        
        testEnd("MessageTimeAdder", "add()");
    }
}
