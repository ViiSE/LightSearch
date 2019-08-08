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
import lightsearch.admin.panel.validate.TimeoutValidator;
import lightsearch.admin.panel.validate.TimeoutValidatorInit;
import static org.testng.Assert.*;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class TimeoutValidatorTestNG {
    
    @Test
    public void validate() {
        testBegin("TimeoutValidator", "validate()");
        
        try {    
            String toutValue = "10000";
            assertNotNull(toutValue, "Timeout value is null!");

            TimeoutValidator dbNameValidator = TimeoutValidatorInit.timeoutValidator();
            dbNameValidator.validate(toutValue);
            
            System.out.println("Validation success");
        }
        catch(ValidatorException ex) {
            System.out.println("CATCH! Message: " + ex.getMessage());
        }
        
        testEnd("TimeoutValidator", "validate()");
    }
}
