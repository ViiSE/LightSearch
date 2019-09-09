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
package test.admin.panel.message;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import lightsearch.admin.panel.data.stream.DataStream;
import lightsearch.admin.panel.data.stream.DataStreamCreator;
import lightsearch.admin.panel.data.stream.DataStreamCreatorInit;
import lightsearch.admin.panel.data.stream.DataStreamInit;
import lightsearch.admin.panel.exception.DataStreamCreatorException;
import lightsearch.admin.panel.exception.MessageSenderException;
import lightsearch.admin.panel.message.MessageSender;
import lightsearch.admin.panel.message.MessageSenderInit;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class MessageSenderTestNG {
    
    private Thread testServerTh;    
    private DataStream dataStream;
    
    @BeforeTest
    public void setUpMethod() {
        testServerTh = new Thread(new TestServer());
        testServerTh.start();
        
        try {
            Socket socket = new Socket("127.0.0.1", 50000);
            assertNotNull(socket, "Socket is null!");
        
            DataStreamCreator dsCreator 
                    = DataStreamCreatorInit.dataStreamCreator(socket);
            assertNotNull(dsCreator, "DataStreamCreator is null!");
        
            try {
                dsCreator.createDataStream();
            }
            catch(DataStreamCreatorException ex) {
                System.out.println("CATCH! Message: " + ex.getMessage());
            }
            
            dataStream = DataStreamInit.dataStream(dsCreator);
        }
        catch(IOException ex) {
            System.out.println("CATCH! Message: " + ex.getMessage());
        }
    }
    
    @Test
    public void sendMessage() {
        testBegin("MessageSender", "sendMessage()");

        try {
            assertNotNull(dataStream, "DataStream is null!");

            MessageSender msgSender = 
                    MessageSenderInit.messageSender(dataStream.dataOutputStream());

            String message = "OK";
            msgSender.sendMessage(message);
            System.out.println("Admin send message: " + message);

        }
        catch(MessageSenderException ex) {
            System.out.println("CATCH! Message: " + ex.getMessage());
        }
        
        testEnd("MessageSender", "sendMessage()");
    }
    
    private class TestServer implements Runnable {

        @Override
        public void run() {
            
            System.out.println("Test server on");
            
            try(ServerSocket serverSocket = new ServerSocket(50000)) {
                Socket admSocket = serverSocket.accept();
                DataInputStream dInStream = 
                        new DataInputStream(admSocket.getInputStream());
                
                String message = dInStream.readUTF();
                System.out.println("Server accept message: " + message);
            }
            catch(IOException ex) {
                System.out.println("CATCH! Message: " + ex.getMessage());
            }
            
            System.out.println("Test server off");
        }   
    }
}
