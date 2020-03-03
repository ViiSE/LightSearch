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
package lightsearch.server.data;

import lightsearch.server.initialization.*;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.Map;

import static org.testng.Assert.*;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class AdministratorsMapTestNG {
    
    @Test
    @Parameters({"rootAdmin"})
    public void administratorsMap(String rootAdmin) {
        testBegin("AdministratorMap", "administratorsMap()");
        
        CurrentServerDirectory currentServerDirectory = CurrentServerDirectoryInit.currentDirectory(
                OsDetectorInit.osDetector());
        AdministratorsMap administratorsMap = AdministratorsMapInit.administratorsMap(currentServerDirectory);
        Map<String, String> adminMap = administratorsMap.administratorsMap();
        
        assertFalse(adminMap.isEmpty(), "AdminMap is null!");
        assertTrue(adminMap.containsKey(rootAdmin), "AdminMap is not contains \"" + rootAdmin + "\"!");
        
        adminMap.values().forEach((pass) -> assertNotEquals(pass, "", "Password is null!"));
        
        adminMap.keySet().forEach((admin) -> assertNotEquals(admin, "", "Admin is null!"));

        System.out.println("Administrator map: " + adminMap);
        
        testEnd("AdministratorMap", "administratorsMap()");
    }
}
