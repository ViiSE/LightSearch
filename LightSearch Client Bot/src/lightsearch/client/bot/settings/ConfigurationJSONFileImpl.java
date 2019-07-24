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
import lightsearch.client.bot.parser.SettingsParser;
import lightsearch.client.bot.parser.SettingsParserInit;
import org.json.simple.JSONObject;

/**
 *
 * @author ViiSE
 */
public class ConfigurationJSONFileImpl implements Configuration {
    
    private final String GLOBAL_SETTINGS = ConfigurationEnum.GLOBAL_SETTINGS.toString();
    private final String BOT_SETTINGS    = ConfigurationEnum.BOT_SETTINGS.toString();
    
    private final String globalSettingsName;
    private final String botSettingsName;
    
    public ConfigurationJSONFileImpl(String configurationName) {
        SettingsReader settingsReader = SettingsReaderInit.settingsReader(configurationName);
        String content = settingsReader.settingsContent();
        SettingsParser parser = SettingsParserInit.settingsParser();
        try {
            JSONObject jObj = (JSONObject)parser.parse(content);
            globalSettingsName = jObj.get(GLOBAL_SETTINGS).toString();
            botSettingsName    = jObj.get(BOT_SETTINGS).toString();
        }
        catch(SettingsParserException ex) {
            throw new RuntimeException("Error in file " + configurationName + ".json. "
                    + "Exception: " + ex.getMessage());
        }
    }

    @Override
    public String globalSettingsName() {
        return globalSettingsName;
    }

    @Override
    public String botSettingsName() {
        return botSettingsName;
    }
    
}
