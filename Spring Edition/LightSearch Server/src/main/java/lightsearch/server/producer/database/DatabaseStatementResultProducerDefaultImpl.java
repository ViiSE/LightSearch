/*
 *  Copyright 2019 ViiSE.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package lightsearch.server.producer.database;

import lightsearch.server.database.statement.result.DatabaseStatementResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service("databaseStatementResultProducerDefault")
public class DatabaseStatementResultProducerDefaultImpl implements DatabaseStatementResultProducer {

    private final String DATABASE_STATEMENT_RESULT = "databaseStatementResultDefault";

    @Autowired
    private ApplicationContext ctx;

    @Override
    public DatabaseStatementResult getDatabaseStatementResultDefaultInstance(String result) {
        return (DatabaseStatementResult) ctx.getBean(DATABASE_STATEMENT_RESULT, result);
    }
}
