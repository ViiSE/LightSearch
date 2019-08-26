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
 * Соединение с СУБД <a href=https://firebirdsql.org/>Firebird</a> по умолчанию.
 * @author ViiSE
 * @see lightsearch.server.database.DatabaseConnection
 * @see lightsearch.server.database.DatabaseConnectionCreator
 * @since 1.0
 */
public class DatabaseConnectionFirebirdDefaultImpl implements DatabaseConnection {

    private final Connection connection;
    
    public DatabaseConnectionFirebirdDefaultImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Connection connection() {
        return connection;
    }
    
}
