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

package lightsearch.server.producer.database;

import lightsearch.server.database.DatabaseConnection;
import lightsearch.server.database.statement.DatabasePreparedStatement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service("databasePreparedStatementProducerDefault")
public class DatabasePreparedStatementProducerDefaultImpl implements DatabasePreparedStatementProducer {

    private final String DB_PREP_STATEMENT_SELECT = "databasePreparedStatementSelectDefault";
    private final String DB_PREP_STATEMENT_UPDATE = "databasePreparedStatementUpdateDefault";
    private final String DB_PREP_STATEMENT_INSERT = "databasePreparedStatementInsertWin1251Default";

    @Autowired
    private ApplicationContext ctx;

    @Override
    public DatabasePreparedStatement getDatabasePreparedStatementSelectDefaultInstance(
            DatabaseConnection databaseConnection, String tableName, long lsCode, boolean state) {
        return (DatabasePreparedStatement) ctx.getBean(DB_PREP_STATEMENT_SELECT, databaseConnection, tableName, lsCode, state);
    }

    @Override
    public DatabasePreparedStatement getDatabasePreparedStatementUpdateDefaultInstance(
            DatabaseConnection databaseConnection, String tableName, long lsCode, boolean state) {
        return (DatabasePreparedStatement) ctx.getBean(DB_PREP_STATEMENT_UPDATE, databaseConnection, tableName, lsCode, state);
    }

    @Override
    public DatabasePreparedStatement getDatabasePreparedStatementInsertWin1251DefaultInstance(
            DatabaseConnection databaseConnection, String tableName, String command, String dateTime, long lsCode, boolean state) {
        return (DatabasePreparedStatement) ctx.getBean(DB_PREP_STATEMENT_INSERT, databaseConnection, tableName, command, dateTime, lsCode, state);
    }
}
