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
package lightsearch.admin.panel.data.stream;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import lightsearch.admin.panel.data.stream.DataStream;
import lightsearch.admin.panel.data.stream.DataStreamCreator;
import lightsearch.admin.panel.data.stream.DataStreamCreatorInit;
import lightsearch.admin.panel.data.stream.DataStreamInit;
import lightsearch.admin.panel.exception.DataStreamCreatorException;
import static org.testng.Assert.*;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class DataStreamTestNG {
    
    private Thread testServerTh;
    private boolean closeServer = true;
    
    private DataStreamCreator dataStreamCreator;
    
    @BeforeTest
    public void setUpMethod() {
        testServerTh = new Thread(new TestServer());
        testServerTh.start();
        
        try {
            Socket socket = new Socket("127.0.0.1", 50000);
            assertNotNull(socket, "Socket is null!");
        
            dataStreamCreator = DataStreamCreatorInit.dataStreamCreator(socket);
            assertNotNull(dataStreamCreator, "DataStreamCreator is null!");
        
            try {
                dataStreamCreator.createDataStream();
            }
            catch(DataStreamCreatorException ex) {
                System.out.println("CATCH! Message: " + ex.getMessage());
            }
        }
        catch(IOException ex) {
            System.out.println("CATCH! Message: " + ex.getMessage());
        }
    }
    
    @Test
    public void dataInputStream() {
        testBegin("DataStream", "dataInputStream()");
        
        DataStream dataStream = DataStreamInit.dataStream(dataStreamCreator);
        assertNotNull(dataStream, "DataStream is null!");
        
        DataInputStream dInStream = dataStream.dataInputStream();
        assertNotNull(dInStream, "DataInputStream is null!");
        
        System.out.println("DataInputStream: " + dInStream);
        
        testEnd("DataStream", "dataInputStream()");
    }
    
    @Test
    public void dataOutputStream() {
        testBegin("DataStream", "dataOutputStream()");
        
        DataStream dataStream = DataStreamInit.dataStream(dataStreamCreator);
        assertNotNull(dataStream, "DataStream is null!");
        
        DataOutputStream dOutStream = dataStream.dataOutputStream();
        assertNotNull(dOutStream, "DataOutputStream is null!");
        
        System.out.println("DataOutputStream: " + dOutStream);
        
        testEnd("DataStream", "dataOutputStream()");
    }
    
    @AfterTest
    public void closeMethod() {
        closeServer = false;
    }
    
    private class TestServer implements Runnable {

        @Override
        public void run() {
            ServerSocket serverSocket = null;
            
            try { serverSocket = new ServerSocket(50000); }
            catch(IOException ex) {
                System.out.println("CATCH! Message: " + ex.getMessage());
            }
            
            System.out.println("Test server on");
            
            try { if(serverSocket != null) serverSocket.accept(); }
            catch(IOException ex) {
                System.out.println("CATCH! Message: " + ex.getMessage());
            }
            
            while(closeServer) { /* Just waiting for the end of test */ }
            
            try { if(serverSocket != null) serverSocket.close(); }
            catch(IOException ex) {
                System.out.println("CATCH! Message: " + ex.getMessage());
            }
            
            System.out.println("Test server off");
        }   
    }
}
