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
 * Реализация интерфейса {@link lightsearch.server.data.LightSearchServerSettingsDAO}.
 * @author ViiSE
 * @since 2.0
 */
public class LightSearchServerSettingsDAOImpl implements LightSearchServerSettingsDAO {

    private int serverReboot;
    private int clientTimeout;
    
    public LightSearchServerSettingsDAOImpl(int serverReboot, int clientTimeout) {
        this.clientTimeout = clientTimeout;
        this.serverReboot = serverReboot;
    }

    @Override
    public int serverRebootValue() {
        return serverReboot;
    }

    @Override
    public int clientTimeoutValue() {
        return clientTimeout;
    }

    @Override
    public void setServerRebootValue(int serverRebootValue) {
        this.serverReboot = serverRebootValue;
    }

    @Override
    public void setClientTimeoutValue(int clientTimeoutValue) {
        this.clientTimeout = clientTimeoutValue;
    }
    
    
}
