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

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import lightsearch.admin.panel.data.stream.DataStreamCreator;
import lightsearch.admin.panel.data.stream.DataStreamCreatorInit;
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
public class DataStreamCreatorTestNG {
    
    private Thread testServerTh;
    private boolean closeServer = true;
    
    private Socket adminSocket;
    
    @BeforeTest
    public void setUpMethod() {
        testServerTh = new Thread(new TestServer());
        testServerTh.start();
        
        try {
            adminSocket = new Socket("127.0.0.1", 50000);
            assertNotNull(adminSocket, "Socket is null!");
        
        }
        catch(IOException ex) {
            System.out.println("CATCH! Message: " + ex.getMessage());
        }
    }
    
    @Test
    public void createDataStream() {
        testBegin("DataStreamCreator", "createDataStream()");
        
        DataStreamCreator dsCreator = 
                DataStreamCreatorInit.dataStreamCreator(adminSocket);
        assertNotNull(dsCreator, "DataStreamCreator is null!");
        
        try {
            dsCreator.createDataStream();
        }
        catch(DataStreamCreatorException ex) {
            System.out.println("CATCH! Message: " + ex.getMessage());
        }
        
        System.out.println("DataInputStream: " + dsCreator.dataInputStream());
        System.out.println("DataOutputStream: " + dsCreator.dataOutputStream());
        
        testEnd("DataStreamCreator", "createDataStream()");
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
