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
package lightsearch.server.identifier;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import lightsearch.server.data.stream.DataStream;
import lightsearch.server.data.stream.DataStreamCreator;
import lightsearch.server.data.stream.DataStreamCreatorInit;
import lightsearch.server.data.stream.DataStreamInit;
import lightsearch.server.exception.DataStreamCreatorException;
import lightsearch.server.exception.ConnectionIdentifierException;
import static org.testng.Assert.*;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class ConnectionIdentifierTestNG {
    
    private boolean close = false;
    
    public class Client implements Runnable {

        @Override
        public void run() {
            try(Socket socket = new Socket("127.0.0.1", 49000);) { 
                DataStreamCreator dataStreamCreator = DataStreamCreatorInit.dataStreamCreator(socket);
                dataStreamCreator.createDataStream();
                DataStream dataStream = DataStreamInit.dataStream(dataStreamCreator);
                dataStream.dataOutputStream().writeUTF("{\"identifier\":\"admin\"}");
                String message = dataStream.dataInputStream().readUTF();
                if(message.equals("OK"))
                    System.out.println("I am connected!");
                while(!close) {
                    // do something
                }
            } 
            catch (IOException | DataStreamCreatorException ignore) {}   
        }   
    }
    
    @Test
    public void identifyConnection() {
        testBegin("ConnectionIdentifier", "identifyConnection()");
        
        try(ServerSocket serverSocket = new ServerSocket(49000);) {
            close = false;
            Thread client = new Thread(new Client());
            client.start();
            Socket clientSocket = serverSocket.accept();
            ConnectionIdentifier connIndent = ConnectionIdentifierInit.connectionIdentifier();
            assertNotNull(connIndent, "Connection identifier is null!");
            ConnectionIdentifierResult connRes = connIndent.identifyConnection(clientSocket);
            System.out.println("OK");
            System.out.println("Connection identifier: " + connIndent);
            System.out.println("Identifier: " + connRes.identifier());
            System.out.println("Client socket: " + connRes.clientSocket());
            System.out.println("Data stream: " + connRes.dataStream());
            
            close = true;
        }
        catch(IOException | ConnectionIdentifierException ex) { 
            System.out.println("CATCH! Message: " + ex.getMessage()); 
        }
        
        testEnd("ConnectionIdentifier", "identifyConnection()");
    }
}
