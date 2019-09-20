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

import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.testng.Assert.assertNotNull;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class DateTimeComparatorTestNG {
    
    @Test
    public void isAfter() {
        testBegin("DateTimeComparator", "isAfter()");

        CurrentDateTime currentDateTime = CurrentDateTimeInit.currentDateTime();
        String pattern = "yyyy-MM-dd HH:mm:ss";
        assertNotNull(pattern, "Pattern string is null!");
        DateTimeComparator dateTimeComparator = DateTimeComparatorInit.dateTimeComparator(pattern);
        
        LocalDateTime dateTime30Seconds = LocalDateTime.now().plusSeconds(30);
        System.out.println("--------------------1--------------------");
        System.out.println("isAfter(" + LocalDateTime.now().toString() + "," + dateTime30Seconds.toString() + ")");
        System.out.println("result: " + dateTimeComparator.isAfter(LocalDateTime.now(), dateTime30Seconds));
        System.out.println("--------------------1--------------------");
        System.out.println();
        
        System.out.println("--------------------2--------------------");
        System.out.println("isAfter(" + currentDateTime.dateTimeInStandardFormat() + "," + LocalDateTime.now().toString() + ")");
        System.out.println("result: " + dateTimeComparator.isAfter(currentDateTime.dateTimeInStandardFormat(), LocalDateTime.now()));
        System.out.println("--------------------2--------------------");
        System.out.println();
        
        System.out.println("LocalDateTime.now().with(LocalTime.MAX): " + LocalDateTime.now().with(LocalTime.MAX).toString());
        System.out.println("LocalDateTime.now().with(LocalTime.MIN): " + LocalDateTime.now().with(LocalTime.MIN).toString());
        
        System.out.println("----------------SUPER TEST(For DatabaseStatementResultSetSelect)------------------");
        System.out.println(dateTimeComparator.isBefore("2019-01-16 23:56:56", LocalDateTime.now().with(LocalTime.MAX))
            && dateTimeComparator.isAfter("2019-01-16 23:56:56", LocalDateTime.now().with(LocalTime.MIN)));
        System.out.println("----------------SUPER TEST(DatabaseStatementResultSetSelect------------------");
        System.out.println(); 
        
        testEnd("DateTimeComparator", "isAfter()");
    }
    
    @Test
    public void isBefore() {
        testBegin("DateTimeComparator", "isBefore()");
        
        CurrentDateTime currentDateTime = CurrentDateTimeInit.currentDateTime();
        String pattern = "yyyy-MM-dd HH:mm:ss";
        //2019-01-17 17:12:21
        assertNotNull(pattern, "Pattern string is null!");
        DateTimeComparator dateTimeComparator = DateTimeComparatorInit.dateTimeComparator(pattern);
        
        LocalDateTime dateTime30Seconds = LocalDateTime.now().plusSeconds(30);
        System.out.println("--------------------1--------------------");
        System.out.println("isBefore(" + LocalDateTime.now().toString() + "," + dateTime30Seconds.toString() + ")");
        System.out.println("result: " + dateTimeComparator.isBefore(LocalDateTime.now(), dateTime30Seconds));
        System.out.println("--------------------1--------------------");
        System.out.println();
        
        System.out.println("--------------------2--------------------");
        System.out.println("isBefore(" + currentDateTime.dateTimeInStandardFormat() + "," + LocalDateTime.now().toString() + ")");
        System.out.println("result: " + dateTimeComparator.isBefore(currentDateTime.dateTimeInStandardFormat(), LocalDateTime.now()));
        System.out.println("--------------------2--------------------");
        System.out.println();
        
        testEnd("DateTimeComparator", "isBefore()");
    }
}
