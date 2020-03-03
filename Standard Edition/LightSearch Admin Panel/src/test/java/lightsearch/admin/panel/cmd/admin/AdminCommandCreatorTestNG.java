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
package lightsearch.admin.panel.cmd.admin;

import lightsearch.admin.panel.data.AdminDTO;
import lightsearch.admin.panel.util.MapRemover;
import lightsearch.admin.panel.util.MapRemoverInit;
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
public class AdminCommandCreatorTestNG {

    private AdminDTO adminDTO;
    private MapRemover mapRemover;

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
        TestServer.setSimpleMode(true);
        TestServer.setAnswerMessage(null);

        adminDTO = DataProviderCreator.createDataProvider(AdminDTO.class, ip, port);
        assertNotNull(adminDTO, "AdminDTO is null!");

        mapRemover = MapRemoverInit.mapRemover();
        assertNotNull(mapRemover, "MapRemover is null!");
    }
    
    @Test
    public void createCommandHolder() {
        testBegin("AdminCommandCreator", "createCommandHolder()");
        
        AdminCommandCreator admCmdCreator = AdminCommandCreatorInit.adminCommandCreator(adminDTO, mapRemover);
        assertNotNull(admCmdCreator.createCommandHolder(), "AdminCommandHolder is null!");
        
        System.out.println("AdminCommandHolderCreator: createCommandHolder() :" + admCmdCreator.createCommandHolder());

        testEnd("AdminCommandCreator", "createCommandHolder()");
    }
    
    @AfterClass
    public void closeMethod() {
        TestServer.setSimpleMode(false);
        TestServer.closeClient = true;
    }
}
