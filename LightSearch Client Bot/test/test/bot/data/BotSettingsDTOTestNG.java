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
package test.bot.data;

import java.util.ArrayList;
import java.util.List;
import lightsearch.client.bot.TestCycle;
import lightsearch.client.bot.TestCycleInit;
import lightsearch.client.bot.data.BotSettingsDTO;
import lightsearch.client.bot.data.BotSettingsDTOInit;
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
public class BotSettingsDTOTestNG {
    
    private BotSettingsDTO botSettingsDTO;
    
    @BeforeClass
    public void setUpClass() {
        List<Processor> processors = new ArrayList<>();
        processors.add(new ProcessorConnectionDefaultImpl());
        processors.add(new ProcessorAuthorizationDefaultImpl());
        
        TestCycle testCycle = TestCycleInit.testCycle(processors);
        
        int cycleAmount = 1;
        long delayBeforeSendingMessage = 3;
        
        botSettingsDTO = BotSettingsDTOInit.botSettingsDTO(testCycle, cycleAmount, delayBeforeSendingMessage);
    }
    
    @Test
    public void testCycle() {
        testBegin("BotSettingsDTO", "testCycle()");
        
        assertNotNull(botSettingsDTO.testCycle(), "TestCycle is null!");
        System.out.println("TestCycle: " + botSettingsDTO.testCycle());
        
        testEnd("BotSettingsDTO", "testCycle()");
    }
    
    @Test
    public void cycleAmount() {
        testBegin("BotSettingsDTO", "cycleAmount()");
        
        assertNotNull(botSettingsDTO.cycleAmount(), "CycleAmount is null!");
        assertFalse(botSettingsDTO.cycleAmount() < 0, "CycleAmount value is less than 0!");
        System.out.println("CycleAmount: " + botSettingsDTO.cycleAmount());
        
        testEnd("BotSettingsDTO", "cycleAmount()");
    }
    
    @Test
    public void delayBeforeSendingMessage() {
        testBegin("BotSettingsDTO", "delayBeforeSendingMessage()");
        
        assertNotNull(botSettingsDTO.delayBeforeSendingMessage(), "DelayBeforeSendingMessage is null!");
        assertFalse(botSettingsDTO.delayBeforeSendingMessage() < 0, "DelayBeforeSendingMessage value is less than 0!");
        System.out.println("DelayBeforeSendingMessage: " + botSettingsDTO.delayBeforeSendingMessage());
        
        testEnd("BotSettingsDTO", "delayBeforeSendingMessage()");
    }
}
