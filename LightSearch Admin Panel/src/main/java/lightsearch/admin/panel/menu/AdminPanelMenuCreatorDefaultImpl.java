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

/**
 *
 * @author ViiSE
 */
public class AdminPanelMenuCreatorDefaultImpl implements AdminPanelMenuCreator {

    @Override
    public AdminPanelMenu createAdminMenu() {
        String menu = "+----------------------------------+\n" +
                      "|1.  Restart server                |\n" +
                      "|2.  Set server timeout            |\n" +
                      "|3.  Set clients timeout           |\n" +
                      "|4.  Request clients list          |\n" +
                      "|5.  Kick client                   |\n" +
                      "|6.  Request blacklist             |\n" +
                      "|7.  Add client to the blacklist   |\n" +
                      "|8.  Remove client to the blacklist|\n" +
                      "|9.  Create new admin              |\n" +
                      "|10. Change database               |\n" +
                      "|11. Exit                          |\n" +
                      "+----------------------------------+\n" +
                      "Choose command: ";
        AdminPanelMenu adminMenu = AdminPanelMenuInit.adminMenu(menu);
        
        return adminMenu;
    }
    
}
