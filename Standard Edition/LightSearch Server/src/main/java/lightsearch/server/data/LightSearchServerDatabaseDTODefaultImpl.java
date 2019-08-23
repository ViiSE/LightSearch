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

/**
 * Реализация интерфейса {@link lightsearch.server.data.LightSearchServerDatabaseDTO} по умолчанию.
 * @author ViiSE
 * @since 2.0
 */
public class LightSearchServerDatabaseDTODefaultImpl implements LightSearchServerDatabaseDTO {

    private final String dbIP;
    private final String dbName;
    private final int dbPort;
    
    public LightSearchServerDatabaseDTODefaultImpl(String dbIP, String dbName, int dbPort) {
        this.dbIP = dbIP;
        this.dbName = dbName;
        this.dbPort = dbPort;
    }

    @Override
    public String dbIP() {
        return dbIP;
    }

    @Override
    public String dbName() {
        return dbName;
    }

    @Override
    public int dbPort() {
        return dbPort;
    }
    
}
