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

import lightsearch.admin.panel.data.ScannerConnectionDTO;
import lightsearch.admin.panel.exception.ScannerException;
import lightsearch.admin.panel.exception.ValidatorException;

/**
 *
 * @author ViiSE
 */
public class ScannerConnectionDefaultImpl implements ScannerConnection {

    private final ScannerConnectionDTO scannerDTO;
    
    public ScannerConnectionDefaultImpl(ScannerConnectionDTO scannerDTO) {
        this.scannerDTO = scannerDTO;
    }
    
    @Override
    public String scanIP() throws ScannerException {
        try {
            String ip = scannerDTO.scanner().nextLine();
            scannerDTO.ipValidator().validate(ip);
            return ip;
        }
        catch(ValidatorException ex) {
            throw new ScannerException(ex.getMessage());
        }
    }

    @Override
    public int scanPort() throws ScannerException {
        try {
            int port = Integer.parseInt(scannerDTO.scanner().nextLine());
            scannerDTO.portValidator().validate(port);
            return port;
        }
        catch(ValidatorException ex) {
            throw new ScannerException(ex.getMessage());
        }
        catch (NumberFormatException ignore) {
            throw new ScannerException("Input value is not a number!");
        }
    }
    
}
