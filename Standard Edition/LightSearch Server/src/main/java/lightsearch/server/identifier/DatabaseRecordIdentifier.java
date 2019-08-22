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
package lightsearch.server.identifier;

/**
 * Идентификатор записи базы данных, значение которого записывается в таблицу {@code LS_REQUEST}.
 * <p>
 * Идентификатор записи базы данных - уникальный идентификатор, который записывается в поле LSCODE таблицы LS_REQUEST.
 * Это же значение записывается в поле LSCODE таблицы LS_RESPONSE, со стороны программы, реализующей бизнес-логику.
 * <p>
 * В LS_REQUEST записываются команды, пришедшие от клиента, а из LS_RESPONSE считываются результат этих команд. Для того,
 * чтобы идентифицировать команду с соответствующим результатом, используется идентификатор записи базы данных.
 * <p>
 * Применяется в обработчике команд клиента.
 * @author ViiSE
 * @see lightsearch.server.cmd.client.processor.AbstractProcessorClient
 * @see lightsearch.server.database.DatabaseWriter
 * @since 2.0
 */
public interface DatabaseRecordIdentifier {
    long next();
    long databaseRecordIdentifier();
}
