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
package lightsearch.admin.panel.data;

import lightsearch.admin.panel.validate.IPValidator;
import lightsearch.admin.panel.validate.IPValidatorInit;
import lightsearch.admin.panel.validate.PortValidator;
import lightsearch.admin.panel.validate.PortValidatorInit;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Scanner;

import static org.testng.Assert.assertNotNull;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class ScannerConnectionDTOTestNG {
    
    private ScannerConnectionDTO scannerDTO;

    @BeforeClass
    public void setUpClass() {
        Scanner scanner = new Scanner(System.in);
        IPValidator ipValidator = IPValidatorInit.ipValidator();
        PortValidator portValidator = PortValidatorInit.portValidator();
        assertNotNull(scanner, "Scanner is null!");
        assertNotNull(ipValidator, "IP validator is null!");
        assertNotNull(portValidator, "Port validator is null!");

        scannerDTO = ScannerConnectionDTOInit.scannerConnectionDTO(scanner, ipValidator, portValidator);
        assertNotNull(scannerDTO, "ScannerConnectionDTO is null!");
    }
    
    @Test
    public void scanner() {
        testBegin("ScannerConnectionDTO", "scanner()");
        
        System.out.println("scannerDTO.scanner(): " + scannerDTO.scanner());
        
        testEnd("ScannerConnectionDTO", "scanner()");
    }
    
    @Test
    public void ipValidator() {
        testBegin("ScannerConnectionDTO", "ipValidator()");
        
        System.out.println("scannerDTO.ipValidator(): " + scannerDTO.ipValidator());
        
        testEnd("ScannerConnectionDTO", "ipValidator()");
    }
    
    @Test
    public void portValidator() {
        testBegin("ScannerConnectionDTO", "portValidator()");
        
        System.out.println("scannerDTO.portValidator(): " + scannerDTO.portValidator());
        
        testEnd("ScannerConnectionDTO", "portValidator()");
    }
}
