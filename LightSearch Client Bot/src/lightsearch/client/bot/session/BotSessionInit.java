/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lightsearch.client.bot.session;

import lightsearch.client.bot.settings.GlobalSettings;

/**
 *
 * @author ViiSE
 */
public class BotSessionInit {
    
    public static BotSession lightSearchClientBotSession(
            GlobalSettings globalSettings) {
        return new BotSessionDefaultImpl(globalSettings);
    }
    
}
