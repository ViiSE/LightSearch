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

import java.util.Map;

/**
 *
 * @author ViiSE
 */
public interface ClientDAO {
    String IMEI();
    Map<String, String> codesSklad();
    Map<String, String> codesTK();
    DatabaseConnection databaseConnection();
    boolean isFirst();
    
    void setIMEI(String IMEI);
    void setCodesSklad(Map<String, String> codesSklad);
    void setCodesTK(Map<String, String> codesTK);
    void setDatabaseConnection(DatabaseConnection databaseConnection);
    void setIsFirst(boolean isFirst);
}
