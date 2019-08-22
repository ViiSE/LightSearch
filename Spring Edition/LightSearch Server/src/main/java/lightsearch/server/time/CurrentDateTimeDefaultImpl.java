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

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

/**
 *
 * @author ViiSE
 */
@Component("currentDateTimeDefault")
public class CurrentDateTimeDefaultImpl implements CurrentDateTime {

    @Override
    public String dateTimeInStandardFormat() {
        SimpleDateFormat nice = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        GregorianCalendar cal = new GregorianCalendar();
        java.util.Date date = cal.getTime();

        String dateStr = nice.format(date);
        return dateStr;
    }

    @Override
    public String dateTimeWithDot() {
        SimpleDateFormat nice = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        GregorianCalendar cal = new GregorianCalendar();
        java.util.Date date = cal.getTime();

        String dateStr = nice.format(date);
        return dateStr;
    }

    @Override
    public String dateLog() {
        SimpleDateFormat nice = new SimpleDateFormat("dd-MM-yyyy");
        GregorianCalendar cal = new GregorianCalendar();
        java.util.Date date = cal.getTime();

        String dateStr = nice.format(date);
        return dateStr;
    }
    
}
