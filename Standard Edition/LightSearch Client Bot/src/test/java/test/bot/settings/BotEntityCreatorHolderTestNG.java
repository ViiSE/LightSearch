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

import lightsearch.client.bot.settings.BotEntityCreatorHolder;
import lightsearch.client.bot.settings.BotEntityCreatorHolderInit;
import lightsearch.client.bot.settings.BotSettingsType;
import static org.testng.Assert.*;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class BotEntityCreatorHolderTestNG {
    
    private final String SIMPLE   = BotSettingsType.SIMPLE.toString();
    private final String ADVANCED = BotSettingsType.ADVANCED.toString();
    
    private final String ip = "127.0.0.1";
    private final int port = 50000;
    private final int botAmount = 4;
    private final long delayMessageDisplay = 1;
    
    @Test
    public void get() {
        testBegin("BotEntityCreatorHolder", "get()");
        
        assertNotNull(ip, "IP is null!");
        assertFalse(port < 1024 || port > 65535, "Wrong port!");
        assertFalse(botAmount <= 0, "Bot amount is less or equals than 0!");
        assertFalse(delayMessageDisplay < 0, "Delay test.message display is less than 0!");
        
        BotEntityCreatorHolder botEntityCrHolder = BotEntityCreatorHolderInit.botEntityCreatorHolder(
                        botAmount, ip, port, delayMessageDisplay);
        assertNotNull(botEntityCrHolder, "BotEntityCreatorHolder is null!");
        
        
        System.out.println("get(SIMPLE): " + botEntityCrHolder.get(SIMPLE));
        System.out.println("get(ADVANCED): " + botEntityCrHolder.get(ADVANCED));
        
        testEnd("BotEntityCreatorHolder", "get()");
    }
}
