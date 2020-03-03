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

import lightsearch.admin.panel.data.ScannerClientValueDTO;
import lightsearch.admin.panel.data.creator.ScannerClientValueDTOCreator;
import lightsearch.admin.panel.data.creator.ScannerClientValueDTOCreatorInit;
import lightsearch.admin.panel.exception.ScannerException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;
import static test.message.TestMessage.*;

/**
 *
 * @author ViiSE
 */
public class ScannerClientValueTestNG {

    private ScannerClientValueDTO scannerClientValueDTO;

    @BeforeClass
    public void setUpMethod() {
        ScannerClientValueDTOCreator scClValDTOCreator = ScannerClientValueDTOCreatorInit.scannerClientValueDTOCreator();
        assertNotNull(scClValDTOCreator, "ScannerClientValueDTOCreator is null!");
        
        scannerClientValueDTO = scClValDTOCreator.createScannerClientValueDTO();
    }
    
    @Test
    public void scanValue() {
        testBegin("ScannerClientValue", "scanValue()");

        try {
            assertNotNull(scannerClientValueDTO, "ScannerClientValueDTOCreator is null!");

            ScannerClientValue scClVal = ScannerClientValueInit.scannerClientValue(scannerClientValueDTO);
            System.out.print("Input IMEI, or number in clients list or blacklist: ");
            String value = scClVal.scanValue();
            System.out.println("Scan value: " + value);
        } catch (ScannerException ex) {
            catchMessage(ex);
        }

        testEnd("ScannerClientValue", "scanValue()");
    }
}
