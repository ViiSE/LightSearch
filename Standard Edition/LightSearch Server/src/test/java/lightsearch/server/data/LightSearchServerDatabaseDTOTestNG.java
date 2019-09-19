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

import org.testng.annotations.Test;
import test.data.DataProviderCreator;

import java.util.regex.Pattern;

import static org.testng.Assert.*;
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
    
    @Test
    public void dbName() {
        testBegin("LightSearchServerDatabaseDTO", "dbName()");
        
        LightSearchServerDatabaseDTO serverDbDTO = DataProviderCreator.createDataProvider(LightSearchServerDatabaseDTO.class);
        
        assertNotNull(serverDbDTO, "Server DB DTO is null!");
        assertNotNull(serverDbDTO.dbName(), "DB name is null!");
        assertNotEquals(serverDbDTO.dbName(), "", "DB name is null!");
        
        testEnd("LightSearchServerDatabaseDTO", "dbName()");
    }
    
    @Test
    public void dbIP() {
        testBegin("LightSearchServerDatabaseDTO", "dbIP()");

        LightSearchServerDatabaseDTO serverDbDTO = DataProviderCreator.createDataProvider(LightSearchServerDatabaseDTO.class);

        assertNotNull(serverDbDTO, "Server DB DTO is null!");
        assertNotNull(serverDbDTO.dbIP(), "DB ip is null!");
        assertNotEquals(serverDbDTO.dbIP(), "", "DB ip is null!");
        assertTrue(validate(serverDbDTO.dbIP()), "Wrong DB ip!");
        
        testEnd("LightSearchServerDatabaseDTO", "dbIP()");
    }
    
    @Test
    public void dbPort() {
        testBegin("LightSearchServerDatabaseDTO", "dbPort()");

        LightSearchServerDatabaseDTO serverDbDTO = DataProviderCreator.createDataProvider(LightSearchServerDatabaseDTO.class);

        assertNotNull(serverDbDTO, "Server DB DTO is null!");
        assertFalse(serverDbDTO.dbPort() < 1023, "Wrong DB port!");
        assertFalse(serverDbDTO.dbPort() > 65535, "Wrong DB port!");
        
        testEnd("LightSearchServerDatabaseDTO", "dbPort()");
    }
}
