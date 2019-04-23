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

import java.util.HashMap;
import java.util.Map;
import lightsearch.server.database.DatabaseConnection;

/**
 *
 * @author ViiSE
 */
public class ClientDAODefaultImpl implements ClientDAO {

    private String username;
    private String IMEI;
    Map<String, String> codesSklad = new HashMap<>();
    Map<String, String> codesTK = new HashMap<>();
    private DatabaseConnection databaseConnection;
    private boolean isFirst = true;
    
    @Override
    public String username() {
        return username;
    }

    @Override
    public String IMEI() {
        return IMEI;
    }

    @Override
    public DatabaseConnection databaseConnection() {
        return databaseConnection;
    }

    @Override
    public boolean isFirst() {
        return isFirst;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }

    @Override
    public void setDatabaseConnection(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public void setIsFirst(boolean isFirst) {
        this.isFirst = isFirst;
    }

    @Override
    public Map<String, String> codesSklad() {
        return codesSklad;
    }

    @Override
    public Map<String, String> codesTK() {
        return codesTK;
    }

    @Override
    public void setCodesSklad(Map<String, String> codesSklad) {
        this.codesSklad = codesSklad;
    }

    @Override
    public void setCodesTK(Map<String, String> codesTK) {
        this.codesTK = codesTK;
    }
    
}