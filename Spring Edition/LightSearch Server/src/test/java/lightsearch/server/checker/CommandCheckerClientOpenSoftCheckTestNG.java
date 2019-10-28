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

package lightsearch.server.checker;

import lightsearch.server.LightSearchServer;
import lightsearch.server.data.LightSearchServerService;
import lightsearch.server.data.pojo.Client;
import lightsearch.server.data.pojo.ClientCommandDTO;
import lightsearch.server.exception.CheckerException;
import lightsearch.server.producer.checker.CommandCheckerProducer;
import lightsearch.server.producer.checker.LightSearchCheckerProducer;
import lightsearch.server.producer.cmd.client.ClientCommandProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static test.message.TestMessage.*;

@SpringBootTest(classes = LightSearchServer.class)
public class CommandCheckerClientOpenSoftCheckTestNG extends AbstractTestNGSpringContextTests {

    @Autowired private CommandCheckerProducer commandCheckerProducer;
    @Autowired private ClientCommandProducer clientCommandProducer;
    @Autowired private LightSearchServerService serverService;
    @Autowired private LightSearchCheckerProducer checkerProducer;

    private CommandChecker checker;

    @BeforeClass
    public void setUpClass() {
        ClientCommandDTO commandDTO = new ClientCommandDTO();
        commandDTO.setIMEI("111111111111111");
        commandDTO.setUserIdentifier("22505");
        commandDTO.setCardCode("777");

        checker = commandCheckerProducer.getCommandCheckerClientOpenSoftCheckInstance(
                clientCommandProducer.getClientCommandDefaultInstance(commandDTO),
                serverService, checkerProducer.getLightSearchCheckerDefaultInstance());

        testBegin("CommandCheckerClientOpenSoftCheck", "check()");
    }

    @SuppressWarnings("unchecked")
    @Test
    public void check_success() {
        try {
            serverService.clientsService().clients().put("111111111111111", new Client("111111111111111", "test"));
            serverService.blacklistService().blacklist().remove("111111111111111");
            checker.check();
            System.out.println("Check: Success!");
        } catch (CheckerException ex) {
            catchMessage(ex);
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    public void check_client_in_the_blacklist() {
        try {
            serverService.blacklistService().blacklist().add("111111111111111");
            checker.check();
            System.out.println("Check: Success!");
        } catch (CheckerException ex) {
            catchMessage(ex);
        }
    }

    @Test
    public void check_client_not_authorized() {
        try {
            serverService.clientsService().clients().remove("111111111111111");
            serverService.blacklistService().blacklist().remove("111111111111111");
            checker.check();
            System.out.println("Check: Success!");
        } catch (CheckerException ex) {
            catchMessage(ex);
        }
    }

    @AfterClass
    public void shutdownClass() {
        testEnd("CommandCheckerClientOpenSoftCheck", "check()");
    }
}
