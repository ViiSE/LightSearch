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
package test.bot.processor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import lightsearch.client.bot.data.BotDAO;
import lightsearch.client.bot.data.BotDAODefaultImpl;
import lightsearch.client.bot.exception.MessageRecipientException;
import lightsearch.client.bot.exception.MessageSenderException;
import lightsearch.client.bot.message.MessageRecipient;
import lightsearch.client.bot.message.MessageRecipientInit;
import lightsearch.client.bot.message.MessageSender;
import lightsearch.client.bot.message.MessageSenderInit;
import lightsearch.client.bot.processor.Processor;
import lightsearch.client.bot.processor.ProcessorAuthorizationDefaultImpl;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class ProcessorAuthorizationDefaultImplTestNG {
    
    private BotDAO botDAO;
    private MessageSender msgSender;
    private MessageRecipient msgRecipient;
    private final long delayMessageDisplay = 1;
    
    @BeforeClass
    public void setUpClass() {
        Thread serverThread = new Thread(new TestServer());
        serverThread.start();
        
        botDAO = getBotDAO();
        Socket socket = getSocket();
        msgSender = getMessageSender(socket);
        msgRecipient = getMessageRecipient(socket);
    }
    
    @Test
    public void apply() {
        testBegin("ProcessorAuthorizationDefaultImpl", "apply()");
        
        assertNotNull(botDAO, "BotDAO is null!");
        assertNotNull(msgSender, "MessageSender is null!");
        assertNotNull(msgRecipient, "MessageRecipient is null!");
        assertFalse(delayMessageDisplay < 0, "DelayMessageDisplay is less than 0!");
        Processor proc = new ProcessorAuthorizationDefaultImpl();
        proc.apply(botDAO, msgSender, msgRecipient, delayMessageDisplay);
        
        testEnd("ProcessorAuthorizationDefaultImpl", "apply()");
    }
    
    private BotDAO getBotDAO() {
        BotDAO _botDAO = new BotDAODefaultImpl();
        _botDAO.setBotName("Super bot");
        _botDAO.setCardCode("111");
        _botDAO.setIMEI("123456789012345");
        _botDAO.setPassword("password");
        _botDAO.setUserIdent("001");
        _botDAO.setUsername("user");
        
        return _botDAO;
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
                Socket clientSock = sock.accept();
                System.out.println("CONNECT!");
                MessageRecipient msgRecipient = 
                        MessageRecipientInit.messageRecipient(
                                new DataInputStream(clientSock.getInputStream()));
                
                MessageSender msgSender = 
                        MessageSenderInit.messageSender(
                                new DataOutputStream(clientSock.getOutputStream()));
                
                // Excepted Authentication Processor message with 
                // IMEI 123456789012345 and ident 001
                String answer = "{"
                            + "\"IMEI\": \"123456789012345\","
                            + "\"is_done\": \"true\","
                            + "\"message\": \"Connection established!\","
                            + "\"ident\": \"001\","
                            + "\"TK_list\": [\"TK1\", \"TK2\"],"
                            + "\"sklad_list\": [\"sklad1\"]"
                        + "}";
                System.out.println("Client send: " + msgRecipient.acceptMessage());
                msgSender.sendMessage(answer);
                
                Thread.sleep(5000);
                
                System.out.println("Server shutdown.");
            } catch (IOException ex) {
                throw new RuntimeException("Cannot create test server. Exception: " + ex.getMessage());
            } catch (MessageRecipientException ex) {
                throw new RuntimeException("Cannot read client message. Exception: " + ex.getMessage());
            } catch (MessageSenderException ex) {
                throw new RuntimeException("Cannot send client message. Exception: " + ex.getMessage());
            }
            catch (InterruptedException ignore) { }
        }
        
    }
}
