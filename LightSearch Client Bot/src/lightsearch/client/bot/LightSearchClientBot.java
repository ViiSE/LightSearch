/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lightsearch.client.bot;

import lightsearch.client.bot.session.BotSessionInit;
import lightsearch.client.bot.settings.GlobalSettingsInit;
import lightsearch.client.bot.session.BotSession;
import lightsearch.client.bot.settings.GlobalSettings;

/**
 *
 * @author ViiSE
 */
public class LightSearchClientBot {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        GlobalSettings globalSettings = 
                GlobalSettingsInit.lightSearchGlobalSettingsCreator("gloabal_settings.json");
        
        BotSession session = 
                BotSessionInit.lightSearchClientBotSession(globalSettings);
        
        int amountBot = 3;
        
        session.createSession(amountBot);
    }
    
}
