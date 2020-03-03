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
package lightsearch.server.database;

import lightsearch.server.exception.DatabaseReaderException;

/**
 * Читает данные из таблицы базы данных.
 * <p>
 * Считывает результат команды из таблицы базы данных.
 * <p>
 * В случае ошибки считывания генерируется исключение {@link lightsearch.server.exception.DatabaseReaderException}.
 * @author ViiSE
 * @see lightsearch.server.database.statement.DatabaseStatementExecutor
 * @since 2.0.0
 */
public interface DatabaseReader {
    String read() throws DatabaseReaderException;
}
