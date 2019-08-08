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

import lightsearch.admin.panel.data.ScannerRestartDTO;
import lightsearch.admin.panel.data.creator.ScannerRestartDTOCreator;
import lightsearch.admin.panel.data.creator.ScannerRestartDTOCreatorInit;
import static org.testng.Assert.*;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class ScannerRestartDTOCreatorTestNG {
    
    @Test
    public void createScannerRestartDTO() {
        testBegin("ScannerRestartDTOCreator", "createScannerRestartDTO()");
        
        ScannerRestartDTOCreator scResDTOCreator = 
                ScannerRestartDTOCreatorInit.scannerRestartDTOCreator();
        assertNotNull(scResDTOCreator, "ScannerRestartDTOCreator is null!");
        
        System.out.println("ScannerRestartDTOCreator: " + scResDTOCreator);
        
        ScannerRestartDTO scResDTO = 
                scResDTOCreator.createScannerRestartDTO();
        assertNotNull(scResDTO, "ScannerRestartDTO is null!");
        
        System.out.println("ScannerRestartDTO: " + scResDTO);
        
        testEnd("ScannerRestartDTOCreator", "createScannerRestartDTO()");
    }
}
