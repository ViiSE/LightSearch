/* 
 * Copyright 2019 ViiSE.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package lightsearch.client.bot;

import java.io.IOException;
import java.net.Socket;
import lightsearch.client.bot.data.BotDAO;
import lightsearch.client.bot.data.BotEntityDTO;
import lightsearch.client.bot.exception.TestCycleOutOfBoundException;
import lightsearch.client.bot.message.MessageRecipient;
import lightsearch.client.bot.message.MessageSender;

/**
 *
 * @author ViiSE
 */
public class BotEntityDefaultImpl implements BotEntity {

    private final Socket socket;
    private final TestCycle testCycle;
    private final int amountCycle;
    private final long delayBeforeSendingMessage;
    
    private final BotDAO botDAO;
    private final MessageSender messageSender;
    private final MessageRecipient messageRecipient;
    private final long delayMessageDisplay;
    
    private boolean isFinish = false;
    
    public BotEntityDefaultImpl(BotEntityDTO botEntityDTO) {
        this.socket               = botEntityDTO.socket();
        testCycle                 = botEntityDTO.botSettingsDTO().testCycle();
        amountCycle               = botEntityDTO.botSettingsDTO().cycleAmount();
        delayBeforeSendingMessage = botEntityDTO.botSettingsDTO().delayBeforeSendingMessage();
        
        botDAO                    = botEntityDTO.botDAO();
        messageSender             = botEntityDTO.messageSender();
        messageRecipient          = botEntityDTO.messageRecipient();
        delayMessageDisplay       = botEntityDTO.delayMessageDisplay();        
    }

    @Override
    public void run() {
        for(int i = 0; i < amountCycle; i++) {
            boolean done = true;
            while(done) {
                try {
                    testCycle.next().apply(botDAO, messageSender, messageRecipient, delayMessageDisplay);
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
