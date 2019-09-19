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
package lightsearch.server.data;

import lightsearch.server.data.stream.DataStream;
import lightsearch.server.data.stream.DataStreamCreator;
import lightsearch.server.data.stream.DataStreamCreatorInit;
import lightsearch.server.data.stream.DataStreamInit;
import lightsearch.server.exception.DataStreamCreatorException;
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
public class DataStreamTestNG {
    
    private boolean close = false;
    
    public class Client implements Runnable {

        private final String ip;
        private final int port;

        public Client(String ip, int port) {
            this.ip = ip;
            this.port = port;
        }

        @Override
        public void run() {
            try { 
                Socket socket = new Socket(ip, port);
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
    @Parameters({"ip", "port"})
    public void dataInputStream(String ip, int port) {
        testBegin("DataStream", "dataInputStream()");
        
        try(ServerSocket serverSocket = new ServerSocket(port)) {
            close = false;
            Thread client = new Thread(new Client(ip, port));
            client.start();
            Socket clientSocket = serverSocket.accept();
            DataStreamCreator dataStreamCreator = DataStreamCreatorInit.dataStreamCreator(clientSocket);
            dataStreamCreator.createDataStream();
            DataStream dataStream = DataStreamInit.dataStream(dataStreamCreator);
            assertNotNull(dataStream.dataInputStream(), "DataInputStream not null!");
            System.out.println("DataInputStream: " + dataStream.dataInputStream());
            close = true;
        } catch(IOException | DataStreamCreatorException ex) {
            catchMessage(ex);
        }
        
        testEnd("DataStream", "dataInputStream()");
    }
    
    @Test
    @Parameters({"ip", "port"})
    public void dataOutputStream(String ip, int port) {
        testBegin("DataStream", "dataOutputStream()");
        
        try(ServerSocket serverSocket = new ServerSocket(port)) {
            close = false;
            Thread client = new Thread(new Client(ip, port));
            client.start();
            Socket clientSocket = serverSocket.accept();
            DataStreamCreator dataStreamCreator = DataStreamCreatorInit.dataStreamCreator(clientSocket);
            dataStreamCreator.createDataStream();
            DataStream dataStream = DataStreamInit.dataStream(dataStreamCreator);
            assertNotNull(dataStream.dataOutputStream(), "DataOutputStream not null!");
            System.out.println("DataOutputStream: " + dataStream.dataOutputStream());
            close = true;
        } catch(IOException | DataStreamCreatorException ex) {
            System.out.println("CATCH! Message:" + ex.getMessage()); 
        }
        
        testEnd("DataStream", "dataOutputStream()");
    }
}
