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
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class AdminPanelDTOCreatorTestNG {
    
    @Test
    public void createAdminPanelDTO() {
        testBegin("AdminPanelDTOCreator", "createAdminPanelDTO()");
        
        AdminPanelDTOCreator admPanelDTOCreator = 
                AdminPanelDTOCreatorInit.adminPanelDTOCreator();
        assertNotNull(admPanelDTOCreator, "AdminPanelDTOCreator is null!");
        
        System.out.println("AdminPanelDTOCreator: " + admPanelDTOCreator);
        
        AdminPanelDTO admPanelDTO = admPanelDTOCreator.createAdminPanelDTO();
        assertNotNull(admPanelDTO, "AdminPanelDTO is null!");
        
        System.out.println("AdminPanelDTO: " + admPanelDTO);
        
        testEnd("AdminPanelDTOCreator", "createAdminPanelDTO()");
    }
}
