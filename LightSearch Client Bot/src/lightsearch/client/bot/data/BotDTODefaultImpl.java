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
public class LightSearchClientBotDTODefaultImpl implements LightSearchClientBotDTO {
    
    private final String username;
    private final String IMEI;
    private final String cardCode;
    private final String userIdent;
    private final String botName;

    LightSearchClientBotDTODefaultImpl(String username, String IMEI, 
            String cardCode, String userIdent, String botName) {
        this.username  = username;
        this.IMEI      = IMEI;
        this.cardCode  = cardCode;
        this.userIdent = userIdent;
        this.botName   = botName;
    }

    @Override
    public String username() {
        return username;
    }

    @Override
    public String IMEI() {
        return IMEI;
    }

    @Override
    public String cardCode() {
        return cardCode;
    }

    @Override
    public String userIdent() {
        return userIdent;
    }

    @Override
    public String botName() {
        return botName;
    }
    
}
