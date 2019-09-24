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

import lightsearch.admin.panel.data.creator.AdminPanelDTOCreatorInit;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class AdminPanelDTOTestNG {
    
    private AdminPanelDTO adminPanelDTO;
    
    @BeforeClass
    public void setUpMethod() {
        adminPanelDTO = AdminPanelDTOCreatorInit.adminPanelDTOCreator().createAdminPanelDTO();
        assertNotNull(adminPanelDTO, "AdminPanelDTO is null!");
    }
    
    @Test
    public void clients() {
        testBegin("AdminPanelDTO", "clients()");
        
        assertNotNull(adminPanelDTO.clients(), "AdminPanelDTO: clients is null!");
        System.out.println("AdminPanelDTO: clients: " + adminPanelDTO.clients());
        
        testEnd("AdminPanelDTO", "clients()");
    }
    
    @Test
    public void blacklist() {
        testBegin("AdminPanelDTO", "blacklist()");

        assertNotNull(adminPanelDTO.blacklist(), "AdminPanelDTO: blacklist is null!");
        System.out.println("AdminPanelDTO: blacklist: " + adminPanelDTO.blacklist());
        
        testEnd("AdminPanelDTO", "blacklist()");
    }
}
