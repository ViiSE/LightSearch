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

import lightsearch.admin.panel.data.ScannerRestartDTO;
import lightsearch.admin.panel.data.creator.ScannerRestartDTOCreator;
import lightsearch.admin.panel.data.creator.ScannerRestartDTOCreatorInit;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class ScannerRestartDTOTestNG {
    
    private ScannerRestartDTOCreator scResDTOCreator;
    
    @BeforeTest
    public void setUpMethod() {
        scResDTOCreator = 
                ScannerRestartDTOCreatorInit.scannerRestartDTOCreator();
        assertNotNull(scResDTOCreator, "ScannerRestartDTOCreator is null!");
    }
    
    @Test
    public void scanner() {
        testBegin("ScannerRestartDTO", "scanner()");
        
        ScannerRestartDTO scRegDTO = scResDTOCreator.createScannerRestartDTO();
        assertNotNull(scRegDTO, "ScannerRestartDTO is null!");
        assertNotNull(scRegDTO.scanner(), "ScannerRestartDTO: scanner is null!");
        
        System.out.println("ScannerRestartDTO: scanner: " + scRegDTO.scanner());
        
        testEnd("ScannerRestartDTO", "scanner()");
    }
    
    @Test
    public void restartValidator() {
        testBegin("ScannerRestartDTO", "restartValidator()");
        
        ScannerRestartDTO scRegDTO = scResDTOCreator.createScannerRestartDTO();
        assertNotNull(scRegDTO, "ScannerRestartDTO is null!");
        assertNotNull(scRegDTO.restartValidator(), 
                "ScannerRestartDTO: restartValidator is null!");
        
        System.out.println("ScannerRestartDTO: restartValidator: " 
                + scRegDTO.restartValidator());
        
        testEnd("ScannerRestartDTO", "restartValidator()");
    }
}
