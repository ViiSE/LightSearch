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

/**
 *
 * @author ViiSE
 */
public class CommandValidatorDefaultImpl implements CommandValidator {

    private final int commandMaxCount = (int)ValidatorEnum.COMMAND_VALUE.maxValue();
    private final int commandMinCount = (int)ValidatorEnum.COMMAND_VALUE.minValue();
    
    @Override
    public void validate(String command) throws ValidatorException {
        try {
            int cmd = Integer.parseInt(command);
            if(cmd < commandMinCount)
                throw new ValidatorException("Command number must be more or equals than " + commandMinCount + "!");
            if(cmd > commandMaxCount)
                throw new ValidatorException("Command number must be less or equals " + commandMaxCount + "!");
        }
        catch (NumberFormatException ignore) {
            throw new ValidatorException("Input value is not a number!");
        }
    }
    
}
