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
package lightsearch.server.message;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.testng.Assert.*;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class MessageTimeAdderTestNG {
    
    @Test
    @Parameters({"firstValue", "secondValue", "thirdValue"})
    public void add(int firstValue, int secondValue, int thirdValue) {
        testBegin("MessageTimeAdder", "add()");
        
        MessageTimeAdder msgTimeAdder = MessageTimeAdderInit.messageTimeAdder();
        
        msgTimeAdder.add(firstValue);
        System.out.println("avg: " + msgTimeAdder.averageTime());
        assertNotEquals((firstValue - 5), msgTimeAdder.averageTime(), "First value is missing!");
        
        msgTimeAdder.add(secondValue);
        System.out.println("avg: " + msgTimeAdder.averageTime());
        assertEquals(secondValue, msgTimeAdder.averageTime(), "Second value is not missing!");
        
        msgTimeAdder.add(thirdValue);
        System.out.println("avg: " + msgTimeAdder.averageTime());
        assertEquals(msgTimeAdder.averageTime(), ((secondValue + thirdValue) / 2), "Second value is not missing!");
        
        testEnd("MessageTimeAdder", "add()");
    }
}
