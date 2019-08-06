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
package test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Map;

import lightsearch.server.initialization.CurrentServerDirectory;
import lightsearch.server.initialization.CurrentServerDirectoryInit;
import lightsearch.server.initialization.AdministratorsMap;
import lightsearch.server.initialization.AdministratorsMapInit;
import lightsearch.server.initialization.OsDetectorInit;

import static org.testng.Assert.*;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class AdministratorsMapTestNG {
    
    @Test(groups = {"Initialization", "Server"})
    public void administratorsMap() {
        testBegin("AdministratorMap", "administratorsMap()");
        
        CurrentServerDirectory currentServerDirectory = CurrentServerDirectoryInit.currentDirectory(
                OsDetectorInit.osDetector());
        AdministratorsMap administratorsMap = AdministratorsMapInit.administratorsMap(currentServerDirectory);
        Map<String, String> adminMap = administratorsMap.administratorsMap();
        
        assertFalse(adminMap.isEmpty(), "AdminMap is null!");
        assertTrue(adminMap.containsKey("admin"), "AdminMap is not contains \"admin\"!");
        
        adminMap.values().forEach((pass) -> {
            assertFalse(pass.equals(""), "Password is null!");
        });
        
        adminMap.keySet().forEach((admin) -> {
            assertFalse(admin.equals(""), "Admin is null!");
        });
        
        testEnd("AdministratorMap", "administratorsMap()");
    }
}
