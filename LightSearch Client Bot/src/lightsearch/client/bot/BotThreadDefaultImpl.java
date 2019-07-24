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
public class BotThreadDefaultImpl implements BotThread {

    private final BotEntity bot;
    
    public BotThreadDefaultImpl(BotEntity bot) {
        this.bot = bot;
    }

    @Override
    public void start() {
        Thread thread = new Thread(bot);
        thread.start();
    }
    
}
