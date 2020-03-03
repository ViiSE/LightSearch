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
package lightsearch.admin.panel.connect.processor;

import lightsearch.admin.panel.data.stream.DataStream;
import lightsearch.admin.panel.message.MessageRecipient;
import lightsearch.admin.panel.message.MessageRecipientInit;
import lightsearch.admin.panel.message.MessageSender;
import lightsearch.admin.panel.message.MessageSenderInit;
import org.testng.annotations.*;
import test.TestServer;
import test.data.DataProviderCreator;

import static org.testng.Assert.assertNotNull;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class ConnectionProcessorTestNG {

    private MessageSender messageSender;
    private MessageRecipient messageRecipient;

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

        DataStream ds = DataProviderCreator.createDataProvider(DataStream.class, ip, port);
        assertNotNull(ds, "DataStream is null!");

        messageSender = MessageSenderInit.messageSender(ds.dataOutputStream());
        assertNotNull(messageSender, "MessageSender is null!");
            
        messageRecipient = MessageRecipientInit.messageRecipient(ds.dataInputStream());
        assertNotNull(messageRecipient, "MessageRecipient is null!");
    }
    
    @Test
    public void apply() {
        testBegin("ConnectionProcessor", "apply()");
        
        ConnectionProcessor connProc = ConnectionProcessorInit.connectionProcessor(messageSender, messageRecipient);
        assertNotNull(connProc, "ConnectionProcessor is null!");
       
        System.out.println("ConnectionProcessor: " + connProc);
        
        testEnd("ConnectionProcessor", "apply()");
    }
    
    @AfterClass
    public void closeMethod() {
        TestServer.setSimpleMode(false);
        TestServer.closeClient = true;
    }
}
