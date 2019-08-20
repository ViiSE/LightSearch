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
import lightsearch.admin.panel.validate.CommandValidator;
import lightsearch.admin.panel.validate.CommandValidatorInit;
import static org.testng.Assert.*;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class CommandValidatorTestNG {
    
     @Test
     public void validate() {
         testBegin("CommandValidator", "validate()");
         
         try {
             String commandNumber = "10";
             assertNotNull(commandNumber, "Command number is null!");
             assertFalse(commandNumber.isEmpty(), "Command number is null!");
             CommandValidator cmdValidator = CommandValidatorInit.commandValidator();
             cmdValidator.validate(commandNumber);
             System.out.println("Command number " + commandNumber + " is validate.");
         } catch (ValidatorException ex) {
             System.out.println("CATCH! Exception: " + ex.getMessage());
         }
         
         testEnd("CommandValidator", "validate()");
     }
}
