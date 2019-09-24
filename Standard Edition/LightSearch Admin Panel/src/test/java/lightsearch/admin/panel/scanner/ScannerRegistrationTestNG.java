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
package lightsearch.admin.panel.scanner;

import lightsearch.admin.panel.data.ScannerRegistrationDTO;
import lightsearch.admin.panel.data.creator.ScannerRegistrationDTOCreator;
import lightsearch.admin.panel.data.creator.ScannerRegistrationDTOCreatorInit;
import lightsearch.admin.panel.exception.ScannerException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;
import static test.message.TestMessage.*;

/**
 *
 * @author ViiSE
 */
public class ScannerRegistrationTestNG {
    
    private ScannerRegistration scanner;
    
    @BeforeClass
    public void setUpMethod() {
        ScannerRegistrationDTOCreator scRegDTOCreator = 
                ScannerRegistrationDTOCreatorInit.scannerRegistrationDTOCreator();
        assertNotNull(scRegDTOCreator, "ScannerRegistrationDTOCreator is null!");

        ScannerRegistrationDTO scannerRegistrationDTO = scRegDTOCreator.createScannerRegistrationDTO();
        scanner = ScannerRegistrationInit.scannerRegistration(scannerRegistrationDTO);
        assertNotNull(scanner, "ScannerRegistration is null!");
    }
    
    @Test
    public void scanAdminName() {
        testBegin("ScannerRegistration", "scanAdminName()");
        
        try {
            System.out.print("Input admin name: ");
            String adminName = scanner.scanAdminName();
            System.out.println("Scan admin name: " + adminName);
        } catch(ScannerException ex) {
            catchMessage(ex);
        }
        
        testEnd("ScannerRegistration", "scanAdminName()");
    }
    
    @Test
    public void scanPort() {
        testBegin("ScannerRegistration", "scanAdminPassword()");
        
        try {
            System.out.print("Input admin password: ");
            String password = scanner.scanAdminPassword();
            System.out.println("Scan admin password: " + password);
        } catch(ScannerException ex) {
            catchMessage(ex);
        }
        
        testEnd("ScannerRegistration", "scanAdminPassword()");
    }
}
