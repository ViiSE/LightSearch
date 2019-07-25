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
package lightsearch.client.bot;

import java.util.ArrayList;
import java.util.List;
import lightsearch.client.bot.settings.BotSettingsReader;
import lightsearch.client.bot.settings.BotSettingsType;
import org.json.simple.JSONObject;

/**
 *
 * @author ViiSE
 */
public class BotEntityCreatorJSONImpl implements BotEntityCreator {

    private final String SIMPLE   = BotSettingsType.SIMPLE.toString();
    private final String ADVANCED = BotSettingsType.ADVANCED.toString();
    
    private final String type;
    private final JSONObject data;
    private final 
    
    private final BotSettingsReader settingsReader;
    
    public BotEntityCreatorJSONImpl(BotSettingsReader settingsReader) {
        type      = settingsReader.type();
        botAmount = settingsReader.botAmount();
        data      = (JSONObject) settingsReader.data();
    }

    @Override
    public List<BotEntity> botList() {
        if(type.equals(SIMPLE)) {
            List<BotEntity> bots = new ArrayList<>();
            for(int i = 0; i < botAmount; i++) {
                
            }
        }
        else if(type.equals(ADVANCED)) {
            
        }
        else
            throw new RuntimeException("Unknown type: " + type);
    }
    
}
