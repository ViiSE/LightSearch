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

import lightsearch.server.data.LightSearchServerDatabaseDTO;
import lightsearch.server.database.DatabaseConnectionCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service("databaseConnectionCreatorProducerDefault")
public class DatabaseConnectionCreatorProducerDefaultImpl implements DatabaseConnectionCreatorProducer {

    private final String DATABASE_CONNECTION_CREATOR_WIN1251 = "databaseConnectionCreatorWin1251Default";
    private final String DATABASE_CONNECTION_CREATOR_UTF8    = "databaseConnectionCreatorUtf8Default";

    @Autowired
    private ApplicationContext ctx;

    @Override
    public DatabaseConnectionCreator getDatabaseConnectionCreatorWin1251DefaultInstance(LightSearchServerDatabaseDTO databaseDTO, String username, String password) {
        return (DatabaseConnectionCreator) ctx.getBean(DATABASE_CONNECTION_CREATOR_WIN1251, databaseDTO, username, password);
    }

    @Override
    public DatabaseConnectionCreator getDatabaseConnectionCreatorUtf8DefaultInstance(LightSearchServerDatabaseDTO databaseDTO, String username, String password) {
        return (DatabaseConnectionCreator) ctx.getBean(DATABASE_CONNECTION_CREATOR_UTF8, databaseDTO, username, password);
    }
}
