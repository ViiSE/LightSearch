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

import lightsearch.client.bot.TestCycleCreator;
import lightsearch.client.bot.TestCycleCreatorInit;
import lightsearch.client.bot.settings.BotSettingsEnum;
import lightsearch.client.bot.settings.BotSettingsReader;
import lightsearch.client.bot.settings.BotSettingsReaderInit;
import org.json.simple.JSONObject;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class TestCycleCreatorTestNG {
    
    private final String CYCLE_CONTENT = BotSettingsEnum.CYCLE_CONTENT.toString();
    private JSONObject data;
    
    @BeforeClass
    public void setUpClass() {
        String fileName = "bot_settings_simple_test.json";
        assertNotNull(fileName, "File name is null!");
        
        BotSettingsReader botSettingsReader = BotSettingsReaderInit.botSettingsReader(fileName);
        data = (JSONObject) botSettingsReader.data();
    }
    
    @Test
    public void createCycle() {
        testBegin("TestCycleCreator", "createCycle()");
        
        assertNotNull(data, "Data is not null!");
        
        TestCycleCreator testCycleCreator = TestCycleCreatorInit.testCycleCreator(data.get(CYCLE_CONTENT));
        System.out.println("createCycle(): " + testCycleCreator.createCycle());
        
        testEnd("TestCycleCreator", "createCycle()");
    }
}
