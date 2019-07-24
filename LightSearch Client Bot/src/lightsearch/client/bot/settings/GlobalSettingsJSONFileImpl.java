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

import lightsearch.client.bot.exception.SettingsParserException;
import lightsearch.client.bot.parser.SettingsParserInit;
import org.json.simple.JSONObject;
import lightsearch.client.bot.parser.SettingsParser;

/**
 *
 * @author ViiSE
 */
public class GlobalSettingsJSONFileImpl implements GlobalSettings {

    private final String SERVER_IP             = GlobalSettingsEnum.SERVER_IP.toString();
    private final String SERVER_PORT           = GlobalSettingsEnum.SERVER_PORT.toString();
    private final String DELAY_MESSAGE_DISPLAY = GlobalSettingsEnum.DELAY_MESSAGE_DISPLAY.toString();
    
    private final String serverIP;
    private final int serverPort;
    private final long delayMessageDisplay;
    
    public GlobalSettingsJSONFileImpl(String fileName) {
        try {
            SettingsReader reader = SettingsReaderInit.settingsReader(fileName);
            
            SettingsParser parser = SettingsParserInit.settingsParser();
            JSONObject jObj = (JSONObject) parser.parse(reader.settingsContent());

            serverIP            = jObj.get(SERVER_IP).toString();
            serverPort          = Integer.parseInt(jObj.get(SERVER_PORT).toString());
            delayMessageDisplay = Integer.parseInt(jObj.get(DELAY_MESSAGE_DISPLAY).toString());
        } catch(SettingsParserException ex) {
            throw new RuntimeException("Error in file " + fileName + ".json. "
                    + "Exception: " + ex.getMessage());
        }
    }

    @Override
    public long delayMessageDisplay() {
        return delayMessageDisplay;
    }

    @Override
    public String serverIP() {
        return serverIP;
    }

    @Override
    public int serverPort() {
        return serverPort;
    }   
}