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

package lightsearch.server.data;

import lightsearch.server.LightSearchServer;
import lightsearch.server.data.pojo.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

@SpringBootTest(classes = LightSearchServer.class)
public class ClientTestNG extends AbstractTestNGSpringContextTests {

    @Autowired
    private LightSearchServerService serverService;

    private ClientsService<String, Client> clientsService;

    @SuppressWarnings({"unchecked"})
    @BeforeClass
    public void setUpClass() {
        clientsService = serverService.clientsService();
        clientsService.addClient("111111111111111", new Client("111111111111111", "Client 1"));
    }

    @Test
    public void client_test_application_properties_read_value_timeout_client() {
        testBegin("Client", "");

        System.out.println(clientsService.clients().get("111111111111111").getTimeoutLimitSeconds());

        testEnd("Client", "");
    }
}
