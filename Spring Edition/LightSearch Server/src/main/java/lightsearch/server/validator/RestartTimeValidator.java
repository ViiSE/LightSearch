/*
 *  Copyright 2019 ViiSE.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package lightsearch.server.validator;

import lightsearch.server.exception.ValidatorException;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Component("restartTimeValidator")
public class RestartTimeValidator implements Validator<String> {

    @Override
    public void validate(String restartTime) throws ValidatorException {
        try {
            LocalTime.parse(restartTime, DateTimeFormatter.ofPattern("HH:mm"));
        } catch (DateTimeParseException ex) {
            throw new ValidatorException("Wrong time format! Exception: " + ex.getMessage(),
                    "Wrong time format. Exception: " + ex.getMessage());
        }
    }
}
