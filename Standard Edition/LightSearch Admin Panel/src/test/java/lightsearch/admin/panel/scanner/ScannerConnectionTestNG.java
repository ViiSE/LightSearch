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

import java.util.Scanner;
import lightsearch.admin.panel.data.ScannerConnectionDTO;
import lightsearch.admin.panel.data.ScannerConnectionDTOInit;
import lightsearch.admin.panel.exception.ScannerException;
import lightsearch.admin.panel.scanner.ScannerConnection;
import lightsearch.admin.panel.scanner.ScannerConnectionInit;
import lightsearch.admin.panel.validate.IPValidator;
import lightsearch.admin.panel.validate.IPValidatorInit;
import lightsearch.admin.panel.validate.PortValidator;
import lightsearch.admin.panel.validate.PortValidatorInit;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;
/**
 *
 * @author ViiSE
 */
public class ScannerConnectionTestNG {
    
    private ScannerConnectionDTO scannerDTO;
    
    @BeforeTest
    public void initScannerConnectionDTO() {
        IPValidator ipValidator = IPValidatorInit.ipValidator();
        PortValidator portValidator = PortValidatorInit.portValidator();
        Scanner scanner = new Scanner(System.in);
        scannerDTO = ScannerConnectionDTOInit.scannerConnectionDTO(scanner, ipValidator, portValidator);
    }
    
    @Test
    public void scanIP() {
        testBegin("ScannerConnection", "scanIP()");
        
        assertNotNull(scannerDTO, "Scanner DTO is null!");
        
        ScannerConnection scannerConnection = ScannerConnectionInit.scannerConnection(scannerDTO);
        
        String ip;
        while(true) {
            try {
                System.out.print("Input ip:");
                ip = scannerConnection.scanIP();

                break;
            } 
            catch (ScannerException ex) {
                System.out.println(ex.getMessage());
            }
        }
        System.out.println("ip:" + ip);
        
        testEnd("ScannerConnection", "scanIP()");
    }
    
    @Test
    public void scanPort() {
        testBegin("ScannerConnection", "scanPort()");
        
        assertNotNull(scannerDTO, "Scanner DTO is null!");
        
        ScannerConnection scannerConnection = ScannerConnectionInit.scannerConnection(scannerDTO);
        
        int port;
        while(true) {
            try {
                System.out.print("Input port:");
                port = scannerConnection.scanPort();

                break;
            } 
            catch (ScannerException ex) {
                System.out.println(ex.getMessage());
            }
        }
        System.out.println("port:" + port);
        
        testEnd("ScannerConnection", "scanPort()");
    }
}
