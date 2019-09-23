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
package lightsearch.client.bot.data;

import lightsearch.client.bot.message.MessageRecipient;
import lightsearch.client.bot.message.MessageSender;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import test.data.DataProviderCreator;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static test.message.TestMessage.*;

/**
 *
 * @author ViiSE
 */
public class BotEntityDTOTestNG {
    
    private BotEntityDTO botEntityDTO;
    private volatile boolean isExit = true;
    
    @BeforeClass
    @Parameters({"delayMessageDisplay", "cycleAmount", "delayBeforeSendingMessage", "ip", "port"})
    public void setUpClass(long delayMessageDisplay, int cycleAmount, long delayBeforeSendingMessage, String ip, int port) {
        Thread thread = new Thread(new TestServer(port));
        thread.start();
        
        BotDAO botDAO = DataProviderCreator.createDataProvider(BotDAO.class);
        BotSettingsDTO botSettingsDTO =
                DataProviderCreator.createDataProvider(BotSettingsDTO.class, cycleAmount, delayBeforeSendingMessage);
        Socket socket = DataProviderCreator.createDataProvider(Socket.class, ip, port);
        assertNotNull(socket, "Socket is null!");
        MessageSender msgSender = DataProviderCreator.createDataProvider(MessageSender.class, socket);
        MessageRecipient msgRecipient = DataProviderCreator.createDataProvider(MessageRecipient.class, socket);
        
        botEntityDTO = new BotEntityDTODefaultImpl(botDAO, socket, botSettingsDTO, msgSender, msgRecipient, delayMessageDisplay);
    }
    
    @Test
    public void botDAO() {
        testBegin("BotEntityDTO", "botDAO()");
        
        assertNotNull(botEntityDTO.botDAO(), "BotDAO is null!");
        System.out.println("BotDAO: " + botEntityDTO.botDAO());
        
        testEnd("BotEntityDTO", "botDAO()");
    }
    
    @Test
    public void botSettingsDTO() {
        testBegin("BotEntityDTO", "botSettingsDTO()");
        
        assertNotNull(botEntityDTO.botSettingsDTO(), "BotSettingsDTO is null!");
        System.out.println("BotSettingsDTO: " + botEntityDTO.botSettingsDTO());
        
        testEnd("BotEntityDTO", "botSettingsDTO()");
    }
    
    @Test
    public void delayMessageDisplay() {
        testBegin("BotEntityDTO", "delayMessageDisplay()");

        assertFalse(botEntityDTO.delayMessageDisplay() < 0, "DelayMessageDisplay is less than 0!");
        System.out.println("DelayMessageDisplay: " + botEntityDTO.delayMessageDisplay());
        
        testEnd("BotEntityDTO", "DelayMessageDisplay()");
    }
    
    @Test
    public void messageRecipient() {
        testBegin("BotEntityDTO", "messageRecipient()");
        
        assertNotNull(botEntityDTO.messageRecipient(), "MessageRecipient is null!");
        System.out.println("MessageRecipient: " + botEntityDTO.messageRecipient());
        
        testEnd("BotEntityDTO", "messageRecipient()");
    }
    
    @Test
    public void messageSender() {
        testBegin("BotEntityDTO", "messageSender()");
        
        assertNotNull(botEntityDTO.messageSender(), "MessageSender is null!");
        System.out.println("MessageSender: " + botEntityDTO.messageSender());
        
        testEnd("BotEntityDTO", "messageSender()");
    }
    
    @Test
    public void socket() {
        testBegin("BotEntityDTO", "socket()");
        
        assertNotNull(botEntityDTO.socket(), "Socket is null!");
        System.out.println("Socket: " + botEntityDTO.socket());
        
        testEnd("BotEntityDTO", "socket()");
    }
    
    @AfterClass
    public void tearDownClass() {
        isExit = false;
    }

    private final class TestServer implements Runnable {

        private final int port;

        TestServer(int port) {
            this.port = port;
        }

        @Override
        public void run() {
            try(ServerSocket sock = new ServerSocket(port)) {
                System.out.println("Test server started.");
                sock.accept();
                System.out.println("CONNECT!");
                while(isExit) {}
                System.out.println("Server shutdown.");
            } catch (IOException ex) {
                catchMessage(ex);
            }
        }
        
    }
}
