/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lightsearch.client.bot;

import lightsearch.client.bot.session.LightSearchClientBotSession;
import lightsearch.client.bot.session.LightSearchClientBotSessionInit;

/**
 *
 * @author ViiSE
 */
public class LightSearchClientBot {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        LightSearchClientBotSession session = 
                LightSearchClientBotSessionInit.lightSearchClientBotSession();
        
        int amountBot = 10;
        
        session.createSession(amountBot);
    }
    
}
