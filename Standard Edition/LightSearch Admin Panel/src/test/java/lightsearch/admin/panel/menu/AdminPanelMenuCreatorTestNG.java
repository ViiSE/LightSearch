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
package lightsearch.admin.panel.menu;

import lightsearch.admin.panel.menu.AdminPanelMenuCreatorInit;
import static org.testng.Assert.*;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;
import org.testng.annotations.Test;
import lightsearch.admin.panel.menu.AdminPanelMenu;
import lightsearch.admin.panel.menu.AdminPanelMenuCreator;

/**
 *
 * @author ViiSE
 */
public class AdminPanelMenuCreatorTestNG {
    
    @Test
    public void createAdminMenu() {
        testBegin("AdminPanelMenuCreator", "createAdminMenu()");
        
        AdminPanelMenuCreator adminMenuCreator = AdminPanelMenuCreatorInit.adminMenuCreator();
        AdminPanelMenu adminMenu = adminMenuCreator.createAdminMenu();
        assertNotNull(adminMenu, "Admin menu is null!");
        adminMenu.menu();
           
        testEnd("AdminPanelMenuCreator", "createAdminMenu()");
    }
}
