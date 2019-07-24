/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lightsearch.client.bot.data;

/**
 *
 * @author ViiSE
 */
public class LightSearchClientBotDTOInit {
    
    public static LightSearchClientBotDTO lightSearchClientBotDTO(String username,
            String IMEI, String cardCode, String userIdent, String botName) {
        return new LightSearchClientBotDTODefaultImpl(username, IMEI, cardCode, 
                userIdent, botName);
    }
}
