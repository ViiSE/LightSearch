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

package lightsearch.server.timer;

import lightsearch.server.LightSearchServer;
import lightsearch.server.data.LightSearchServerService;
import lightsearch.server.data.pojo.Client;
import lightsearch.server.producer.timer.TimeoutManagerProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;

@SpringBootTest(classes = LightSearchServer.class)
public class TimeoutManagerReducerTestNG extends AbstractTestNGSpringContextTests {

    @Autowired private TimeoutManagerProducer timeoutManagerProducer;
    @Autowired private LightSearchServerService serverService;

    private TimeoutManager timeoutManager;

    @SuppressWarnings("unchecked")
    @BeforeClass
    public void setUpClass() {
        timeoutManager = timeoutManagerProducer.getTimeoutManagerReducerImpl(1, serverService.clientsService());
        serverService.clientsService().clients().put("111111111111111", new Client("user1"));
        serverService.clientsService().clients().put("222222222222222", new Client("user2"));
        serverService.clientsService().clients().put("333333333333333", new Client("user3"));
        serverService.clientsService().clients().put("444444444444444", new Client("user4"));
        Client client = (Client) serverService.clientsService().clients().get("444444444444444");
        client.setTimeoutLimitSeconds(1);
    }

    @Test
    public void check() {
        System.out.println(serverService.clientsService().clients().toString());

        timeoutManager.refresh();
        timeoutManager.check();

        System.out.println(serverService.clientsService().clients().toString());
    }
}
