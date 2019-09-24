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
package lightsearch.admin.panel.connect.processor.result;

import test.TestServer;
import lightsearch.admin.panel.connect.processor.ConnectionProcessor;
import org.testng.annotations.*;
import test.data.DataProviderCreator;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class ConnectionProcessorResultTestNG {

    private ConnectionProcessor connectionProcessor;

    @BeforeTest
    @Parameters({"ip", "port"})
    public void setUpTest(String ip, int port) {
        if(!TestServer.serverOn) {
            Thread testServerTh = new Thread(new TestServer(port));
            testServerTh.start();
        }
    }

    @BeforeClass
    @Parameters({"ip", "port", "answerMessage"})
    public void setUpMethod(String ip, int port, String answerMessage) {
        TestServer.closeClient = false;
        TestServer.setAnswerMessage(answerMessage);

        connectionProcessor = DataProviderCreator.createDataProvider(ConnectionProcessor.class, ip, port);
    }
    
    @Test
    public void result() {
        testBegin("ConnectionProcessorResult", "result()");
       
        ConnectionProcessorResult connProcRes = connectionProcessor.apply();
        assertNotNull(connProcRes, "ConnectionProcessorResult is null!");
        
        String res = connProcRes.result();
        assertNotNull(res, "Result is null!");
        assertFalse(res.isEmpty(), "Result is null!");
        
        System.out.println("Result: " + res);
        
        testEnd("ConnectionProcessorResult", "result()");
    }

    @AfterClass
    public void closeMethod() {
        TestServer.closeClient = true;
    }
}
