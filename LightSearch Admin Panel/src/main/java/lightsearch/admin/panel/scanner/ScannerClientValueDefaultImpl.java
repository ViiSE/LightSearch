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

import lightsearch.admin.panel.exception.ScannerException;
import lightsearch.admin.panel.exception.ValidatorException;
import lightsearch.admin.panel.data.ScannerClientValueDTO;

/**
 *
 * @author ViiSE
 */
public class ScannerClientValueDefaultImpl implements ScannerClientValue {
    
    private final ScannerClientValueDTO scannerDTO;

    public ScannerClientValueDefaultImpl(ScannerClientValueDTO scannerDTO) {
        this.scannerDTO = scannerDTO;
    }

    @Override
    public String scanValue() throws ScannerException {
        
        String value = scannerDTO.scanner().nextLine();
        try {
            String IMEI = value;
            scannerDTO.IMEIValidator().validate(IMEI);
            return IMEI;
        }
        catch(ValidatorException exIMEI) {
            try {
                scannerDTO.numberValidator().validate(value);
                return value;
            } 
            catch(ValidatorException exNumber) {
                throw new ScannerException(exIMEI.getMessage() + 
                        " or " 
                        + exNumber.getMessage());
            }
        }
    }
}
