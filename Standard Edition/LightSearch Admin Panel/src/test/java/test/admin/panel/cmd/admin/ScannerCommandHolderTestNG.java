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
package test.admin.panel.cmd.admin;

import lightsearch.admin.panel.cmd.admin.ScannerCommandHolder;
import lightsearch.admin.panel.cmd.admin.ScannerCommandHolderCreator;
import lightsearch.admin.panel.cmd.admin.ScannerCommandHolderCreatorInit;
import lightsearch.admin.panel.scanner.ScannerClientValue;
import lightsearch.admin.panel.scanner.ScannerDatabase;
import lightsearch.admin.panel.scanner.ScannerRegistration;
import lightsearch.admin.panel.scanner.ScannerRestart;
import lightsearch.admin.panel.scanner.ScannerTimeout;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class ScannerCommandHolderTestNG {
    
    private ScannerCommandHolderCreator scannerCommandHolderCreator;
    
    @BeforeTest
    public void setUpMethod() {
        scannerCommandHolderCreator =
                ScannerCommandHolderCreatorInit.scannerCommandHolderCreator();
        assertNotNull(scannerCommandHolderCreator, 
                "ScannerCommandHolderCreator is null!");
    }
    
    /*
        scCmdHolder.scannerTimeout();
    */
    
    @Test
    public void scannerClientValue() {
        testBegin("ScannerCommandHolder", "scannerClientValue()");
        
        ScannerCommandHolder scCmdHolder = 
                scannerCommandHolderCreator.createScannerCommandHolder();
        assertNotNull(scCmdHolder, "ScannerCommandHolder is null!");
        
        ScannerClientValue scClVal = scCmdHolder.scannerClientValue();
        assertNotNull(scClVal, "ScannerClientValue is null!");
        
        System.out.println("ScannerCommandHolder: scannerClientValue(): " + scClVal);
        
        testEnd("ScannerCommandHolder", "scannerClientValue()");
    }
    
    @Test
    public void scannerDatabase() {
        testBegin("ScannerCommandHolder", "scannerDatabase()");
        
        ScannerCommandHolder scCmdHolder = 
                scannerCommandHolderCreator.createScannerCommandHolder();
        assertNotNull(scCmdHolder, "ScannerCommandHolder is null!");
        
        ScannerDatabase scDb = scCmdHolder.scannerDatabase();
        assertNotNull(scDb, "ScannerDatabase is null!");
        
        System.out.println("ScannerCommandHolder: scannerDatabase(): " + scDb);
        
        testEnd("ScannerCommandHolder", "scannerDatabase()");
    }
    
    @Test
    public void scannerRegistration() {
        testBegin("ScannerCommandHolder", "scannerRegistration()");
        
        ScannerCommandHolder scCmdHolder = 
                scannerCommandHolderCreator.createScannerCommandHolder();
        assertNotNull(scCmdHolder, "ScannerCommandHolder is null!");
        
        ScannerRegistration scReg = scCmdHolder.scannerRegistration();
        assertNotNull(scReg, "ScannerRegistration is null!");
        
        System.out.println("ScannerCommandHolder: scannerRegistration(): " + scReg);
        
        testEnd("ScannerCommandHolder", "scannerRegistration()");
    }
    
    @Test
    public void scannerRestart() {
        testBegin("ScannerCommandHolder", "scannerRestart()");
        
        ScannerCommandHolder scCmdHolder = 
                scannerCommandHolderCreator.createScannerCommandHolder();
        assertNotNull(scCmdHolder, "ScannerCommandHolder is null!");
        
        ScannerRestart scRes = scCmdHolder.scannerRestart();
        assertNotNull(scRes, "ScannerRestart is null!");
        
        System.out.println("ScannerCommandHolder: scannerRestart(): " + scRes);
        
        testEnd("ScannerCommandHolder", "scannerRestart()");
    }
    
    @Test
    public void scannerTimeout() {
        testBegin("ScannerCommandHolder", "scannerTimeout()");
        
        ScannerCommandHolder scCmdHolder = 
                scannerCommandHolderCreator.createScannerCommandHolder();
        assertNotNull(scCmdHolder, "ScannerCommandHolder is null!");
        
        ScannerTimeout scTout = scCmdHolder.scannerTimeout();
        assertNotNull(scTout, "ScannerTimeout is null!");
        
        System.out.println("ScannerCommandHolder: scannerTimeout(): " + scTout);
        
        testEnd("ScannerCommandHolder", "scannerTimeout()");
    }
}
