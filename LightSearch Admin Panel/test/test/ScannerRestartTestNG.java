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

import lightsearch.admin.panel.data.ScannerRestartDTO;
import lightsearch.admin.panel.data.creator.ScannerRestartDTOCreator;
import lightsearch.admin.panel.data.creator.ScannerRestartDTOCreatorInit;
import lightsearch.admin.panel.exception.ScannerException;
import lightsearch.admin.panel.scanner.ScannerRestart;
import lightsearch.admin.panel.scanner.ScannerRestartInit;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class ScannerRestartTestNG {
    
    private ScannerRestartDTO scannerRestartDTO;
    
    @BeforeTest
    public void setUpMethod() {
        ScannerRestartDTOCreator scResDTOCreator = 
                ScannerRestartDTOCreatorInit.scannerRestartDTOCreator();
        assertNotNull(scResDTOCreator, "ScannerRestartDTOCreator is null!");
        
        scannerRestartDTO = scResDTOCreator.createScannerRestartDTO();
    }
    
    @Test
    public void scanAnswer() {
        testBegin("ScannerRestart", "scanAnswer()");
        
        try {
            assertNotNull(scannerRestartDTO, "ScannerRestartDTO is null!");
            
            ScannerRestart scanner = 
                    ScannerRestartInit.scannerRestart(scannerRestartDTO);
            assertNotNull(scanner, "ScannerRestart is null!");
            
            System.out.print("Input y|yes or n|no: ");
            String answer = scanner.scanAnswer();
            System.out.println("Scan answer: " + answer);
        }
        catch(ScannerException ex) {
            System.out.println("CATCH! Message: " + ex.getMessage());
        }
        
        testEnd("ScannerRestart", "scanAnswer()");
    }
}
