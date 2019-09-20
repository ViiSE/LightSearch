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
package lightsearch.server.message;

import lightsearch.server.data.stream.DataStream;
import lightsearch.server.data.stream.DataStreamCreator;
import lightsearch.server.data.stream.DataStreamCreatorInit;
import lightsearch.server.data.stream.DataStreamInit;
import lightsearch.server.exception.ConnectionIdentifierException;
import lightsearch.server.exception.DataStreamCreatorException;
import lightsearch.server.exception.MessageSenderException;
import lightsearch.server.identifier.ConnectionIdentifier;
import lightsearch.server.identifier.ConnectionIdentifierInit;
import lightsearch.server.identifier.ConnectionIdentifierResult;
import lightsearch.server.thread.LightSearchThread;
import lightsearch.server.thread.LightSearchThreadInit;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static org.testng.Assert.assertNotNull;
import static test.message.TestMessage.*;

/**
 *
 * @author ViiSE
 */
public class MessageSenderTestNG {
    
    private boolean close = false;
    
    private class Admin implements Runnable {

        private final String ip;
        private final int port;

        Admin(String ip, int port) {
            this.ip = ip;
            this.port = port;
        }

        @Override
        public void run() {
            try(Socket socket = new Socket(ip, port)) {
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
            } catch (IOException | DataStreamCreatorException ignore) { }
        }   
    }
    
    private void adminTest(ServerSocket serverSocket, String ip, int port) {
        try {
            LightSearchThread admin = LightSearchThreadInit.lightSearchThread(new Admin(ip, port));
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
            catchMessage(ex);
        }
    }
    
    @Test
    @Parameters({"ip", "port"})
    public void sendMessage(String ip, int port) {
        testBegin("MessageSender", "sendMessage()");
        
        close = false;
        try(ServerSocket serverSocket = new ServerSocket(port);) {
            adminTest(serverSocket, ip, port);
            close = true;
            serverSocket.close();
        } catch(IOException ex) {
            catchMessage(ex);
        }
        
        testEnd("MessageSender", "sendMessage()");
    }
}
