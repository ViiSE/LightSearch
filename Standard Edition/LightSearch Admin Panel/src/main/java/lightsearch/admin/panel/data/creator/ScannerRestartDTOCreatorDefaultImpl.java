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

import lightsearch.admin.panel.data.ScannerRestartDTO;
import lightsearch.admin.panel.data.ScannerRestartDTOInit;
import lightsearch.admin.panel.validate.RestartValidator;
import lightsearch.admin.panel.validate.RestartValidatorInit;

import java.util.Scanner;

/**
 *
 * @author ViiSE
 */
public class ScannerRestartDTOCreatorDefaultImpl implements ScannerRestartDTOCreator {

    @Override
    public ScannerRestartDTO createScannerRestartDTO() {
        Scanner scanner = new Scanner(System.in);
        RestartValidator restartValidator = RestartValidatorInit.restartValidator();
        ScannerRestartDTO scannerRestartDTO = 
                ScannerRestartDTOInit.scannerRestartDTO(scanner, restartValidator);
        return scannerRestartDTO;
    }
}
