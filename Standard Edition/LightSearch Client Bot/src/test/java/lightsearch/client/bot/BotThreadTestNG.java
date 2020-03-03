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

import lightsearch.client.bot.exception.MessageRecipientException;
import lightsearch.client.bot.exception.MessageSenderException;
import lightsearch.client.bot.message.MessageRecipient;
import lightsearch.client.bot.message.MessageRecipientInit;
import lightsearch.client.bot.message.MessageSender;
import lightsearch.client.bot.message.MessageSenderInit;
import lightsearch.client.bot.settings.BotSettingsReader;
import lightsearch.client.bot.settings.BotSettingsReaderInit;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import test.TestUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static test.ResourcesFilesPath.getResourcesFilesPath;
import static test.message.TestMessage.*;

/**
 *
 * @author ViiSE
 */
public class BotThreadTestNG {
    
    private String botSettingsReaderFileName;
    private BotSettingsReader settingsReader;
    
    @BeforeClass
    @Parameters({"port"})
    public void setUpClass(int serverPort) {
        Thread serverThread = new Thread(new TestServer(serverPort));
        serverThread.start();
        
        botSettingsReaderFileName = getResourcesFilesPath() + "bot_settings_simple_test.json";
        settingsReader = BotSettingsReaderInit.botSettingsReader(botSettingsReaderFileName);
    }
    
    @Test
    @Parameters({"ip", "port", "delayMessageDisplay"})
    public void start(String serverIP, int serverPort, long delayMessageDisplay) {
        testBegin("BotThread", "start()");
        
        assertNotNull(botSettingsReaderFileName, "BotSettingsReader file name is null!");
        assertNotNull(settingsReader, "BotSettingsReader is null!");
        
        assertNotNull(serverIP, "ServerIP is null!");
        assertFalse(serverPort < 1024 || serverPort > 65535, "Wrong port!");
        assertFalse(delayMessageDisplay < 0, "DelayMessageDisplay is less than 0!");
        
        BotEntityCreator bEnCr = BotEntityCreatorInit.botEntityCreator(settingsReader, serverIP, serverPort, delayMessageDisplay);
        assertNotNull(bEnCr, "BotEntityCreator is null!");
        
        BotThread botThread = BotThreadInit.botThread(bEnCr.botList().get(0));
        assertNotNull(botThread, "BotThread is null!");
        
        botThread.start();
        
        testEnd("BotThread", "start()");
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
                        MessageRecipientInit.messageRecipient(
                                new DataInputStream(clientSock.getInputStream()));
                
                MessageSender msgSender = 
                        MessageSenderInit.messageSender(
                                new DataOutputStream(clientSock.getOutputStream()));
                
                //Excepted Connection Processor test.message
                String answer = "OK";
                System.out.println("Client send: " + msgRecipient.acceptMessage());
                msgSender.sendMessage(answer);
                System.out.println("Server send: " + answer);
                
                // Excepted Authentication Processor test.message with
                // IMEI 111111111111110 and ident 330
                answer = "{"
                            + "\"IMEI\": \"111111111111110\","
                            + "\"is_done\": \"true\","
                            + "\"test.message\": \"Connection established!\","
                            + "\"ident\": \"330\","
                            + "\"TK_list\": [\"TK1\", \"TK2\"],"
                            + "\"sklad_list\": [\"sklad1\"]"
                        + "}";
                System.out.println("Client send: " + msgRecipient.acceptMessage());
                msgSender.sendMessage(answer);
                
                // Excepted Seach Processor test.message
                answer = "{"
                            + "\"IMEI\": \"111111111111110\","
                            + "\"is_done\": \"true\","
                            + "\"data\": []"
                        + "}";
                System.out.println("Client send: " + msgRecipient.acceptMessage());
                msgSender.sendMessage(answer);
                
                TestUtils.sleep(5000);
                
                System.out.println("Server shutdown.");
            } catch (IOException | MessageSenderException | MessageRecipientException ex) {
                catchMessage(ex);
            }
        }
        
    }
}
