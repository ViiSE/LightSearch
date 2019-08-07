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

import lightsearch.admin.panel.data.ConnectionDTO;
import lightsearch.admin.panel.data.ConnectionDTOInit;
import lightsearch.admin.panel.data.ScannerConnectionDTO;
import lightsearch.admin.panel.exception.ScannerException;
import lightsearch.admin.panel.scanner.ScannerConnection;
import lightsearch.admin.panel.scanner.ScannerConnectionInit;
import lightsearch.admin.panel.print.AdminPanelPrinter;

/**
 *
 * @author ViiSE
 */
public class ConnectionDTOCreatorDefaultImpl implements ConnectionDTOCreator {
    
    private final AdminPanelPrinter printer;
    private final ScannerConnectionDTO scannerDTO;

    public ConnectionDTOCreatorDefaultImpl(AdminPanelPrinter printer, ScannerConnectionDTO scannerDTO) {
        this.printer = printer;
        this.scannerDTO = scannerDTO;
    }
    
    @Override
    public ConnectionDTO createConnectionDTO() {
        ScannerConnection scannerConnection = ScannerConnectionInit.scannerConnection(scannerDTO);
        String ip;
        int port;
        while(true) {
            try {
                printer.print("Input server ip: ");
                ip = scannerConnection.scanIP();
                printer.print("Input server port: ");
                port = scannerConnection.scanPort();
                ConnectionDTO connectionDTO = ConnectionDTOInit.connectionDTO(ip, port);
                return connectionDTO;
            }
            catch(ScannerException ex) {
                printer.println(ex.getMessage());
            }
        }
    }
}