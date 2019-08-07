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
package test.bot;

import java.util.ArrayList;
import java.util.List;
import lightsearch.client.bot.TestCycle;
import lightsearch.client.bot.TestCycleInit;
import lightsearch.client.bot.exception.TestCycleOutOfBoundException;
import lightsearch.client.bot.processor.Processor;
import lightsearch.client.bot.processor.ProcessorAuthorizationDefaultImpl;
import lightsearch.client.bot.processor.ProcessorConnectionDefaultImpl;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class TestCycleTestNG {
    
    List<Processor> processors;
    
    @BeforeClass
    public void setUpClass() {
        processors = new ArrayList<>();
        processors.add(new ProcessorConnectionDefaultImpl());
        processors.add(new ProcessorAuthorizationDefaultImpl());
    }
    
    @Test
    public void next() {
        testBegin("TestCycle", "next()");
        
        assertNotNull(processors, "Processors is null!");
        System.out.println("Processors list: " + processors);
        
        TestCycle testCycle = TestCycleInit.testCycle(processors);
        assertNotNull(testCycle, "TestCycle is null!");
        
        try {
            while(true)
                System.out.println("next(): " + testCycle.next());
        }
        catch(TestCycleOutOfBoundException ex) {
            System.out.println(ex.getMessage());
        }
        
        testEnd("TestCycle", "next()");
    }
}
