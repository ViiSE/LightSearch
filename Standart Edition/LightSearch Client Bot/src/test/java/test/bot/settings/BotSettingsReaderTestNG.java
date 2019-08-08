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
package test.bot.settings;

import lightsearch.client.bot.settings.BotSettingsReader;
import lightsearch.client.bot.settings.BotSettingsReaderInit;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static test.ResourcesFilesPath.getResourcesFilesPath;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class BotSettingsReaderTestNG {
    
    private BotSettingsReader botSettingsReader;
        
    
    @BeforeClass
    public void setUpClass() {
        String fileName = getResourcesFilesPath() + "bot_settings_simple.json";
        assertNotNull(fileName, "FileName is null!");
        
        botSettingsReader = BotSettingsReaderInit.botSettingsReader(fileName);
    }
    
    @Test
    public void type() {
        testBegin("BotSettingsReader", "type()");
        
        assertNotNull(botSettingsReader, "BotSettingsReader is null!");
        System.out.println("type(): " + botSettingsReader.type());
        
        testEnd("BotSettingsReader", "type()");
    }
    
    @Test
    public void botAmount() {
        testBegin("BotSettingsReader", "botAmount()");
        
        assertNotNull(botSettingsReader, "BotSettingsReader is null!");
        System.out.println("botAmount(): " + botSettingsReader.botAmount());
        
        testEnd("BotSettingsReader", "botAmount()");
    }
    
    @Test
    public void data() {
        testBegin("BotSettingsReader", "data()");
        
        assertNotNull(botSettingsReader, "BotSettingsReader is null!");
        System.out.println("data(): " + botSettingsReader.data());
        
        testEnd("BotSettingsReader", "data()");
    }
}
