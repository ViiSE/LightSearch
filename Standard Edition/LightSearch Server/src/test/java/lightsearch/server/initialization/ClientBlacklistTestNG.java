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
package lightsearch.server.initialization;

import java.util.List;

import lightsearch.server.initialization.ClientBlacklist;
import lightsearch.server.initialization.ClientBlacklistInit;

import lightsearch.server.initialization.CurrentServerDirectory;
import lightsearch.server.initialization.CurrentServerDirectoryInit;
import lightsearch.server.initialization.OsDetectorInit;

import static org.testng.Assert.*;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class ClientBlacklistTestNG {

    @Test(groups = {"Initialization", "Server"})
    public void blacklist() {
        testBegin("ClientBlacklist", "blacklist()");
        
        CurrentServerDirectory currentServerDirectory = CurrentServerDirectoryInit.currentDirectory(
                OsDetectorInit.osDetector());
        ClientBlacklist clientBlacklist = ClientBlacklistInit.clientBlacklist(currentServerDirectory);
        List<String> blacklist = clientBlacklist.blacklist();
        
        assertNotNull(blacklist, "Blacklist is null!");
        
        blacklist.forEach((client) -> {
            assertNotEquals(client, "", "Client is null!");
            assertNotNull(client, "Client is null!");
        });
        
        for(int i = 0; i < blacklist.size(); i++) {
            String clientT = blacklist.get(i);
            for(int j = 0; j < blacklist.size(); j++)
                if(i != j)
                    assertNotEquals(blacklist.get(j), clientT, "The same client in blacklist several times!");
        }
        
        testEnd("ClientBlacklist", "blacklist()");
    }
}
