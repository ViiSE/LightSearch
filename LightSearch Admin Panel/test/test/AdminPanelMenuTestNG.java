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

import java.util.Map;
import lightsearch.admin.panel.menu.AdminPanelMenuInit;
import static org.testng.Assert.*;
import org.testng.annotations.Test;
import lightsearch.admin.panel.menu.AdminPanelMenu;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class AdminPanelMenuTestNG {
    
    @Test
    public void menu() {
        testBegin("AdminPanelMenu", "menu()");
        
        String menu = "I am menu";
        assertNotNull(menu, "Menu string is null!");
        assertFalse(menu.equals(""), "Menu string is null!");
        AdminPanelMenu adminMenu = AdminPanelMenuInit.adminMenu(menu);
        adminMenu.menu();
        
        testEnd("AdminPanelMenu", "menu()");
    }
}
