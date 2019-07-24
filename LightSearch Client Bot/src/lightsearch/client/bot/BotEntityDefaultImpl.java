/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lightsearch.client.bot;

import java.io.IOException;
import java.net.Socket;
import lightsearch.client.bot.exception.TestCycleOutOfBoundException;
import lightsearch.client.bot.settings.BotSettings;

/**
 *
 * @author ViiSE
 */
public class BotEntityDefaultImpl implements BotEntity {

    private final Socket socket;
    private final TestCycle testCycle;
    private final int amountCycle;
    private final long delayBeforeSendingMessage;
    private boolean isFinish = false;
    
    public BotEntityDefaultImpl(Socket socket, BotSettings settings) {
        this.socket               = socket;
        testCycle                 = settings.testCycle();
        amountCycle               = settings.amountCycle();
        delayBeforeSendingMessage = settings.delayBeforeSendingMessage();
    }

    @Override
    public void run() {
        for(int i = 0; i < amountCycle; i++) {
            boolean done = true;
            while(done) {
                try {
                    testCycle.next().apply();
                    if(amountCycle != 0) Thread.sleep(delayBeforeSendingMessage);
                }
                catch(InterruptedException ignore) {}
                catch(TestCycleOutOfBoundException ex) { 
                    System.out.println(ex.getMessage());
                    done = false;
                }
            }
        }
    }

    @Override
    public void destroy() {
        if(!isFinish) {
            try { socket.close(); } catch(IOException ignore) {}
            isFinish = true;
        }
    }
    
}
