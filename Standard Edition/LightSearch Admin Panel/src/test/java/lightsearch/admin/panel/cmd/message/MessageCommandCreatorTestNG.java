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
package lightsearch.admin.panel.cmd.message;

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
public class MessageCommandCreatorTestNG {

    private MessageCommandCreator msgCmdCreator;

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

        msgCmdCreator = DataProviderCreator.createDataProvider(MessageCommandCreator.class, ip, port);
    }
    
    @Test
    public void createMessageCommandHolder() {
        testBegin("MessageCommandCreator", "createMessageCommandHolder()");

        assertNotNull(msgCmdCreator, "MessageCommandCreator is null!");
        assertNotNull(msgCmdCreator.createMessageCommandHolder(),
                "MessageCommandCreator: createMessageCommandHolder() is null!");
        System.out.println("MessageCommandCreator: createMessageCommandHolder(): "
                + msgCmdCreator.createMessageCommandHolder());
        
        testEnd("MessageCommandCreator", "createMessageCommandHolder()");
    }
    
    @AfterClass
    public void closeMethod() {
        TestServer.setSimpleMode(false);
        TestServer.closeClient = true;
    }
}
