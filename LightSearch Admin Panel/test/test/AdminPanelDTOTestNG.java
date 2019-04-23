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
package test;

import lightsearch.admin.panel.data.AdminPanelDTO;
import lightsearch.admin.panel.data.creator.AdminPanelDTOCreator;
import lightsearch.admin.panel.data.creator.AdminPanelDTOCreatorInit;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class AdminPanelDTOTestNG {
    
    private AdminPanelDTOCreator adminPanelDTOCreator;
    
    @BeforeTest
    public void setUpMethod() {
        adminPanelDTOCreator = AdminPanelDTOCreatorInit.adminPanelDTOCreator();
        assertNotNull(adminPanelDTOCreator, "AdminPanelDTOCreator is null!");
    }
    
    @Test
    public void clients() {
        testBegin("AdminPanelDTO", "clients()");
        
        AdminPanelDTO admPanelDTO = adminPanelDTOCreator.createAdminPanelDTO();
        assertNotNull(admPanelDTO, "AdminPanelDTO is null!");
        assertNotNull(admPanelDTO.clients(), "AdminPanelDTO: clients is null!");
        
        System.out.println("AdminPanelDTO: clients: " + admPanelDTO.clients());
        
        testEnd("AdminPanelDTO", "clients()");
    }
    
    @Test
    public void blacklist() {
        testBegin("AdminPanelDTO", "blacklist()");
        
        AdminPanelDTO admPanelDTO = adminPanelDTOCreator.createAdminPanelDTO();
        assertNotNull(admPanelDTO, "AdminPanelDTO is null!");
        assertNotNull(admPanelDTO.blacklist(), "AdminPanelDTO: blacklist is null!");
        
        System.out.println("AdminPanelDTO: blacklist: " + admPanelDTO.blacklist());
        
        testEnd("AdminPanelDTO", "blacklist()");
    }
}
