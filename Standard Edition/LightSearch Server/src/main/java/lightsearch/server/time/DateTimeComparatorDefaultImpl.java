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
 * Реализация интерфейса {@link lightsearch.server.time.DateTimeComparator} по умолчанию.
 * <p>
 * Сравниваемая дата может передаваться либо как экземпляр класса {@link java.time.LocalDateTime}, либо как
 * {@link java.lang.String}, иначе оба метода вернут {@code false}.
 * <p>
 * Если сравниваемая дата является экземпляром класса {@link java.time.LocalDateTime}, то эти две даты сравниваются
 * стандартным способом.
 * <p>
 * Если сравниваемая дата является экземпляром класса {@link java.lang.String}, то эти две даты сравниваются через
 * шаблон даты и времени, передаваемый в качестве параметра конструктора этого класса. Если нет необходимости сравнивать
 * дату в виде {@link java.lang.String}, то в качестве параметра в конструктор можно передать {@code null}.
 * @author ViiSE
 * @see lightsearch.server.time.CurrentDateTimePattern
 * @since 1.0
 */
public class DateTimeComparatorDefaultImpl implements DateTimeComparator {

    private final String pattern;
    
    public DateTimeComparatorDefaultImpl(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public boolean isAfter(Object originalDate, LocalDateTime afterDate) {
        if(originalDate instanceof LocalDateTime) {
            return ((LocalDateTime) originalDate).isAfter(afterDate);
        }
        else if(originalDate instanceof String) {
            return LocalDateTime.parse((String)originalDate, DateTimeFormatter.ofPattern(pattern)).isAfter(afterDate);
        }
        else 
            return false;
    }

    @Override
    public boolean isBefore(Object originalDate, LocalDateTime beforeDate) {
        if(originalDate instanceof LocalDateTime) {
            //LocalDateTime origDate = (LocalDateTime) originalDate;
            return ((LocalDateTime) originalDate).isBefore(beforeDate);
        }
        else if(originalDate instanceof String) {
            //LocalDateTime origDate = LocalDateTime.parse((String)originalDate, DateTimeFormatter.ofPattern(pattern));
            return LocalDateTime.parse((String)originalDate, DateTimeFormatter.ofPattern(pattern)).isBefore(beforeDate);
        }
        else 
            return false;
    }
    
}
