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
package test.bot.processor;

import lightsearch.client.bot.processor.BotEntityProcessor;
import lightsearch.client.bot.processor.BotEntityProcessorSimpleJSON;
import lightsearch.client.bot.settings.BotSettingsReader;
import lightsearch.client.bot.settings.BotSettingsReaderInit;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class BotEntityProcessorSimpleJSONTestNG {
    
    private final String botSettingsFileName = "bot_settings_simple_test.json";
    private final int botAmount = 2;
    private final String ip = "127.0.0.1";
    private final int port = 50000;
    private final long delayMessageDisplay = 5;
    private Object data;
    
    @BeforeClass
    public void setUpClass() {
        BotSettingsReader botSettingsReader = BotSettingsReaderInit.botSettingsReader(botSettingsFileName);
        data = botSettingsReader.data();
    }
    
    //To start this test run LightSearch Server
    @Test
    public void apply() {
        testBegin("BotEntityProcessorSimpleJSONTestNG", "apply()");
        
        assertNotNull(data, "Data is null!");
        assertFalse(botAmount < 0, "Bot amount is less than 0!");
        assertNotNull(ip, "IP is null!");
        assertFalse(port < 1023 || port > 65535, "Wrong port!");
        assertFalse(delayMessageDisplay < 0, "DelayMessageDisplay is null!");
        
        BotEntityProcessor botProc = new BotEntityProcessorSimpleJSON(
                        botAmount, ip, port, delayMessageDisplay);
        assertNotNull(botProc, "BotEntityProcessor is null!");
        
        botProc.apply(data);
        
        testEnd("BotEntityProcessorSimpleJSONTestNG", "apply()");
    }
}
