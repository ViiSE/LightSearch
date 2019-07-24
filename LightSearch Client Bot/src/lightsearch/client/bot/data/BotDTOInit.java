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
public class BotDTOInit {
    
    public static BotDTO lightSearchClientBotDTO(String username,
            String IMEI, String cardCode, String userIdent, String botName) {
        return new BotDTODefaultImpl(username, IMEI, cardCode, 
                userIdent, botName);
    }
}
