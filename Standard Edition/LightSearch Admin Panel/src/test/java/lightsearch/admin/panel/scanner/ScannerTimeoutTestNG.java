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

import lightsearch.admin.panel.data.ScannerTimeoutDTO;
import lightsearch.admin.panel.data.creator.ScannerTimeoutDTOCreator;
import lightsearch.admin.panel.data.creator.ScannerTimeoutDTOCreatorInit;
import lightsearch.admin.panel.exception.ScannerException;
import lightsearch.admin.panel.scanner.ScannerTimeout;
import lightsearch.admin.panel.scanner.ScannerTimeoutInit;
import static org.testng.Assert.*;
import static test.message.TestMessage.*;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 *
 * @author ViiSE
 */
public class ScannerTimeoutTestNG {
    
    private ScannerTimeout scanner;
    
    @BeforeClass
    public void setUpMethod() {
        ScannerTimeoutDTOCreator scToutDTOCreator = ScannerTimeoutDTOCreatorInit.scannerTimeoutDTOCreator();
        assertNotNull(scToutDTOCreator, "ScannerTimeoutDTOCreator is null!");

        ScannerTimeoutDTO scannerTimeoutDTO = scToutDTOCreator.createScannerTimeoutDTO();
        scanner = ScannerTimeoutInit.scannerTimeout(scannerTimeoutDTO);
        assertNotNull(scanner, "ScannerTimeout is null!");
    }
    
    @Test
    public void scanTimeoutValue() {
        testBegin("ScannerTimeout", "scanTimeoutValue()");
        
        try {
            System.out.print("Input timeout value: ");
            String toutValue = scanner.scanTimeoutValue();
            System.out.println("Scan timeout value: " + toutValue);
        } catch(ScannerException ex) {
            catchMessage(ex);
        }
        
        testEnd("ScannerTimeout", "scanTimeoutValue()");
    }
}
