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

import lightsearch.admin.panel.data.ScannerChooserCommandDTO;
import lightsearch.admin.panel.data.creator.ScannerChooserCommandDTOCreator;
import lightsearch.admin.panel.data.creator.ScannerChooserCommandDTOCreatorInit;
import static org.testng.Assert.*;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class ScannerChooserCommandDTOCreatorTestNG {
    
    @Test
    public void createScannerChooserCommandDTO() {
        testBegin("ScannerChooserCommandDTOCreator", "createScannerChooserCommandDTO()");
        
        ScannerChooserCommandDTOCreator scChCmdDTOCreator = 
                ScannerChooserCommandDTOCreatorInit.scannerChooserCommandDTOCreator();
        assertNotNull(scChCmdDTOCreator, "ScannerChooserCommandDTOCreator is null!");
        
        System.out.println("ScannerChooserCommandDTOCreator: " + scChCmdDTOCreator);
        
        ScannerChooserCommandDTO scChCmdDTO = 
                scChCmdDTOCreator.createScannerChooserCommandDTO();
        assertNotNull(scChCmdDTO, "ScannerChooserCommandDTO is null!");
        
        System.out.println("ScannerChooserCommandDTO: " + scChCmdDTO);
        
        testEnd("ScannerChooserCommandDTOCreator", "createScannerChooserCommandDTO()");
    }
}
