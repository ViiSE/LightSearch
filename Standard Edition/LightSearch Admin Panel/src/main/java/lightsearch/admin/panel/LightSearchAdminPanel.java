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
package lightsearch.admin.panel;

import lightsearch.admin.panel.data.AdminPanelDTO;
import lightsearch.admin.panel.data.ScannerConnectionDTO;
import lightsearch.admin.panel.data.creator.AdminPanelDTOCreator;
import lightsearch.admin.panel.data.creator.AdminPanelDTOCreatorInit;
import lightsearch.admin.panel.data.creator.ScannerConnectionDTOCreator;
import lightsearch.admin.panel.data.creator.ScannerConnectionDTOCreatorInit;
import lightsearch.admin.panel.print.AdminPanelPrinter;
import lightsearch.admin.panel.print.AdminPanelPrinterInit;
import lightsearch.admin.panel.session.AdminPanelSession;
import lightsearch.admin.panel.session.AdminPanelSessionCreator;
import lightsearch.admin.panel.session.AdminPanelSessionCreatorInit;

/**
 *
 * @author ViiSE
 */
public class LightSearchAdminPanel {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {      
        System.out.println("LightSearch Admin Panel, v. 2.0.0");
        System.out.println("Welcome!");
        
        AdminPanelPrinter printer = AdminPanelPrinterInit.adminPanelPrinter();
        
        ScannerConnectionDTOCreator scannerDTOCreator = 
                ScannerConnectionDTOCreatorInit.scannerConnectionDTOCreator();
        ScannerConnectionDTO scannerConnectionDTO = 
                scannerDTOCreator.createScannerConnectionDTO();
        
        AdminPanelDTOCreator admPanelDTOCreator = 
                AdminPanelDTOCreatorInit.adminPanelDTOCreator();
        AdminPanelDTO adminPanelDTO = admPanelDTOCreator.createAdminPanelDTO();
        
        AdminPanelSessionCreator sessionCreator = 
                AdminPanelSessionCreatorInit.adminPanelSessionCreatorInteractive(
                        printer, scannerConnectionDTO, adminPanelDTO);
        AdminPanelSession session = sessionCreator.createSession();

        session.startSession();
    }
}
