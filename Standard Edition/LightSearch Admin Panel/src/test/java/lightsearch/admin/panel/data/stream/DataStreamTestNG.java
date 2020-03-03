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

import lightsearch.admin.panel.exception.DataStreamCreatorException;
import org.testng.annotations.*;
import test.TestServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static org.testng.Assert.assertNotNull;
import static test.message.TestMessage.*;

/**
 *
 * @author ViiSE
 */
public class DataStreamTestNG {

    private DataStreamCreator dataStreamCreator;

    @BeforeTest
    @Parameters({"ip", "port"})
    public void setUpTest(String ip, int port) {
        if(!TestServer.serverOn) {
            Thread testServerTh = new Thread(new TestServer(port));
            testServerTh.start();
        }
    }

    @BeforeClass
    @Parameters({"ip", "port"})
    public void setUpMethod(String ip, int port) {
        TestServer.closeClient = false;
        TestServer.setAnswerMessage(null);
        TestServer.setSimpleMode(true);
        
        try {
            Socket socket = new Socket(ip, port);
            assertNotNull(socket, "Socket is null!");
        
            dataStreamCreator = DataStreamCreatorInit.dataStreamCreator(socket);
            assertNotNull(dataStreamCreator, "DataStreamCreator is null!");
        
            dataStreamCreator.createDataStream();

        } catch(IOException | DataStreamCreatorException ex) {
            catchMessage(ex);
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
    
    @AfterClass
    public void closeMethod() {
        TestServer.setSimpleMode(false);
        TestServer.closeClient = true;
    }
}
