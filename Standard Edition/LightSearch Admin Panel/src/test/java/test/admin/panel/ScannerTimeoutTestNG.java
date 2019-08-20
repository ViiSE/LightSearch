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

import lightsearch.admin.panel.data.ScannerTimeoutDTO;
import lightsearch.admin.panel.data.creator.ScannerTimeoutDTOCreator;
import lightsearch.admin.panel.data.creator.ScannerTimeoutDTOCreatorInit;
import lightsearch.admin.panel.exception.ScannerException;
import lightsearch.admin.panel.scanner.ScannerTimeout;
import lightsearch.admin.panel.scanner.ScannerTimeoutInit;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class ScannerTimeoutTestNG {
    
    private ScannerTimeoutDTO scannerTimeoutDTO;
    
    @BeforeTest
    public void setUpMethod() {
        ScannerTimeoutDTOCreator scToutDTOCreator = 
                ScannerTimeoutDTOCreatorInit.scannerTimeoutDTOCreator();
        assertNotNull(scToutDTOCreator, "ScannerTimeoutDTOCreator is null!");
        
        scannerTimeoutDTO = scToutDTOCreator.createScannerTimeoutDTO();
    }
    
    @Test
    public void scanTimeoutValue() {
        testBegin("ScannerTimeout", "scanTimeoutValue()");
        
        try {
            assertNotNull(scannerTimeoutDTO, "ScannerTimeoutDTO is null!");
            
            ScannerTimeout scanner = 
                    ScannerTimeoutInit.scannerTimeout(scannerTimeoutDTO);
            assertNotNull(scanner, "ScannerTimeout is null!");
            
            System.out.print("Input timeout value: ");
            String toutValue = scanner.scanTimeoutValue();
            System.out.println("Scan timeout value: " + toutValue);
        }
        catch(ScannerException ex) {
            System.out.println("CATCH! Message: " + ex.getMessage());
        }
        
        testEnd("ScannerTimeout", "scanTimeoutValue()");
    }
}
