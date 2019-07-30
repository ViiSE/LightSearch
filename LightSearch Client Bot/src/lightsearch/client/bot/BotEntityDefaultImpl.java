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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import lightsearch.client.bot.data.BotDAO;
import lightsearch.client.bot.data.BotEntityDTO;
import lightsearch.client.bot.exception.TestCycleOutOfBoundException;
import lightsearch.client.bot.message.MessageRecipient;
import lightsearch.client.bot.message.MessageRecipientInit;
import lightsearch.client.bot.message.MessageSender;
import lightsearch.client.bot.message.MessageSenderInit;

/**
 *
 * @author ViiSE
 */
public class BotEntityDefaultImpl implements BotEntity {

    private Socket socket;
    private final TestCycle testCycle;
    private final int cycleAmount;
    private final long delayBeforeSendingMessage;
    
    private final BotDAO botDAO;
    private MessageSender messageSender;
    private MessageRecipient messageRecipient;
    private final long delayMessageDisplay;
    
    private boolean isFinish = false;
    
    public BotEntityDefaultImpl(BotEntityDTO botEntityDTO) {
        this.socket               = botEntityDTO.socket();
        testCycle                 = botEntityDTO.botSettingsDTO().testCycle();
        cycleAmount               = botEntityDTO.botSettingsDTO().cycleAmount();
        delayBeforeSendingMessage = botEntityDTO.botSettingsDTO().delayBeforeSendingMessage();
        
        botDAO                    = botEntityDTO.botDAO();
        messageSender             = botEntityDTO.messageSender();
        messageRecipient          = botEntityDTO.messageRecipient();
        delayMessageDisplay       = botEntityDTO.delayMessageDisplay();        
    }

    @Override
    public void run() {
        for(int i = 0; i < cycleAmount; i++) {
            boolean done = true;
            while(done) {
                try {
                    testCycle.next().apply(botDAO, messageSender, messageRecipient, delayMessageDisplay);
                    if(cycleAmount != 0) Thread.sleep(delayBeforeSendingMessage);
                }
                catch(InterruptedException ignore) {}
                catch(TestCycleOutOfBoundException ex) {
                    InetAddress address = socket.getInetAddress();
                    int port = socket.getPort();
                    try { socket.close(); }
                    catch(IOException closeEx) {
                        throw new RuntimeException("Cannot close socket. Exception: " + closeEx.getMessage());
                    }
                    try {
                        socket = new Socket(address, port);
                        messageSender    = MessageSenderInit.messageSender(new DataOutputStream(socket.getOutputStream()));
                        messageRecipient = MessageRecipientInit.messageRecipient(new DataInputStream(socket.getInputStream()));
                    }
                    catch(IOException connectEx) {
                        throw new RuntimeException("Cannot connect socket. Exception: " + connectEx.getMessage());
                    }
                    
                    System.out.println(ex.getMessage());
                    done = false;
                }
            }
        }
        destroy();
    }

    @Override
    public void destroy() {
        if(!isFinish) {
            try { socket.close(); } catch(IOException ignore) {}
            isFinish = true;
        }
    }
}
