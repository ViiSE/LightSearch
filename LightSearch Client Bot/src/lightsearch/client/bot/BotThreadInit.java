/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lightsearch.client.bot;

/**
 *
 * @author ViiSE
 */
public class BotThreadInit {
    
    public static BotThread botThread(BotEntity bot) {
        return new BotThreadDefaultImpl(bot);
    }
}