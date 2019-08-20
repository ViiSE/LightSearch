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
package test.bot.data;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import lightsearch.client.bot.TestCycle;
import lightsearch.client.bot.TestCycleInit;
import lightsearch.client.bot.data.BotDAO;
import lightsearch.client.bot.data.BotDAODefaultImpl;
import lightsearch.client.bot.data.BotEntityDTO;
import lightsearch.client.bot.data.BotEntityDTODefaultImpl;
import lightsearch.client.bot.data.BotSettingsDTO;
import lightsearch.client.bot.data.BotSettingsDTOInit;
import lightsearch.client.bot.message.MessageRecipient;
import lightsearch.client.bot.message.MessageRecipientInit;
import lightsearch.client.bot.message.MessageSender;
import lightsearch.client.bot.message.MessageSenderInit;
import lightsearch.client.bot.processor.Processor;
import lightsearch.client.bot.processor.ProcessorAuthorizationDefaultImpl;
import lightsearch.client.bot.processor.ProcessorConnectionDefaultImpl;
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class BotEntityDTOTestNG {
    
    private BotEntityDTO botEntityDTO;
    private boolean isExit = true;
    
    @BeforeClass
    public void setUpClass() {
        Thread thread = new Thread(new TestServer());
        thread.start();
        
        BotDAO botDAO = getBotDAO();
        BotSettingsDTO botSettingsDTO = getBotSettingsDTO();
        long delayMessageDisplay = 1;
        Socket socket = getSocket();
        MessageSender msgSender = getMessageSender(socket);
        MessageRecipient msgRecipient = getMessageRecipient(socket);
        
        botEntityDTO = new BotEntityDTODefaultImpl(botDAO, socket, botSettingsDTO, 
                msgSender, msgRecipient, delayMessageDisplay);
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
        
        assertNotNull(botEntityDTO.delayMessageDisplay(), "DelayMessageDisplay is null!");
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
    
    private BotDAO getBotDAO() {
        BotDAO botDAO = new BotDAODefaultImpl();
        botDAO.setBotName("Super test.bot");
        botDAO.setCardCode("111");
        botDAO.setIMEI("123456789012345");
        botDAO.setPassword("password");
        botDAO.setUserIdent("001");
        botDAO.setUsername("user");
        
        return botDAO;
    }
    
    private BotSettingsDTO getBotSettingsDTO() {
        List<Processor> processors = new ArrayList<>();
        processors.add(new ProcessorConnectionDefaultImpl());
        processors.add(new ProcessorAuthorizationDefaultImpl());
        
        TestCycle testCycle = TestCycleInit.testCycle(processors);
        
        int cycleAmount = 1;
        long delayBeforeSendingMessage = 3;
        
        return BotSettingsDTOInit.botSettingsDTO(testCycle, cycleAmount, delayBeforeSendingMessage);
    }
    
    private Socket getSocket() {
        try {
            return new Socket("127.0.0.1", 50000);
        } catch (IOException ex) {
            throw new RuntimeException("Cannot create client socket.");
        }
    }
    
    private MessageSender getMessageSender(Socket socket) {
        try {
            return MessageSenderInit.messageSender(new DataOutputStream(socket.getOutputStream()));
        } catch (IOException ex) {
            throw new RuntimeException("Cannot create MessageSender instance. Exception: " + ex.getMessage());
        }
    }
    
    private MessageRecipient getMessageRecipient(Socket socket) {
        try {
            return MessageRecipientInit.messageRecipient(new DataInputStream(socket.getInputStream()));
        } catch (IOException ex) {
            throw new RuntimeException("Cannot create MessageSender instance. Exception: " + ex.getMessage());
        }
    }
    
    private final class TestServer implements Runnable {

        @Override
        public void run() {
            try(ServerSocket sock = new ServerSocket(50000)) {
                System.out.println("Test server started.");
                sock.accept();
                System.out.println("CONNECT!");
                while(isExit) {}
                System.out.println("Server shutdown.");
            } catch (IOException ex) {
                throw new RuntimeException("Cannot create test server.");
            }
        }
        
    }
}
