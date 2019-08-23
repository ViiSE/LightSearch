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

import lightsearch.admin.panel.data.ScannerClientValueDTO;
import lightsearch.admin.panel.data.creator.ScannerClientValueDTOCreator;
import lightsearch.admin.panel.data.creator.ScannerClientValueDTOCreatorInit;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class ScannerClientValueDTOTestNG {
    
    private ScannerClientValueDTOCreator scClValDTOCreator;
    
    @BeforeTest
    public void setUpMethod() {
        scClValDTOCreator = 
                ScannerClientValueDTOCreatorInit.scannerClientValueDTOCreator();
        assertNotNull(scClValDTOCreator, "ScannerClientValueDTOCreator is null!");
    }
    
    @Test
    public void scanner() {
        testBegin("ScannerClientValueDTO", "scanner()");
        
        ScannerClientValueDTO scClValDTO = scClValDTOCreator.createScannerClientValueDTO();
        assertNotNull(scClValDTO, "ScannerClientValueDTO is null!");
        assertNotNull(scClValDTO.scanner(), "ScannerClientValueDTO: scanner is null!");
        
        System.out.println("ScannerClientValueDTO: scanner: " + scClValDTO.scanner());
        
        testEnd("ScannerClientValueDTO", "scanner()");
    }
    
    @Test
    public void IMEIValidator() {
        testBegin("ScannerClientValueDTO", "IMEIValidator()");
        
        ScannerClientValueDTO scClValDTO = scClValDTOCreator.createScannerClientValueDTO();
        assertNotNull(scClValDTO, "ScannerClientValueDTO is null!");
        assertNotNull(scClValDTO.IMEIValidator(), "ScannerClientValueDTO: IMEIValidator is null!");
        
        System.out.println("ScannerClientValueDTO: IMEIValidator: " + scClValDTO.IMEIValidator());
        
        testEnd("ScannerClientValueDTO", "IMEIValidator()");
    }
    
    @Test
    public void numberValidator() {
        testBegin("ScannerClientValueDTO", "numberValidator()");
        
        ScannerClientValueDTO scClValDTO = scClValDTOCreator.createScannerClientValueDTO();
        assertNotNull(scClValDTO, "ScannerClientValueDTO is null!");
        assertNotNull(scClValDTO.numberValidator(), 
                "ScannerClientValueDTO: numberValidator is null!");
        
        System.out.println("ScannerClientValueDTO: numberValidator: " 
                + scClValDTO.numberValidator());
        
        testEnd("ScannerClientValueDTO", "numberValidator()");
    }
}