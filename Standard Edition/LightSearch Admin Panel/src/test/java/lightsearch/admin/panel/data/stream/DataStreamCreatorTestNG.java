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

import java.io.IOException;
import java.net.Socket;

import static org.testng.Assert.assertNotNull;
import static test.message.TestMessage.*;

/**
 *
 * @author ViiSE
 */
public class DataStreamCreatorTestNG {

    private Socket adminSocket;

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
            adminSocket = new Socket(ip, port);
            assertNotNull(adminSocket, "Socket is null!");
        
        } catch(IOException ex) {
            catchMessage(ex);
        }
    }
    
    @Test
    public void createDataStream() {
        testBegin("DataStreamCreator", "createDataStream()");
        
        DataStreamCreator dsCreator = DataStreamCreatorInit.dataStreamCreator(adminSocket);
        assertNotNull(dsCreator, "DataStreamCreator is null!");
        
        try {
            dsCreator.createDataStream();
        } catch(DataStreamCreatorException ex) {
            catchMessage(ex);
        }
        
        System.out.println("DataInputStream: " + dsCreator.dataInputStream());
        System.out.println("DataOutputStream: " + dsCreator.dataOutputStream());
        
        testEnd("DataStreamCreator", "createDataStream()");
    }

    @AfterClass
    public void closeMethod() {
        TestServer.setSimpleMode(false);
        TestServer.closeClient = true;
    }
}
