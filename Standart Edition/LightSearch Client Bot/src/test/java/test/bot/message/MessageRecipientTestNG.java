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
package test.bot.message;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import lightsearch.client.bot.exception.MessageRecipientException;
import lightsearch.client.bot.message.MessageRecipient;
import lightsearch.client.bot.message.MessageRecipientInit;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class MessageRecipientTestNG {
   
    private Socket socket;
    
    @BeforeClass
    public void setUpClass() {
        Thread testServerTh = new Thread(new TestServer());
        testServerTh.start();
        
        try {
            socket = new Socket("127.0.0.1", 50000);
            assertNotNull(socket, "Socket is null!");
        }
        catch(IOException ex) {
            System.out.println("CATCH! Message: " + ex.getMessage());
        }
    }
    
    @Test
    public void acceptMessage() {
        testBegin("MessageRecipient", "acceptMessage()");

        try {
            MessageRecipient msgRecipient = MessageRecipientInit.messageRecipient(
                    new DataInputStream(socket.getInputStream()));

            String acceptMessage = msgRecipient.acceptMessage();
            System.out.println("Accept test.message: " + acceptMessage);

        }
        catch(MessageRecipientException | IOException ex) {
            System.out.println("CATCH! Message: " + ex.getMessage());
        }
        
        testEnd("MessageRecipient", "acceptMessage()");
    }
    
    private class TestServer implements Runnable {

        @Override
        public void run() {
            
            System.out.println("Test server on");
            
            try(ServerSocket serverSocket = new ServerSocket(50000)) {
                Socket admSocket = serverSocket.accept();
                DataOutputStream dOutStream = 
                        new DataOutputStream(admSocket.getOutputStream());
                
                String message = "OK";
                System.out.println("Server send test.message: " + message);
                dOutStream.writeUTF(message);
            }
            catch(IOException ex) {
                System.out.println("CATCH! Message: " + ex.getMessage());
            }
            
            System.out.println("Test server off");
        }   
    }
}
