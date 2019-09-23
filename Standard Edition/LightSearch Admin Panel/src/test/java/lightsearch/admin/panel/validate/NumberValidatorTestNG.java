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
import lightsearch.admin.panel.validate.NumberValidator;
import lightsearch.admin.panel.validate.NumberValidatorInit;
import static org.testng.Assert.*;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class NumberValidatorTestNG {
    
    @Test
    public void validate() {
        testBegin("NumberValidator", "validate()");
        
        try {    
            String number = "11";
            assertNotNull(number, "Number is null!");

            NumberValidator numberValidator = NumberValidatorInit.numberValidator();
            numberValidator.validate(number);
            
            System.out.println("Validation success");
        }
        catch(ValidatorException ex) {
            System.out.println("CATCH! Message: " + ex.getMessage());
        }
        
        testEnd("NumberValidator", "validate()");
    }
}
