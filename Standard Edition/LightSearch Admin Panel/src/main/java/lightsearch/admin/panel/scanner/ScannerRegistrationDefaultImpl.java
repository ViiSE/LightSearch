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

import lightsearch.admin.panel.data.ScannerRegistrationDTO;
import lightsearch.admin.panel.exception.ScannerException;
import lightsearch.admin.panel.exception.ValidatorException;

import java.util.Arrays;

/**
 *
 * @author ViiSE
 */
public class ScannerRegistrationDefaultImpl implements ScannerRegistration {
    
    private final ScannerRegistrationDTO scannerDTO;

    public ScannerRegistrationDefaultImpl(ScannerRegistrationDTO scannerDTO) {
        this.scannerDTO = scannerDTO;
    }

    @Override
    public String scanAdminName() throws ScannerException {
        try {
            String adminName = scannerDTO.scanner().nextLine();
            scannerDTO.adminNameValidator().validate(adminName);
            return adminName;
        } catch (ValidatorException ex) {
            throw new ScannerException(ex.getMessage());
        }
    }

    @Override
    public String scanAdminPassword() throws ScannerException {
        if(System.console() == null)
            throw new ScannerException("Not supported for this console!");
        
        char[] adminPassCh = System.console().readPassword();
        String adminPassSha = scannerDTO.hashAlgorithms().sha256(Arrays.toString(adminPassCh));
        return adminPassSha;
    }
}
