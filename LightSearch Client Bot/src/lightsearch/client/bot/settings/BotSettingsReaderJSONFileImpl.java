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
public class BotSettingsReaderJSONFileImpl implements BotSettingsReader {

    private final String TYPE       = BotSettingsEnum.TYPE.toString();
    private final String BOT_AMOUNT = BotSettingsEnum.BOT_AMOUNT.toString();
    
    private final String botSettingsType;
    private final int botAmount;
    private final JSONObject data;
    
    public BotSettingsReaderJSONFileImpl(String fileName) {
        try {
            SettingsReader reader = SettingsReaderInit.settingsReader(fileName);
            SettingsParser parser = SettingsParserInit.settingsParser();
            data = (JSONObject) parser.parse(reader.settingsContent());
            
            botSettingsType = data.get(TYPE).toString();
            botAmount       = Integer.parseInt(data.get(BOT_AMOUNT).toString());
        }
        catch(SettingsParserException ex) {
            throw new RuntimeException("Error " + fileName + " file: " + ex.getMessage());
        }
    }

    @Override
    public String type() {
        return botSettingsType;
    }

    @Override
    public int botAmount() {
        return botAmount;
    }

    @Override
    public Object data() {
        return data;
    }    
}
