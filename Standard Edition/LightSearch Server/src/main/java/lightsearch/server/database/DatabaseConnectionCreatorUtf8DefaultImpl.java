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
import java.sql.DriverManager;
import java.sql.SQLException;
import lightsearch.server.data.LightSearchServerDatabaseDTO;
import lightsearch.server.exception.DatabaseConnectionCreatorException;

/**
 * Создает соединение с СУБД <a href=https://firebirdsql.org/>Firebird</a> с кодировкой UTF-8.
 * @author ViiSE
 * @since 1.0.0
 */
public class DatabaseConnectionCreatorUtf8DefaultImpl implements DatabaseConnectionCreator {

    private final LightSearchServerDatabaseDTO databaseDTO;
    private final String username;
    private final String password;
    
    public DatabaseConnectionCreatorUtf8DefaultImpl(LightSearchServerDatabaseDTO databaseDTO, String username, String password) {
        this.databaseDTO = databaseDTO;
        this.username = username;
        this.password = password;
    }
 
    @Override
    public DatabaseConnection createFirebirdConnection() throws DatabaseConnectionCreatorException {
        try {           
            Class.forName("org.firebirdsql.jdbc.FBDriver");
            Connection connection = DriverManager.getConnection("jdbc:firebirdsql://" 
                + databaseDTO.dbIP() + ":" + databaseDTO.dbPort() + "/" + databaseDTO.dbName() + 
                "?encoding=utf8&amp;useUnicode=true&amp;characterEncoding=utf8", 
                username, password);
            return DatabaseConnectionInit.databaseConnection(connection);
        }
        catch(ClassNotFoundException ex) {
            throw new DatabaseConnectionCreatorException(ex.getMessage(), 
            "Ошибка драйвера JDBC Jaybird. Обратитесь к администратору для устранения ошибки.");
        }
        catch(SQLException ex) {
            //335544344 - неверное имя базы
            //335544345 - неверное имя юзера или пароль
            //335544721 - неверный порт или ip, или сервер отключен
            switch (ex.getErrorCode()) {
                case 335544344:
                    throw new DatabaseConnectionCreatorException("Invalid database name", "Неверное имя базы данных. Обратитесь к администратору для устранения ошибки.");
                case 335544345:
                    throw new DatabaseConnectionCreatorException("Invalid username and/or password", "Неверное имя пользователя и/или пароль.");
                case 335544721:
                    throw new DatabaseConnectionCreatorException("Invalid port and/or host, or server is shut down", "Неверный порт и/или хост, или сервер отключен.");
            }
            throw new DatabaseConnectionCreatorException(ex.getMessage(), "Неизвестная ошибка. Обратитесь к администратору для устранения проблемы.");
        }
    }
    
}
