/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lightsearch.client.bot.settings;

/**
 *
 * @author ViiSE
 */
public class BotSettingsInit {
    
    public static BotSettings lightSearchClientBotSettings(String fileName) {
        return new BotSettingsJSONFileImpl(fileName);
    }
}