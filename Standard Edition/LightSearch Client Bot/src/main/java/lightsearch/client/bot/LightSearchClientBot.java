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

import lightsearch.client.bot.session.BotSession;
import lightsearch.client.bot.session.BotSessionInit;
import lightsearch.client.bot.settings.Configuration;
import lightsearch.client.bot.settings.ConfigurationInit;
import lightsearch.client.bot.settings.GlobalSettings;
import lightsearch.client.bot.settings.GlobalSettingsInit;

/**
 *
 * @author ViiSE
 */
public class LightSearchClientBot {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("LightSearch Client Bot, version 1.0. Welcome!");
        
        Configuration configuration;
        if(args.length == 0)
            configuration = ConfigurationInit.configuration("configuration.json");
        else
            configuration = ConfigurationInit.configuration(args[0]);
        
        GlobalSettings globalSettings = 
                GlobalSettingsInit.globalSettingsCreator(configuration.globalSettingsName());
        
        BotSession session = BotSessionInit.botSession(configuration.botSettingsName(), globalSettings, configuration.isPerformance());

        session.startSession();
    }
    
}
