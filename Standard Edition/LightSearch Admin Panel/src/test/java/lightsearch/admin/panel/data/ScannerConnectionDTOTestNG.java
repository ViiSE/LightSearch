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

import java.util.Scanner;
import lightsearch.admin.panel.data.ScannerConnectionDTO;
import lightsearch.admin.panel.data.ScannerConnectionDTOInit;
import lightsearch.admin.panel.validate.IPValidator;
import lightsearch.admin.panel.validate.IPValidatorInit;
import lightsearch.admin.panel.validate.PortValidator;
import lightsearch.admin.panel.validate.PortValidatorInit;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class ScannerConnectionDTOTestNG {
    
    private Scanner scanner;
    private IPValidator ipValidator;
    private PortValidator portValidator;
    
    @BeforeTest
    public void init() {
        scanner = new Scanner(System.in);
        ipValidator = IPValidatorInit.ipValidator();
        portValidator = PortValidatorInit.portValidator();
    }
    
    @Test
    public void scanner() {
        testBegin("ScannerConnectionDTO", "scanner()");
        
        assertNotNull(scanner, "Scanner is null!");
        assertNotNull(ipValidator, "IP validator is null!");
        assertNotNull(portValidator, "Port validator is null!");
        
        ScannerConnectionDTO scannerDTO = ScannerConnectionDTOInit.scannerConnectionDTO(scanner, ipValidator, portValidator);
        System.out.println("scannerDTO.scanner(): " + scannerDTO.scanner());
        
        testEnd("ScannerConnectionDTO", "scanner()");
    }
    
    @Test
    public void ipValidator() {
        testBegin("ScannerConnectionDTO", "ipValidator()");
        
        assertNotNull(scanner, "Scanner is null!");
        assertNotNull(ipValidator, "IP validator is null!");
        assertNotNull(portValidator, "Port validator is null!");
        
        ScannerConnectionDTO scannerDTO = ScannerConnectionDTOInit.scannerConnectionDTO(scanner, ipValidator, portValidator);
        System.out.println("scannerDTO.ipValidator(): " + scannerDTO.ipValidator());
        
        testEnd("ScannerConnectionDTO", "ipValidator()");
    }
    
    @Test
    public void portValidator() {
        testBegin("ScannerConnectionDTO", "portValidator()");
        
        assertNotNull(scanner, "Scanner is null!");
        assertNotNull(ipValidator, "IP validator is null!");
        assertNotNull(portValidator, "Port validator is null!");
        
        ScannerConnectionDTO scannerDTO = ScannerConnectionDTOInit.scannerConnectionDTO(scanner, ipValidator, portValidator);
        System.out.println("scannerDTO.portValidator(): " + scannerDTO.portValidator());
        
        testEnd("ScannerConnectionDTO", "portValidator()");
    }
}
