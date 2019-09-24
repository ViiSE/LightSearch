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

import lightsearch.admin.panel.data.stream.DataStream;
import lightsearch.admin.panel.exception.MessageSenderException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import test.TestServer;
import test.data.DataProviderCreator;

import static org.testng.Assert.assertNotNull;
import static test.message.TestMessage.*;

/**
 *
 * @author ViiSE
 */
public class MessageSenderTestNG {

    private DataStream dataStream;
    
    @BeforeClass
    @Parameters({"ip", "port"})
    public void setUpMethod(String ip, int port) {
        TestServer.closeClient = false;
        TestServer.setAnswerMessage(null);
        
        dataStream = DataProviderCreator.createDataProvider(DataStream.class, ip, port);
        assertNotNull(dataStream, "DataStream is null!");
    }
    
    @Test
    @Parameters({"sendMessage"})
    public void sendMessage(String message) {
        testBegin("MessageSender", "sendMessage()");

        try {
            MessageSender msgSender = MessageSenderInit.messageSender(dataStream.dataOutputStream());
            msgSender.sendMessage(message);
            System.out.println("Admin send message: " + message);
        } catch(MessageSenderException ex) {
            catchMessage(ex);
        }
        
        testEnd("MessageSender", "sendMessage()");
    }

    @AfterClass
    public void tearDownClass() {
        TestServer.closeClient = true;
    }
}
