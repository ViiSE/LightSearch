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
package lightsearch.client.bot.message;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import lightsearch.client.bot.exception.MessageSenderException;
import lightsearch.client.bot.message.MessageSender;
import lightsearch.client.bot.message.MessageSenderInit;
import static org.testng.Assert.*;
import static test.message.TestMessage.*;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import test.data.DataProviderCreator;

/**
 *
 * @author ViiSE
 */
public class MessageSenderTestNG {
    
    private Socket socket;
    
    @BeforeClass
    @Parameters({"ip", "port"})
    public void setUpClass(String ip, int port) {
        Thread testServerTh = new Thread(new TestServer(port));
        testServerTh.start();

        socket = DataProviderCreator.createDataProvider(Socket.class, ip, port);
        assertNotNull(socket, "Socket is null!");
    }
    
    @Test
    public void sendMessage() {
        testBegin("MessageSender", "sendMessage()");

        try {
            MessageSender msgSender = DataProviderCreator.createDataProvider(MessageSender.class, socket);
            assertNotNull(msgSender, "MessageSender is null!");

            String message = "OK";
            msgSender.sendMessage(message);
            System.out.println("Client send test.message: " + message);

        } catch(MessageSenderException ex) {
            catchMessage(ex);
        }
        
        testEnd("MessageSender", "sendMessage()");
    }
    
    private static class TestServer implements Runnable {

        private final int port;

        TestServer(int port) {
            this.port = port;
        }

        @Override
        public void run() {
            
            System.out.println("Test server on");
            
            try(ServerSocket serverSocket = new ServerSocket(port)) {
                Socket admSocket = serverSocket.accept();
                DataInputStream dInStream = new DataInputStream(admSocket.getInputStream());
                
                String message = dInStream.readUTF();
                System.out.println("Server accept test.message: " + message);
            } catch(IOException ex) {
                catchMessage(ex);
            }
            
            System.out.println("Test server off");
        }   
    }
}
