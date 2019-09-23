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
package lightsearch.client.bot.parser;

import lightsearch.client.bot.exception.SettingsParserException;
import lightsearch.client.bot.parser.SettingsParser;
import lightsearch.client.bot.parser.SettingsParserInit;
import lightsearch.client.bot.settings.GlobalSettingsEnum;
import org.json.simple.JSONObject;
import static org.testng.Assert.*;
import static test.message.TestMessage.*;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 *
 * @author ViiSE
 */
public class SettingsParserTestNG {
    
    private final String IP   = GlobalSettingsEnum.SERVER_IP.toString();
    private final String PORT = GlobalSettingsEnum.SERVER_PORT.toString();
    private final String DELAY_MESSAGE_DISPLAY = GlobalSettingsEnum.DELAY_MESSAGE_DISPLAY.toString();
    
    @Test
    @Parameters({"globalSettingsRaw"})
    public void parse(String glSettings) {
        testBegin("SettingsParser", "parse()");
        
        try {
            assertNotNull(glSettings, "GlobalSettings is null!");
            SettingsParser settingsParser = SettingsParserInit.settingsParser();
            Object settings = settingsParser.parse(glSettings);
            System.out.println("Settings: " + settings);
            
            JSONObject jSettings = (JSONObject) settings;
            System.out.println("settings: server_ip   = " + jSettings.get(IP));
            System.out.println("settings: server_port = " + jSettings.get(PORT));
            System.out.println("settings: server_delayMessage_display = " + jSettings.get(DELAY_MESSAGE_DISPLAY));
        } catch(SettingsParserException ex) {
            catchMessage(ex);
        }
        
        testEnd("SettingsParser", "parse()");
    }
}
