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
package lightsearch.server.database;

import lightsearch.server.initialization.*;
import org.testng.annotations.Test;

import java.util.regex.Pattern;

import static org.testng.Assert.*;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class DatabaseSettingsTestNG {
    
    private static final Pattern PATTERN = Pattern.compile(
        "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
    
    private static boolean validate(final String ip) {
        return PATTERN.matcher(ip).matches();
    }
    
    @Test
    public void name() {
        testBegin("DatabaseSettings", "name()");
        
        CurrentServerDirectory currentServerDirectory =
                CurrentServerDirectoryInit.currentDirectory(OsDetectorInit.osDetector());
        
        DatabaseSettings dbSettings = DatabaseSettingsInit.databaseSettings(currentServerDirectory);
        String dbName = dbSettings.name();
        
        assertNotNull(dbName, "DB name is null!");
        assertNotEquals(dbName, "", "DB name is null!");
        
        testEnd("DatabaseSettings", "name()");
    }
    
    @Test
    public void ip() {
        testBegin("DatabaseSettings", "ip()");
        
        CurrentServerDirectory currentServerDirectory =
                CurrentServerDirectoryInit.currentDirectory(OsDetectorInit.osDetector());
        
        DatabaseSettings dbSettings = DatabaseSettingsInit.databaseSettings(currentServerDirectory);
        String dbIP = dbSettings.ip();
        
        assertNotNull(dbIP, "DB ip is null!");
        assertNotEquals(dbIP, "", "DB ip is null!");
        assertTrue(validate(dbIP), "Wrong DB ip!");
        
        testEnd("DatabaseSettings", "ip()");
    }
    
    @Test
    public void port() {
        testBegin("DatabaseSettings", "port()");
        
        CurrentServerDirectory currentServerDirectory =
                CurrentServerDirectoryInit.currentDirectory(OsDetectorInit.osDetector());
        
        DatabaseSettings dbSettings = DatabaseSettingsInit.databaseSettings(currentServerDirectory);
        int dbPort = dbSettings.port();
        
        assertFalse(dbPort < 1023, "Wrong DB port!");
        assertFalse(dbPort > 65535, "Wrong DB port!");
        
        testEnd("DatabaseSettings", "port()");
    }
}
