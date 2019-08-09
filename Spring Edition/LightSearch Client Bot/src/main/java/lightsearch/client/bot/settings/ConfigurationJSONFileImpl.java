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

import lightsearch.client.bot.constants.ConfigurationName;
import lightsearch.client.bot.exception.SettingsParserException;
import lightsearch.client.bot.parser.SettingsParser;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service("configurationJSONFile")
@Scope("prototype")
public class ConfigurationJSONFileImpl implements Configuration {

    private final String SETTINGS_READER = "settingsReaderFile";
    private final String SETTINGS_PARSER = "settingsParserJSON";

    private final String GLOBAL_SETTINGS = ConfigurationName.GLOBAL_SETTINGS;
    private final String BOT_SETTINGS    = ConfigurationName.BOT_SETTINGS;
    private final String PERFORMANCE     = ConfigurationName.PERFORMANCE;

    private String configurationName;
    private String globalSettingsName;
    private String botSettingsName;
    private boolean isPerformance;

    @Autowired
    private ApplicationContext ctx;

    private boolean isRead = false;

    public ConfigurationJSONFileImpl(@Value("configuration.json") String configurationName) {
        this.configurationName = configurationName;
    }

    public void read() {
        SettingsReader settingsReader = (SettingsReader) ctx.getBean(SETTINGS_READER, configurationName);
        String content = settingsReader.settingsContent();
        SettingsParser parser = ctx.getBean(SETTINGS_PARSER, SettingsParser.class);
        try {
            JSONObject jObj = (JSONObject)parser.parse(content);
            globalSettingsName = jObj.get(GLOBAL_SETTINGS).toString();
            botSettingsName    = jObj.get(BOT_SETTINGS).toString();
            isPerformance = Boolean.valueOf(jObj.get(PERFORMANCE).toString());
        }
        catch(SettingsParserException ex) {
            throw new RuntimeException("Error in file " + configurationName + ".json. "
                    + "Exception: " + ex.getMessage());
        }
        isRead = true;
    }

    @Override
    public String globalSettingsName() {
         if(!isRead) read();
         return globalSettingsName;
    }

    @Override
    public String botSettingsName() {
        if(!isRead) read();
        return botSettingsName;
    }

    @Override
    public boolean isPerformance() {
        if(!isRead) read();
        return isPerformance;
    }
}
