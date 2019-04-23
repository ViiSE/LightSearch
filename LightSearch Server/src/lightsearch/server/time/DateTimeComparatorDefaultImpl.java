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
package lightsearch.server.time;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author ViiSE
 */
public class DateTimeComparatorDefaultImpl implements DateTimeComparator {

    private final String pattern;
    
    public DateTimeComparatorDefaultImpl(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public boolean isAfter(Object originalDate, LocalDateTime afterDate) {
        if(originalDate instanceof LocalDateTime) {
            LocalDateTime origDate = (LocalDateTime) originalDate;
            return origDate.isAfter(afterDate);
        }
        else if(originalDate instanceof String) {
            LocalDateTime origDate = LocalDateTime.parse((String)originalDate, DateTimeFormatter.ofPattern(pattern));
            return origDate.isAfter(afterDate);
        }
        else 
            return false;
    }

    @Override
    public boolean isBefore(Object originalDate, LocalDateTime beforeDate) {
        if(originalDate instanceof LocalDateTime) {
            LocalDateTime origDate = (LocalDateTime) originalDate;
            return origDate.isBefore(beforeDate);
        }
        else if(originalDate instanceof String) {
            LocalDateTime origDate = LocalDateTime.parse((String)originalDate, DateTimeFormatter.ofPattern(pattern));
            return origDate.isBefore(beforeDate);
        }
        else 
            return false;
    }
    
}
