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

import lightsearch.client.bot.data.BotDAO;
import lightsearch.client.bot.data.BotDAODefaultImpl;
import lightsearch.client.bot.data.ConnectionDTO;
import lightsearch.client.bot.exception.SocketException;
import lightsearch.client.bot.message.MessageRecipient;
import lightsearch.client.bot.message.MessageRecipientInit;
import lightsearch.client.bot.message.MessageSender;
import lightsearch.client.bot.message.MessageSenderInit;
import lightsearch.client.bot.processor.Processor;
import lightsearch.client.bot.processor.ProcessorClearAverageTimeDeafultImpl;
import lightsearch.client.bot.processor.ProcessorConnectionSystemDefaultImpl;
import lightsearch.client.bot.socket.SocketCreator;
import lightsearch.client.bot.socket.SocketCreatorInit;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author ViiSE
 */
public class BotsCheckerDefaultImpl implements BotsChecker {

    private final ConnectionDTO connDTO;
    
    public BotsCheckerDefaultImpl(ConnectionDTO connDTO) {
        this.connDTO = connDTO;
    }
    
    @SuppressWarnings("SleepWhileInLoop")
    @Override
    public void start() {
        boolean isWorked = true;
        while(isWorked) {
            try { Thread.sleep(100); } catch(InterruptedException ignored) {}
            if(BotsDoneSwitcher.isDone()) {
                SocketCreator socketCreator = SocketCreatorInit.socketCreator(connDTO);
                try(Socket socket = socketCreator.createSocket()) {
                    System.out.println("System test.bot:");
                    BotDAO botDAO = new BotDAODefaultImpl();
                    botDAO.setBotName("System");
                    
                    MessageSender msgSender = MessageSenderInit.messageSender(new DataOutputStream(socket.getOutputStream()));
                    MessageRecipient msgRecipient = MessageRecipientInit.messageRecipient(new DataInputStream(socket.getInputStream()));
                    Processor procConn = new ProcessorConnectionSystemDefaultImpl();
                    Processor procClear = new ProcessorClearAverageTimeDeafultImpl();
                    
                    procConn.apply(botDAO, msgSender, msgRecipient, 0);
                    procClear.apply(botDAO, msgSender, msgRecipient, 0);
                    
                    isWorked = false;
                }
                catch(IOException | SocketException ignored) { }
            }
        }
    }
    
}
