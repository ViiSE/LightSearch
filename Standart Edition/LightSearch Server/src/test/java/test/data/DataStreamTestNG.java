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
package test.data;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import lightsearch.server.data.stream.DataStream;
import lightsearch.server.data.stream.DataStreamCreator;
import lightsearch.server.data.stream.DataStreamCreatorInit;
import lightsearch.server.data.stream.DataStreamInit;
import lightsearch.server.exception.DataStreamCreatorException;
import static org.testng.Assert.*;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class DataStreamTestNG {
    
    private boolean close = false;
    
    public class Client implements Runnable {

        @Override
        public void run() {
            try { 
                Socket socket = new Socket("127.0.0.1", 49000);
                socket.getOutputStream();
                socket.getInputStream();
                while(!close) {
                    // do something
                }
            } 
            catch (IOException ignore) { }   
        }   
    }
    
    @Test
    public void dataInputStream() {
        testBegin("DataStream", "dataInputStream()");
        
        try(ServerSocket serverSocket = new ServerSocket(49000);) {
            close = false;
            Thread client = new Thread(new Client());
            client.start();
            Socket clientSocket = serverSocket.accept();
            DataStreamCreator dataStreamCreator = DataStreamCreatorInit.dataStreamCreator(clientSocket);
            dataStreamCreator.createDataStream();
            DataStream dataStream = DataStreamInit.dataStream(dataStreamCreator);
            assertNotNull(dataStream.dataInputStream(), "DataInputStream not null!");
            System.out.println("DataInputStream: " + dataStream.dataInputStream());
            close = true;
        }
        catch(IOException | DataStreamCreatorException ex) { 
            System.out.println("CATCH! Message: " + ex.getMessage()); 
        }
        
        testEnd("DataStream", "dataInputStream()");
    }
    
    @Test
    public void dataOutputStream() {
        testBegin("DataStream", "dataOutputStream()");
        
        try(ServerSocket serverSocket = new ServerSocket(49000);) {
            close = false;
            Thread client = new Thread(new Client());
            client.start();
            Socket clientSocket = serverSocket.accept();
            DataStreamCreator dataStreamCreator = DataStreamCreatorInit.dataStreamCreator(clientSocket);
            dataStreamCreator.createDataStream();
            DataStream dataStream = DataStreamInit.dataStream(dataStreamCreator);
            assertNotNull(dataStream.dataOutputStream(), "DataOutputStream not null!");
            System.out.println("DataOutputStream: " + dataStream.dataOutputStream());
            close = true;
        }
        catch(IOException | DataStreamCreatorException ex) { 
            System.out.println("CATCH! Message:" + ex.getMessage()); 
        }
        
        testEnd("DataStream", "dataOutputStream()");
    }
}
