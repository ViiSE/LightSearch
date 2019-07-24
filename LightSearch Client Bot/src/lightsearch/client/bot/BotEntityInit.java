/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lightsearch.client.bot;

import java.net.Socket;
import lightsearch.client.bot.settings.BotSettings;

/**
 *
 * @author ViiSE
 */
public class BotEntityInit {
    
    public static BotEntity lightSearchClientBotEntity(Socket socket, 
            BotSettings settings) {
        return new BotEntityDefaultImpl(socket, settings);
    }
}
