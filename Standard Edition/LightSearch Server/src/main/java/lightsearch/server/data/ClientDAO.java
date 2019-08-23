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
package lightsearch.server.data;

import lightsearch.server.database.DatabaseConnection;

/**
 * DAO клиента LightSearch Server.
 * @author ViiSE
 * @see lightsearch.server.cmd.client.ClientCommand
 * @see lightsearch.server.cmd.client.processor
 * @see lightsearch.server.cmd.client.processor.debug
 * @see lightsearch.server.handler.client
 * @since 2.0
 */
public interface ClientDAO {
    String IMEI();
    DatabaseConnection databaseConnection();
    /**
     * Проверяет, в первый ли раз клиент делает попытку авотризации или нет.
     * @return Результат проверки.
     */
    boolean isFirst();
    
    void setIMEI(String IMEI);
    void setDatabaseConnection(DatabaseConnection databaseConnection);
    /**
     * Устанавливает, в первый ли раз клиент делает попытку авторизации или нет.
     * @param isFirst указывает, в первый ли раз клиент делает попытку авторизации или нет.
     */
    void setIsFirst(boolean isFirst);
}
