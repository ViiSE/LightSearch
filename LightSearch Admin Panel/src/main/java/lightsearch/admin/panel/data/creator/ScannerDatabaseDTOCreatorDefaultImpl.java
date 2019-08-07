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
import lightsearch.admin.panel.data.ScannerDatabaseDTO;
import lightsearch.admin.panel.data.ScannerDatabaseDTOInit;
import lightsearch.admin.panel.validate.DatabaseNameValidator;
import lightsearch.admin.panel.validate.DatabaseNameValidatorInit;
import lightsearch.admin.panel.validate.IPValidator;
import lightsearch.admin.panel.validate.IPValidatorInit;
import lightsearch.admin.panel.validate.PortValidator;
import lightsearch.admin.panel.validate.PortValidatorInit;

/**
 *
 * @author ViiSE
 */
public class ScannerDatabaseDTOCreatorDefaultImpl implements ScannerDatabaseDTOCreator {

    @Override
    public ScannerDatabaseDTO createScannerDatabaseDTO() {
        Scanner scanner = new Scanner(System.in);
        IPValidator ipValidator = IPValidatorInit.ipValidator();
        PortValidator portValidator = PortValidatorInit.portValidator();
        DatabaseNameValidator dbNameValidator = DatabaseNameValidatorInit.databaseNameValidator();
        
        ScannerDatabaseDTO scannerDTO = ScannerDatabaseDTOInit.scannerDatabaseDTO(
                scanner, ipValidator, portValidator, dbNameValidator);
        return scannerDTO;
    }
}
