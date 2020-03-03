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
import lightsearch.client.bot.data.BotEntityDTO;
import lightsearch.client.bot.data.BotEntityDTODefaultImpl;
import lightsearch.client.bot.data.BotSettingsDTO;
import lightsearch.client.bot.exception.MessageRecipientException;
import lightsearch.client.bot.exception.MessageSenderException;
import lightsearch.client.bot.message.MessageRecipient;
import lightsearch.client.bot.message.MessageRecipientInit;
import lightsearch.client.bot.message.MessageSender;
import lightsearch.client.bot.message.MessageSenderInit;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import test.data.DataProviderCreator;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static org.testng.Assert.assertNotNull;
import static test.message.TestMessage.*;

/**
 *
 * @author ViiSE
 */
public class BotEntityTestNG {
    
    private BotEntity botEntity;
    private BotEntityDTO botEntityDTO;
    private boolean isExit = true;

    @BeforeClass
    @Parameters({"ip", "port", "delayMessageDisplay", "cycleAmount", "delayBeforeSendingMessage"})
    public void setUpClass(String ip, int port, long delayMessageDisplay, int cycleAmount, long delayBeforeSendingMessage) {
        Thread serverThread = new Thread(new TestServer(port));
        serverThread.start();
        
        BotDAO botDAO = DataProviderCreator.createDataProvider(BotDAO.class);
        BotSettingsDTO botSettingsDTO =
                DataProviderCreator.createDataProvider(BotSettingsDTO.class, cycleAmount, delayBeforeSendingMessage);

        Socket socket = DataProviderCreator.createDataProvider(Socket.class, ip, port);
        MessageSender msgSender = DataProviderCreator.createDataProvider(MessageSender.class, socket);
        MessageRecipient msgRecipient = DataProviderCreator.createDataProvider(MessageRecipient.class, socket);
        
        botEntityDTO =
                new BotEntityDTODefaultImpl(botDAO, socket, botSettingsDTO, msgSender, msgRecipient, delayMessageDisplay);
    }
    
    @Test
    public void run() {
        testBegin("BotEntity", "run()");
        
        assertNotNull(botEntityDTO, "BotEntityDTO is null!");
        botEntity = BotEntityInit.botEntity(botEntityDTO);
        assertNotNull(botEntity, "BotEntity is null!");
        
        Thread clientThread = new Thread(botEntity);
        clientThread.start();
        
        testEnd("BotEntity", "run()");
    }
    
    @AfterClass
    public void tearDownClass() {
        isExit = false;
        botEntity.destroy();
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
                Socket clientSock = sock.accept();
                System.out.println("CONNECT!");
                MessageRecipient msgRecipient = 
                        MessageRecipientInit.messageRecipient(new DataInputStream(clientSock.getInputStream()));
                
                MessageSender msgSender = MessageSenderInit.messageSender(new DataOutputStream(clientSock.getOutputStream()));
                
                //Excepted Connection Processor test.message
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
            } catch (IOException | MessageRecipientException | MessageSenderException ex) {
                catchMessage(ex);
            }
        }
        
    }
}
