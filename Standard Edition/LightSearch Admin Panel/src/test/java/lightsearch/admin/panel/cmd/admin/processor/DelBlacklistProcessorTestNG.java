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
package lightsearch.admin.panel.cmd.admin.processor;

import java.util.Map;
import java.util.function.Function;
import lightsearch.admin.panel.cmd.admin.AdminCommandCreator;
import lightsearch.admin.panel.cmd.admin.AdminCommandEnum;
import lightsearch.admin.panel.data.AdminPanelDTO;
import lightsearch.admin.panel.data.creator.AdminPanelDTOCreatorInit;

import static org.testng.Assert.*;

import org.testng.annotations.*;
import test.data.DataProviderCreator;

import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class DelBlacklistProcessorTestNG {
    
    private final String DEL_BLACKLIST = AdminCommandEnum.DEL_BLACKLIST.stringValue();
    
    private Map<String, Function<AdminPanelDTO, String>> admCmdHolder;
    private AdminPanelDTO adminPanelDTO;
    
    @BeforeClass
    @Parameters({"ip", "port", "delBlMessage", "openTest"})
    public void setUpMethod(String ip, int port, String answerMessage, boolean openTest) {
        TestServer.closeServer = false;
        Thread testServerTh = new Thread(new TestServer(port, answerMessage));
        testServerTh.start();

        admCmdHolder =
                DataProviderCreator.createDataProvider(AdminCommandCreator.class, ip, port, openTest).createCommandHolder();
        assertNotNull(admCmdHolder, "AdminCommandHolder is null!");

        adminPanelDTO  = AdminPanelDTOCreatorInit.adminPanelDTOCreator().createAdminPanelDTO();
        assertNotNull(adminPanelDTO, "AdminPanelDTO is null!");
    }
    
    @Test
    public void apply() {
        testBegin("DelBlacklistProcessor", "apply()");
        
        adminPanelDTO.blacklist().put("1", "123456789123456");
        adminPanelDTO.blacklist().put("2", "987654321987654");
        
        Function<AdminPanelDTO, String> processor = admCmdHolder.get(DEL_BLACKLIST);
        assertNotNull(processor, "DelBlacklistProcessor is null!");
        
        String result = processor.apply(adminPanelDTO);
        assertNotNull(result, "Result is null!");
        assertFalse(result.isEmpty(), "Result is null!");
        
        System.out.println("Result:");
        System.out.println(result);
        System.out.println("AdminPanelDTO: blacklist: " + adminPanelDTO.blacklist());
        
        testEnd("DelBlacklistProcessor", "apply()");
    }
    
    @AfterClass
    public void closeMethod() {
        TestServer.closeServer = true;
    }
}
