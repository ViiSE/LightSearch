/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lightsearch.client.bot;

import java.net.Socket;
import lightsearch.client.bot.settings.LightSearchClientBotSettings;

/**
 *
 * @author ViiSE
 */
public class LightSearchClientBotEntityInit {
    
    public static LightSearchClientBotEntity lightSearchClientBotEntity(Socket socket, 
            LightSearchClientBotSettings settings) {
        return new LightSearchClientBotEntityDefaultImpl(socket, settings);
    }
}
