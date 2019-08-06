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
package test.bot;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import lightsearch.client.bot.BotEntity;
import lightsearch.client.bot.BotEntityInit;
import lightsearch.client.bot.TestCycle;
import lightsearch.client.bot.TestCycleInit;
import lightsearch.client.bot.data.BotDAO;
import lightsearch.client.bot.data.BotDAODefaultImpl;
import lightsearch.client.bot.data.BotEntityDTO;
import lightsearch.client.bot.data.BotEntityDTODefaultImpl;
import lightsearch.client.bot.data.BotSettingsDTO;
import lightsearch.client.bot.data.BotSettingsDTOInit;
import lightsearch.client.bot.exception.MessageRecipientException;
import lightsearch.client.bot.exception.MessageSenderException;
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
public class BotEntityTestNG {
    
    private BotEntity botEntity;
    private BotEntityDTO botEntityDTO;
    private boolean isExit = true;
    
    @BeforeClass
    public void setUpClass() {   
        Thread serverThread = new Thread(new TestServer());
        serverThread.start();
        
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
    public void run() {
        testBegin("BotEntity", "run()");
        
        assertNotNull(botEntityDTO, "BotEntityDTO is null!");
        botEntity = BotEntityInit.botEntity(botEntityDTO);
        assertNotNull(botEntity, "BotEntity is null!");
        
        Thread clientThread = new Thread(botEntity);
        clientThread.run();
        
        testEnd("BotEntity", "run()");
    }
    
    @AfterClass
    public void tearDownClass() {
        isExit = false;
        botEntity.destroy();
    }
    
    private BotDAO getBotDAO() {
        BotDAO botDAO = new BotDAODefaultImpl();
        botDAO.setBotName("Super bot");
        botDAO.setCardCode("001");
        botDAO.setIMEI("111111111111111");
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
            throw new RuntimeException("Cannot create MessageRecipient instance. Exception: " + ex.getMessage());
        }
    }
    
    private final class TestServer implements Runnable {

        @Override
        public void run() {
            try(ServerSocket sock = new ServerSocket(50000)) {
                System.out.println("Test server started.");
                Socket clientSock = sock.accept();
                System.out.println("CONNECT!");
                MessageRecipient msgRecipient = 
                        MessageRecipientInit.messageRecipient(
                                new DataInputStream(clientSock.getInputStream()));
                
                MessageSender msgSender = 
                        MessageSenderInit.messageSender(
                                new DataOutputStream(clientSock.getOutputStream()));
                
                //Excepted Connection Processor message
                String answer = "OK";
                System.out.println("Client send: " + msgRecipient.acceptMessage());
                msgSender.sendMessage(answer);
                System.out.println("Server send: " + answer);
                
                // Excepted Authentication Processor message with 
                // IMEI 111111111111111 and ident 001
                answer = "{"
                            + "\"IMEI\": \"111111111111111\","
                            + "\"is_done\": \"true\","
                            + "\"message\": \"Connection established!\","
                            + "\"TK_list\": [\"TK1\", \"TK2\"],"
                            + "\"sklad_list\": [\"sklad1\"]"
                        + "}";
                System.out.println("Client send: " + msgRecipient.acceptMessage());
                msgSender.sendMessage(answer);
                
                while(isExit) {}
                
                System.out.println("Server shutdown.");
            } catch (IOException ex) {
                throw new RuntimeException("Cannot create test server. Exception: " + ex.getMessage());
            } catch (MessageRecipientException ex) {
                throw new RuntimeException("Cannot read client message. Exception: " + ex.getMessage());
            } catch (MessageSenderException ex) {
                throw new RuntimeException("Cannot send client message. Exception: " + ex.getMessage());
            }
        }
        
    }
}
