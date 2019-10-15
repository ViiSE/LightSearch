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

package lightsearch.server.data.pojo;

public class AdminCommandDTO {

    private String command;
    private String serverTime;
    private String clientTimeout;
    private String IMEI;
    private String name;
    private String adminName;
    private String password;
    private String ip;
    private String port;
    private String dbName;

    public void setCommand(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public void setServerTime(String serverTime) {
        this.serverTime = serverTime;
    }

    public String getServerTime() {
        return serverTime;
    }

    public void setClientTimeout(String clientTimeout) {
        this.clientTimeout = clientTimeout;
    }

    public String getClientTimeout() {
        return clientTimeout;
    }

    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }

    public String getIMEI() {
        return IMEI;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getIp() {
        return ip;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getPort() {
        return port;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getDbName() {
        return dbName;
    }
}
