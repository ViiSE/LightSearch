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

import lightsearch.admin.panel.data.ScannerConnectionDTO;
import lightsearch.admin.panel.data.creator.ScannerConnectionDTOCreator;
import lightsearch.admin.panel.data.creator.ScannerConnectionDTOCreatorInit;
import static org.testng.Assert.*;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class ScannerConnectionDTOCreatorTestNG {
    
    @Test
    public void createScannerConnectionDTO() {
        testBegin("ScannerConnectionDTOCreator", "createScannerConnectionDTO()");
        
        ScannerConnectionDTOCreator scConnDTOCreator = 
                ScannerConnectionDTOCreatorInit.scannerConnectionDTOCreator();
        assertNotNull(scConnDTOCreator, "ScannerConnectionDTOCreator is null!");
        
        System.out.println("ScannerConnectionDTOCreator: " + scConnDTOCreator);
        
        ScannerConnectionDTO scConnDTO = 
                scConnDTOCreator.createScannerConnectionDTO();
        assertNotNull(scConnDTO, "ScannerConnectionDTO is null!");
        
        System.out.println("ScannerConnectionDTO: " + scConnDTO);
        
        testEnd("ScannerConnectionDTOCreator", "createScannerConnectionDTO()");
    }
}
