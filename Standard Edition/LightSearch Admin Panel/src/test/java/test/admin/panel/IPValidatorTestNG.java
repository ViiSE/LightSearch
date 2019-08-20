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
package test.admin.panel;

import lightsearch.admin.panel.exception.ValidatorException;
import lightsearch.admin.panel.validate.IPValidator;
import lightsearch.admin.panel.validate.IPValidatorInit;
import static org.testng.Assert.*;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class IPValidatorTestNG {
    
    @Test
    public void validate() {
        testBegin("IPValidator", "validate()");
        
        String ip = "127.0.0.1";
        assertNotNull(ip, "IP is null!");
        assertFalse(ip.isEmpty(), "IP is null!");
        IPValidator ipValidator = IPValidatorInit.ipValidator();
        try {
            ipValidator.validate(ip);
            System.out.println("Validation success");
        }
        catch(ValidatorException ex) {
            System.out.println("CATCH! Exception: " + ex.getMessage());
        }
        
        testEnd("IPValidator", "validate()");
    }
}
