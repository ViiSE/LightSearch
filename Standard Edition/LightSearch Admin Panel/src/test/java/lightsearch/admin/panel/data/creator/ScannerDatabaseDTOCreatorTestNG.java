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
package lightsearch.admin.panel.data.creator;

import lightsearch.admin.panel.data.ScannerDatabaseDTO;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class ScannerDatabaseDTOCreatorTestNG {
    
    @Test
    public void createScannerDatabaseDTO() {
        testBegin("ScannerDatabaseDTOCreator", "createScannerDatabaseDTO()");
        
        ScannerDatabaseDTOCreator scDbDTOCreator = ScannerDatabaseDTOCreatorInit.scannerDatabaseDTOCreator();
        assertNotNull(scDbDTOCreator, "ScannerDatabaseDTOCreator is null!");
        
        System.out.println("ScannerDatabaseDTOCreator: " + scDbDTOCreator);
        
        ScannerDatabaseDTO scDbDTO = scDbDTOCreator.createScannerDatabaseDTO();
        assertNotNull(scDbDTO, "ScannerDatabaseDTO is null!");
        
        System.out.println("ScannerDatabaseDTO: " + scDbDTO);
        
        testEnd("ScannerDatabaseDTOCreator", "createScannerDatabaseDTO()");
    }
}
