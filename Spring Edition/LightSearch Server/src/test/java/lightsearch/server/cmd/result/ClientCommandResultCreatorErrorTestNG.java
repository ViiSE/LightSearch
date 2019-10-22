/*
 *  Copyright 2019 ViiSE.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package lightsearch.server.cmd.result;

import lightsearch.server.LightSearchServer;
import lightsearch.server.data.pojo.ClientCommandResult;
import lightsearch.server.exception.CommandResultException;
import lightsearch.server.producer.cmd.result.ClientCommandResultCreatorProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;
import static test.message.TestMessage.*;

@SpringBootTest(classes = LightSearchServer.class)
public class ClientCommandResultCreatorErrorTestNG extends AbstractTestNGSpringContextTests {

    @Autowired
    private ClientCommandResultCreatorProducer cmdResCrProducer;

    private ClientCommandResultCreator cmdResCr;

    @BeforeClass
    public void setUpClass() {
        cmdResCr = cmdResCrProducer.getCommandResultCreatorClientErrorInstance("111111111111111", "Error");
    }

    @Test
    public void createClientCommandResult() {
        testBegin("ClientCommandResultCreatorError" ,"createClientCommandResult()");

        try {
            ClientCommandResult clCmdRes = cmdResCr.createClientCommandResult();
            assertNotNull(clCmdRes, "ClientCommandResult is null!");
            System.out.println("CommandResult: ");
            System.out.println("IMEI: " + clCmdRes.getIMEI());
            System.out.println("isDone: " + clCmdRes.getIsDone());
            System.out.println("message: " + clCmdRes.getMessage());
        } catch (CommandResultException ex) {
            catchMessage(ex);
        }

        testEnd("ClientCommandResultCreatorError" ,"createClientCommandResult()");
    }
}
