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

import java.sql.Connection;

/**
 * Соединение с базой данных.
 * <p>
 * Для работы LightSearch Server в базе данных предприятия необходимо завести две таблицы: LS_REQUEST и LS_RESPONSE.
 * В LS_REQUEST LightSearch Server записывает команды клиента, в LS_RESPONSE программа предприятия, реализующая
 * бизнес-логику, записывает результаты команд. Более подробное описание и структуры таблиц определяется в
 * <a href="https://github.com/ViiSE/LightSearch/blob/master/Documents/Protocol/LightSearch%20Protocol.txt">протоколе</a>.
 * <p>
 * Используется при отправке команд и считывании их результатов.
 * @author ViiSE
 * @see lightsearch.server.data.ClientDAO
 * @see lightsearch.server.database.DatabaseReader
 * @see lightsearch.server.database.DatabaseWriter
 * @see lightsearch.server.database.statement.DatabasePreparedStatement
 * @see lightsearch.server.database.statement.DatabaseStatementExecutor
 * @since 1.0
 */
public interface DatabaseConnection {
    Connection connection();
}
