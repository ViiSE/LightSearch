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
package lightsearch.admin.panel.validate;

import lightsearch.admin.panel.exception.ValidatorException;
import lightsearch.admin.panel.validate.IPValidator;
import lightsearch.admin.panel.validate.IPValidatorInit;
import static org.testng.Assert.*;
import static test.message.TestMessage.*;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 *
 * @author ViiSE
 */
public class IPValidatorTestNG {
    
    @Test
    @Parameters({"ip"})
    public void validate(String ip) {
        testBegin("IPValidator", "validate()");

        try {
            assertNotNull(ip, "IP is null!");
            assertFalse(ip.isEmpty(), "IP is null!");
            IPValidator ipValidator = IPValidatorInit.ipValidator();
            ipValidator.validate(ip);
            System.out.println("Validation success");
        } catch(ValidatorException ex) {
            catchMessage(ex);
        }
        
        testEnd("IPValidator", "validate()");
    }
}
