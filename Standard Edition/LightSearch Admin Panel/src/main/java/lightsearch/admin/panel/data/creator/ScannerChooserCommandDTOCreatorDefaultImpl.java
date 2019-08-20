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

import java.util.Scanner;
import lightsearch.admin.panel.data.ScannerChooserCommandDTO;
import lightsearch.admin.panel.data.ScannerChooserCommandDTOInit;
import lightsearch.admin.panel.validate.CommandValidator;
import lightsearch.admin.panel.validate.CommandValidatorInit;

/**
 *
 * @author ViiSE
 */
public class ScannerChooserCommandDTOCreatorDefaultImpl implements ScannerChooserCommandDTOCreator {

    @Override
    public ScannerChooserCommandDTO createScannerChooserCommandDTO() {
        Scanner scanner = new Scanner(System.in);
        CommandValidator commandValidator = CommandValidatorInit.commandValidator();
        ScannerChooserCommandDTO scannerDTO = ScannerChooserCommandDTOInit.
                scannerChooserCommandDTO(scanner, commandValidator);
        return scannerDTO;
    }
}
