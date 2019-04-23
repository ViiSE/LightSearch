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
package test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import lightsearch.server.data.stream.DataStream;
import lightsearch.server.data.stream.DataStreamCreator;
import lightsearch.server.data.stream.DataStreamCreatorInit;
import lightsearch.server.data.stream.DataStreamInit;
import lightsearch.server.exception.DataStreamCreatorException;
import lightsearch.server.identifier.ConnectionIdentifier;
import lightsearch.server.identifier.ConnectionIdentifierInit;
import lightsearch.server.identifier.ConnectionIdentifierResult;
import lightsearch.server.exception.ConnectionIdentifierException;
import lightsearch.server.message.MessageSender;
import lightsearch.server.message.MessageSenderInit;
import lightsearch.server.exception.MessageSenderException;
import lightsearch.server.thread.LightSearchThread;
import lightsearch.server.thread.LightSearchThreadInit;
import static org.testng.Assert.*;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class MessageSenderTestNG {
    
    private boolean close = false;
    
    public class Admin implements Runnable {

        @Override
        public void run() {
            try(Socket socket = new Socket("127.0.0.1", 49000);) {
                DataStreamCreator dataStreamCreator = DataStreamCreatorInit.dataStreamCreator(socket);
                dataStreamCreator.createDataStream();
                DataStream dataStream = DataStreamInit.dataStream(dataStreamCreator);
                long i = 0;
                while(!close) {
                    if(i == 0) {
                        String message = "{\"identifier\": \"admin\"}";
                        dataStream.dataOutputStream().writeUTF(message);
                        dataStream.dataOutputStream().flush();
                        
                        String getMessage = dataStream.dataInputStream().readUTF();
                        System.out.println("ADMIN GET MESSAGE: " + getMessage);
                        
                        i++;
                        
                        String message2 = dataStream.dataInputStream().readUTF();
                        System.out.println("Server send message: " + message2);
                    }
                }
            } 
            catch (IOException | DataStreamCreatorException ignore) { }   
        }   
    }
    
    private void adminTest(ServerSocket serverSocket) {
        try {
            LightSearchThread admin = LightSearchThreadInit.lightSearchThread(new Admin());
            admin.start();
            Socket clientSocket = serverSocket.accept();
            
            ConnectionIdentifier connectionIdentifier = ConnectionIdentifierInit.connectionIdentifier();
            ConnectionIdentifierResult connectionIdentifierResult = connectionIdentifier.identifyConnection(clientSocket);
            
            assertNotNull(connectionIdentifierResult, "ConnectionIdentifierResult is null!");
            assertNotNull(connectionIdentifierResult.clientSocket(), "Client socket is null!");
            assertNotNull(connectionIdentifierResult.dataStream(), "DataStream is null!");
            assertNotNull(connectionIdentifierResult.identifier(), "Identifier is null!");
            
            MessageSender messageSender = MessageSenderInit.messageSender(
                    connectionIdentifierResult.dataStream().dataOutputStream());
            
            messageSender.sendMessage("Hello, mister admin!");
                        
        } catch (IOException | ConnectionIdentifierException | MessageSenderException ex) {
            System.out.println("CATCH! adminTest: " + ex.getMessage());
        }
    }
    
    @Test
    public void sendMessage() {
        testBegin("MessageSender", "sendMessage()");
        
        close = false;
        try(ServerSocket serverSocket = new ServerSocket(49000);) {
            adminTest(serverSocket);
            close = true;
            serverSocket.close();
        }
        catch(IOException ex) { 
            System.out.println("CATCH! Message: " + ex.getMessage()); 
        }
        
        testEnd("MessageSender", "sendMessage()");
    }
}
