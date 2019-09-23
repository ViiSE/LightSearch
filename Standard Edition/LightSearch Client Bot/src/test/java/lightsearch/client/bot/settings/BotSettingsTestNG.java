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
package lightsearch.client.bot.settings;

import java.util.ArrayList;
import java.util.List;
import lightsearch.client.bot.TestCycle;
import lightsearch.client.bot.TestCycleInit;
import lightsearch.client.bot.processor.Processor;
import lightsearch.client.bot.processor.ProcessorConnectionDefaultImpl;
import lightsearch.client.bot.settings.BotSettings;
import lightsearch.client.bot.settings.BotSettingsDefaultImpl;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import test.data.DataProviderCreator;

import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class BotSettingsTestNG {
    
    private BotSettings botSettings;
    
    @BeforeClass
    @Parameters({"delayBeforeSendingMessage", "cycleAmount"})
    public void setUpClass(long delayBeforeSendingMessage, int cycleAmount) {
        assertFalse(delayBeforeSendingMessage < 0, "DelayBeforeSendingMessage is less than 0!");
        assertFalse(cycleAmount <= 0, "AmountCycle is less than 0!");
        
        botSettings = new BotSettingsDefaultImpl(delayBeforeSendingMessage, cycleAmount);
    }
    
    @Test
    public void delayBeforeSendingMessage() {
        testBegin("BotSettings", "delayBeforeSendingMessage()");
        
        assertNotNull(botSettings, "BotSettings is null!");
        System.out.println("delayBeforeSendingMessage(): " + botSettings.delayBeforeSendingMessage());
        
        testEnd("BotSettings", "delayBeforeSendingMessage()");
    }
    
    @Test
    public void cycleAmount() {
        testBegin("BotSettings", "cycleAmount()");
        
        assertNotNull(botSettings, "BotSettings is null!");
        System.out.println("cycleAmount(): " + botSettings.cycleAmount());
        
        testEnd("BotSettings", "cycleAmount()");
    }
    
    @Test
    public void testCycle() {
        testBegin("BotSettings", "cycleAmount()");
        
        TestCycle testCycle = DataProviderCreator.createDataProvider(TestCycle.class);
        assertNotNull(testCycle, "TestCycle is null!");
        
        assertNotNull(botSettings, "BotSettings is null!");
        botSettings.setTestCycle(testCycle);
        
        System.out.println("testCycle(): " + botSettings.testCycle());
        
        testEnd("BotSettings", "cycleAmount()");
    }
}
