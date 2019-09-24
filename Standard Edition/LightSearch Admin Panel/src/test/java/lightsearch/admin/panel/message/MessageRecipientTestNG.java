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
package lightsearch.admin.panel.message;

import test.TestServer;
import lightsearch.admin.panel.data.stream.DataStream;
import lightsearch.admin.panel.exception.MessageRecipientException;

import static org.testng.Assert.*;
import static test.message.TestMessage.*;

import org.testng.annotations.*;
import test.data.DataProviderCreator;

/**
 *
 * @author ViiSE
 */
public class MessageRecipientTestNG {

    private DataStream dataStream;

    @BeforeTest
    @Parameters({"ip", "port"})
    public void setUpTest(String ip, int port) {
        if(!TestServer.serverOn) {
            Thread testServerTh = new Thread(new TestServer(port));
            testServerTh.start();
        }
    }

    @BeforeClass
    @Parameters({"ip", "port", "recipientMessage"})
    public void setUpMethod(String ip, int port, String answerMessage) {
        TestServer.closeClient = false;
        TestServer.setAnswerMessage(answerMessage);
        TestServer.setRecipientMode(false);

        dataStream = DataProviderCreator.createDataProvider(DataStream.class, ip, port);
        assertNotNull(dataStream, "DataStream is null!");
    }
    
    @Test
    public void acceptMessage() {
        testBegin("MessageRecipient", "acceptMessage()");

        try {
            MessageRecipient msgRecipient = MessageRecipientInit.messageRecipient(dataStream.dataInputStream());
            String acceptMessage = msgRecipient.acceptMessage();
            System.out.println("Accept message: " + acceptMessage);
        } catch(MessageRecipientException ex) {
            catchMessage(ex);
        }
        
        testEnd("MessageRecipient", "acceptMessage()");
    }

    @AfterClass
    public void tearDownClass() {
        TestServer.setRecipientMode(true);
        TestServer.closeClient = true;
    }
}
