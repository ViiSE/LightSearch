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

import lightsearch.admin.panel.data.creator.ScannerChooserCommandDTOCreatorInit;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class ScannerChooserCommandDTOTestNG {
    
    private ScannerChooserCommandDTO scChCmdDTO;
    
    @BeforeClass
    public void setUpMethod() {
        scChCmdDTO = ScannerChooserCommandDTOCreatorInit.scannerChooserCommandDTOCreator().createScannerChooserCommandDTO();
        assertNotNull(scChCmdDTO, "ScannerChooserCommandDTO is null!");
    }
    
    @Test
    public void scanner() {
        testBegin("ScannerChooserCommandDTO", "scanner()");
        
        assertNotNull(scChCmdDTO.scanner(), "ScannerChooserCommandDTO: scanner is null!");
        System.out.println("ScannerChooserCommandDTO: scanner: " + scChCmdDTO.scanner());
        
        testEnd("ScannerChooserCommandDTO", "scanner()");
    }
    
    @Test
    public void commandValidator() {
        testBegin("ScannerChooserCommandDTO", "commandValidator()");
        
        assertNotNull(scChCmdDTO.commandValidator(), "ScannerChooserCommandDTO: commandValidator is null!");
        System.out.println("ScannerChooserCommandDTO: commandValidator: " + scChCmdDTO.commandValidator());
        
        testEnd("ScannerChooserCommandDTO", "commandValidator()");
    }
}
