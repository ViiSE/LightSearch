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
package test.admin.panel;

import lightsearch.admin.panel.data.ScannerRegistrationDTO;
import lightsearch.admin.panel.data.creator.ScannerRegistrationDTOCreator;
import lightsearch.admin.panel.data.creator.ScannerRegistrationDTOCreatorInit;
import lightsearch.admin.panel.exception.ScannerException;
import lightsearch.admin.panel.scanner.ScannerRegistration;
import lightsearch.admin.panel.scanner.ScannerRegistrationInit;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class ScannerRegistrationTestNG {
    
    private ScannerRegistrationDTO scannerRegistrationDTO;
    
    @BeforeTest
    public void setUpMethod() {
        ScannerRegistrationDTOCreator scRegDTOCreator = 
                ScannerRegistrationDTOCreatorInit.scannerRegistrationDTOCreator();
        assertNotNull(scRegDTOCreator, "ScannerRegistrationDTOCreator is null!");
        
        scannerRegistrationDTO = scRegDTOCreator.createScannerRegistrationDTO();
    }
    
    @Test
    public void scanAdminName() {
        testBegin("ScannerRegistration", "scanAdminName()");
        
        try {
            assertNotNull(scannerRegistrationDTO, "ScannerRegistrationDTO is null!");
            
            ScannerRegistration scanner = 
                    ScannerRegistrationInit.scannerRegistration(scannerRegistrationDTO);
            assertNotNull(scanner, "ScannerRegistration is null!");
            
            System.out.print("Input admin name: ");
            String adminName = scanner.scanAdminName();
            System.out.println("Scan admin name: " + adminName);
        }
        catch(ScannerException ex) {
            System.out.println("CATCH! Message: " + ex.getMessage());
        }
        
        testEnd("ScannerRegistration", "scanAdminName()");
    }
    
    @Test
    public void scanPort() {
        testBegin("ScannerRegistration", "scanAdminPassword()");
        
        try {
            assertNotNull(scannerRegistrationDTO, "ScannerRegistrationDTO is null!");
            
            ScannerRegistration scanner = 
                    ScannerRegistrationInit.scannerRegistration(scannerRegistrationDTO);
            assertNotNull(scanner, "ScannerRegistration is null!");
            
            System.out.print("Input admin password: ");
            String password = scanner.scanAdminPassword();
            System.out.println("Scan admin password: " + password);
        }
        catch(ScannerException ex) {
            System.out.println("CATCH! Message: " + ex.getMessage());
        }
        
        testEnd("ScannerRegistration", "scanAdminPassword()");
    }
}
