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

import lightsearch.admin.panel.data.ConnectionDTO;
import lightsearch.admin.panel.data.ScannerConnectionDTO;
import lightsearch.admin.panel.data.creator.ConnectionDTOCreator;
import lightsearch.admin.panel.data.creator.ConnectionDTOCreatorInit;
import lightsearch.admin.panel.data.creator.ScannerConnectionDTOCreator;
import lightsearch.admin.panel.data.creator.ScannerConnectionDTOCreatorInit;
import lightsearch.admin.panel.print.AdminPanelPrinter;
import lightsearch.admin.panel.print.AdminPanelPrinterInit;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class ConnectionDTOCreatorTestNG {
    
    private AdminPanelPrinter printer;
    private ScannerConnectionDTO scannerDTO;
    
    @BeforeTest
    public void setUpMethod() {
        printer = AdminPanelPrinterInit.adminPanelPrinter();
        
        ScannerConnectionDTOCreator scConnDTOCreator = 
                ScannerConnectionDTOCreatorInit.scannerConnectionDTOCreator();
        assertNotNull(scConnDTOCreator, "ScannerConnectionDTOCreator is null!");
        
        scannerDTO = scConnDTOCreator.createScannerConnectionDTO();
    }
    
    @Test
    public void createConnectionDTO() {
        testBegin("ConnectionDTOCreator", "createConnectionDTOCreator()");
        
        assertNotNull(printer, "AdminPanelPrinter is null!");
        assertNotNull(scannerDTO, "ScannerConnectionDTO is null!");
        
        ConnectionDTOCreator connDTOCreator = 
                ConnectionDTOCreatorInit.connectionDTOCreator(printer, scannerDTO);
        assertNotNull(connDTOCreator, "ConnectionDTOCreator is null!");
        
        System.out.println("ConnectionDTOCreator: " + connDTOCreator);
        
        ConnectionDTO connDTO = connDTOCreator.createConnectionDTO();
        assertNotNull(connDTO, "ConnectionDTO is null!");
        
        System.out.println("ConnectionDTO: " + connDTO);
        
        testEnd("ConnectionDTOCreator", "createConnectionDTOCreator()");
    }
}
