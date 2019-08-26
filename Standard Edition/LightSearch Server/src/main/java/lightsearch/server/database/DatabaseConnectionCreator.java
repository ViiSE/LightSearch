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

import lightsearch.server.exception.DatabaseConnectionCreatorException;

/**
 * Создает соединение с базой данных.
 * <p>
 * Создает соединение для конкретной СУБД.
 * <p>
 * Для создания соединений с другими СУБД необходимо добавить новые методы для соответствующих СУБД в этот интерфейс.
 * <p>
 * В случае ошибки создания соединения генерируется исключение
 * {@link lightsearch.server.exception.DatabaseConnectionCreatorException}.
 * @author ViiSE
 * @since 1.0.0
 */
public interface DatabaseConnectionCreator {
    DatabaseConnection createFirebirdConnection() throws DatabaseConnectionCreatorException;
}
