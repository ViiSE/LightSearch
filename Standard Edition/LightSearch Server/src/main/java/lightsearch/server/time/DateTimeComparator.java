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

/**
 * Сравнивает две даты.
 * <p>
 * Этот интерфейс применяется в таймерах и в работе с базой данных.
 * @author ViiSE
 * @see lightsearch.server.timer.SuperIteratorDatabaseRecordWriterTimer
 * @see lightsearch.server.database.statement.result.DatabaseStatementResultSetSelect
 * @since 1.0
 */
public interface DateTimeComparator {
    boolean isAfter(Object originalDate, LocalDateTime afterDate);
    boolean isBefore(Object originalDate, LocalDateTime beforeDate);
}
