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
public class IMEIValidatorDefaultImpl implements IMEIValidator {

    private final int IMEIMinLength = (int)ValidatorEnum.IMEI_LENGTH.minValue();
    private final int IMEIMaxLength = (int)ValidatorEnum.IMEI_LENGTH.maxValue();
    
    @Override
    public void validate(String IMEI) throws ValidatorException {
        if(!IMEI.matches("[0-9]+"))
            throw new ValidatorException("IMEI contains non-numeric characters!");
        if(IMEI.length() < IMEIMinLength)
            throw new ValidatorException("IMEI length value is less than " + IMEIMinLength + "!");
        if(IMEI.length() > IMEIMaxLength)
            throw new ValidatorException("IMEI length value is more than " + IMEIMaxLength + "!");
    }
}
