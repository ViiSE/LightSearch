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

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lightsearch.admin.panel.exception.ValidatorException;

/**
 *
 * @author ViiSE
 */
public class IPValidatorDefaultImpl implements IPValidator {

    private final String ipv4Pattern = ValidatorEnum.IPV4_PATTERN.toString();
    Pattern pattern;
    
    public IPValidatorDefaultImpl() {
        pattern = Pattern.compile(ipv4Pattern);
    }
    
    @Override
    public void validate(String ip) throws ValidatorException {
        Matcher matcher = pattern.matcher(ip);
        if(!matcher.matches())
            throw new ValidatorException("IP is not valid!");
    }
    
}