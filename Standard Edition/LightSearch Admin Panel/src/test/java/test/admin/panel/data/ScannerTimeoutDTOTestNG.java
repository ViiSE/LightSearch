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
package test.admin.panel.data;

import lightsearch.admin.panel.data.ScannerTimeoutDTO;
import lightsearch.admin.panel.data.creator.ScannerTimeoutDTOCreator;
import lightsearch.admin.panel.data.creator.ScannerTimeoutDTOCreatorInit;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class ScannerTimeoutDTOTestNG {
    
    private ScannerTimeoutDTOCreator scToutDTOCreator;
    
    @BeforeTest
    public void setUpMethod() {
        scToutDTOCreator = 
                ScannerTimeoutDTOCreatorInit.scannerTimeoutDTOCreator();
        assertNotNull(scToutDTOCreator, "ScannerTimeoutDTOCreator is null!");
    }
    
    @Test
    public void scanner() {
        testBegin("ScannerTimeoutDTO", "scanner()");
        
        ScannerTimeoutDTO scToutDTO = scToutDTOCreator.createScannerTimeoutDTO();
        assertNotNull(scToutDTO, "ScannerTimeoutDTO is null!");
        assertNotNull(scToutDTO.scanner(), "ScannerTimeoutDTO: scanner is null!");
        
        System.out.println("ScannerTimeoutDTO: scanner: " + scToutDTO.scanner());
        
        testEnd("ScannerTimeoutDTO", "scanner()");
    }
    
    @Test
    public void timeoutValidator() {
        testBegin("ScannerTimeoutDTO", "timeoutValidator()");
        
        ScannerTimeoutDTO scToutDTO = scToutDTOCreator.createScannerTimeoutDTO();
        assertNotNull(scToutDTO, "ScannerTimeoutDTO is null!");
        assertNotNull(scToutDTO.timeoutValidator(), 
                "ScannerTimeoutDTO: timeoutValidator is null!");
        
        System.out.println("ScannerTimeoutDTO: timeoutValidator: " 
                + scToutDTO.timeoutValidator());
        
        testEnd("ScannerTimeoutDTO", "timeoutValidator()");
    }
}
