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

import java.util.regex.Pattern;
import lightsearch.server.data.LightSearchServerDatabaseDTO;
import lightsearch.server.data.LightSearchServerDatabaseDTOInit;
import static org.testng.Assert.*;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class LightSearchServerDatabaseDTOTestNG {
    
    private static final Pattern PATTERN = Pattern.compile(
        "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");    
        
    private static boolean validate(final String ip) {
        return PATTERN.matcher(ip).matches();
    }
    
    @Test(groups={"Initialization", "Server"})
    public void dbName() {
        testBegin("LightSearchServerDatabaseDTO", "dbName()");
        
        String ip = "192.168.3.129";
        String name = "db";
        int port = 3004;
        
        LightSearchServerDatabaseDTO serverDbDTO = LightSearchServerDatabaseDTOInit.lightSearchServerDatabaseDTO(
                ip, 
                name, 
                port);
        
        assertNotNull(serverDbDTO, "Server DB DTO is null!");
        assertNotNull(serverDbDTO.dbName(), "DB name is null!");
        assertFalse(serverDbDTO.dbName().equals(""), "DB name is null!");
        
        testEnd("LightSearchServerDatabaseDTO", "dbName()");
    }
    
    @Test(groups={"Initialization", "Server"})
    public void dbIP() {
        testBegin("LightSearchServerDatabaseDTO", "dbIP()");
        
        String ip = "192.168.3.129";
        String name = "db";
        int port = 3004;
        
        LightSearchServerDatabaseDTO serverDbDTO = LightSearchServerDatabaseDTOInit.lightSearchServerDatabaseDTO(
                ip, 
                name, 
                port);
        
        assertNotNull(serverDbDTO, "Server DB DTO is null!");
        assertNotNull(serverDbDTO.dbIP(), "DB ip is null!");
        assertFalse(serverDbDTO.dbIP().equals(""), "DB ip is null!");
        assertTrue(validate(serverDbDTO.dbIP()), "Wrong DB ip!");
        
        testEnd("LightSearchServerDatabaseDTO", "dbIP()");
    }
    
    @Test(groups={"Initialization", "Server"})
    public void dbPort() {
        testBegin("LightSearchServerDatabaseDTO", "dbPort()");
        
        String ip = "192.168.3.129";
        String name = "db";
        int port = 3030;
        
        LightSearchServerDatabaseDTO serverDbDTO = LightSearchServerDatabaseDTOInit.lightSearchServerDatabaseDTO(
                ip, 
                name, 
                port);
        
        assertNotNull(serverDbDTO, "Server DB DTO is null!");
        assertFalse(serverDbDTO.dbPort() < 1023, "Wrong DB port!");
        assertFalse(serverDbDTO.dbPort() > 65535, "Wrong DB port!");
        
        testEnd("LightSearchServerDatabaseDTO", "dbPort()");
    }
}
