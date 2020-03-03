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
package lightsearch.admin.panel.cmd.admin;

import lightsearch.admin.panel.data.*;
import lightsearch.admin.panel.data.creator.*;
import lightsearch.admin.panel.scanner.*;

/**
 *
 * @author ViiSE
 */
public class ScannerCommandHolderCreatorDefaultImpl implements ScannerCommandHolderCreator {

    @Override
    public ScannerCommandHolder createScannerCommandHolder() {
        ScannerRegistrationDTOCreator scRegDTOCreator =
                ScannerRegistrationDTOCreatorInit.scannerRegistrationDTOCreator();
        ScannerClientValueDTOCreator scClValDTOCreator =
                ScannerClientValueDTOCreatorInit.scannerClientValueDTOCreator();
        ScannerDatabaseDTOCreator scDbDTOCreator = 
                ScannerDatabaseDTOCreatorInit.scannerDatabaseDTOCreator();
        ScannerTimeoutDTOCreator scToutDTOCreator =
                ScannerTimeoutDTOCreatorInit.scannerTimeoutDTOCreator();
        ScannerRestartDTOCreator scResDTOCreator =
                ScannerRestartDTOCreatorInit.scannerRestartDTOCreator();
        
        ScannerRegistrationDTO scRegDTO = scRegDTOCreator.createScannerRegistrationDTO();
        ScannerClientValueDTO scClValDTO = scClValDTOCreator.createScannerClientValueDTO();
        ScannerDatabaseDTO scDbDTO = scDbDTOCreator.createScannerDatabaseDTO();
        ScannerTimeoutDTO scToutDTO = scToutDTOCreator.createScannerTimeoutDTO();
        ScannerRestartDTO scResDTO = scResDTOCreator.createScannerRestartDTO();
        
        ScannerRegistration scReg = ScannerRegistrationInit.scannerRegistration(scRegDTO);
        ScannerClientValue scClVal = ScannerClientValueInit.scannerClientValue(scClValDTO);
        ScannerDatabase scDb = ScannerDatabaseInit.scannerDatabase(scDbDTO);
        ScannerTimeout scTout = ScannerTimeoutInit.scannerTimeout(scToutDTO);
        ScannerRestart scRes = ScannerRestartInit.scannerRestart(scResDTO);

        return ScannerCommandHolderInit.scannerCommandHolder(scReg, scClVal, scDb, scTout, scRes);
    }
}
