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
package lightsearch.admin.panel.data;

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
public class MessageCommandDTOTestNG {

    private MessageCommandDTO messageCommandDTO;

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

        messageCommandDTO = DataProviderCreator.createDataProvider(MessageCommandDTO.class, ip, port);
        assertNotNull(messageCommandDTO, "MessageCommandDTO is null!");
    }
    
    @Test
    public void messageSender() {
        testBegin("MessageCommandDTO", "messageSender()");
        
        assertNotNull(messageCommandDTO.messageSender(), "MessageCommandDTO: messageSender is null!");
        System.out.println("MessageCommandDTO: messageSender: " + messageCommandDTO.messageSender());
        
        testEnd("MessageCommandDTO", "messageSender()");
    }
    
    @Test
    public void messageRecipient() {
        testBegin("MessageCommandDTO", "messageRecipient()");

        assertNotNull(messageCommandDTO.messageRecipient(), "MessageCommandDTO: messageRecipient is null!");
        System.out.println("MessageCommandDTO: messageRecipient: " + messageCommandDTO.messageRecipient());
        
        testEnd("MessageCommandDTO", "messageRecipient()");
    }
    
    @AfterClass
    public void closeMethod() {
        TestServer.setSimpleMode(false);
        TestServer.closeClient = true;
    }

}
