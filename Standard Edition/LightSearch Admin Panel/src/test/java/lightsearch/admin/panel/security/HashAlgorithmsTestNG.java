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
package lightsearch.admin.panel.security;

import lightsearch.admin.panel.security.HashAlgorithms;
import lightsearch.admin.panel.security.HashAlgorithmsInit;
import static org.testng.Assert.*;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class HashAlgorithmsTestNG {
    
    @Test
    @Parameters({"message"})
    public void sha256(String message) {
        testBegin("HashAlgorithms", "sha256()");

        assertNotNull(message, "Message is null!");
        
        System.out.println("Source message: " + message);
        HashAlgorithms hashAlgorithms = HashAlgorithmsInit.hashAlgorithms();
        String hashMessage = hashAlgorithms.sha256(message);
        System.out.println("Hash message: " + hashMessage);
    
        testEnd("HashAlgorithms", "sha256()");
    }
}
