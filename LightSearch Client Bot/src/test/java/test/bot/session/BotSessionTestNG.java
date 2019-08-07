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
package test.bot.session;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import lightsearch.client.bot.exception.MessageRecipientException;
import lightsearch.client.bot.exception.MessageSenderException;
import lightsearch.client.bot.message.MessageRecipient;
import lightsearch.client.bot.message.MessageRecipientInit;
import lightsearch.client.bot.message.MessageSender;
import lightsearch.client.bot.message.MessageSenderInit;
import lightsearch.client.bot.session.BotSession;
import lightsearch.client.bot.session.BotSessionInit;
import lightsearch.client.bot.settings.Configuration;
import lightsearch.client.bot.settings.ConfigurationInit;
import lightsearch.client.bot.settings.GlobalSettings;
import lightsearch.client.bot.settings.GlobalSettingsInit;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static test.ResourcesFilesPath.getResourcesFilesPath;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class BotSessionTestNG {
    
    private final String configurationName = getResourcesFilesPath() + "configuration.json";
    private String botSettingsName;
    private GlobalSettings globalSettings;
    private boolean isPerformance;
    
    @BeforeClass
    public void setUpClass() {
        Thread serverThread = new Thread(new TestServer());
        serverThread.start();
        
        Configuration configuration = ConfigurationInit.configuration(configurationName);
        globalSettings = GlobalSettingsInit.globalSettingsCreator(getResourcesFilesPath() + configuration.globalSettingsName());
        botSettingsName = getResourcesFilesPath() + configuration.botSettingsName();
        isPerformance = configuration.isPerformance();
    }
    
    @Test
    public void startSession() {
        testBegin("BotSession", "startSession()");
        
        assertNotNull(globalSettings, "GlobalSettings is null!");
        assertNotNull(botSettingsName, "BotSettingsName is null!");
        
        BotSession botSession = BotSessionInit.BotSession(botSettingsName, 
                globalSettings, isPerformance);
        assertNotNull(botSession, "BotSession is null!");
        
        botSession.startSession();
        
        testEnd("BotSession", "startSession()");
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
                
                // Excepted Search Processor test.message
                answer = "{"
                            + "\"IMEI\": \"111111111111110\","
                            + "\"is_done\": \"true\","
                            + "\"data\": []"
                        + "}";
                System.out.println("Client send: " + msgRecipient.acceptMessage());
                msgSender.sendMessage(answer);
                
                Thread.sleep(5000);
                
                System.out.println("Server shutdown.");
            } catch (IOException ex) {
                throw new RuntimeException("Cannot create test server. Exception: " + ex.getMessage());
            } catch (MessageRecipientException ex) {
                throw new RuntimeException("Cannot read client test.message. Exception: " + ex.getMessage());
            } catch (MessageSenderException ex) {
                throw new RuntimeException("Cannot send client test.message. Exception: " + ex.getMessage());
            }
            catch (InterruptedException ignore) { }
        }
    }
}
