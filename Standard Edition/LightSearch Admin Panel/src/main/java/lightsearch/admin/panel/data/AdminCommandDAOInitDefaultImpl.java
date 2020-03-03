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
package lightsearch.admin.panel.data;

/**
 *
 * @author ViiSE
 */
public class AdminCommandDAOInitDefaultImpl implements AdminCommandDAO {

    private String name;
    private String serverTime;
    private String clientTimeout;
    private String IMEI;
    private String adminName;
    private String password;
    private String ip;
    private String port;
    private String dbName;
    
    @Override
    public String name() {
        return name;
    }

    @Override
    public String serverTimeout() {
        return serverTime;
    }

    @Override
    public String clientTimeout() {
        return clientTimeout;
    }

    @Override
    public String IMEI() {
        return IMEI;
    }

    @Override
    public String adminName() {
        return adminName;
    }

    @Override
    public String password() {
        return password;
    }

    @Override
    public String ip() {
        return ip;
    }

    @Override
    public String port() {
        return port;
    }

    @Override
    public String dbName() {
        return dbName;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setServerTimeout(String serverTime) {
        this.serverTime = serverTime;
    }

    @Override
    public void setClientTimeout(String clientTimeout) {
        this.clientTimeout = clientTimeout;
    }

    @Override
    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }

    @Override
    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public void setPort(String port) {
        this.port = port;
    }

    @Override
    public void setDbName(String dbName) {
        this.dbName = dbName;
    }
}
