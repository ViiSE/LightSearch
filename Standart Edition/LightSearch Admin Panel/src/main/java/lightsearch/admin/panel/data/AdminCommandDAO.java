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
public interface AdminCommandDAO {
    String name();
    String serverTimeout();
    String clientTimeout();
    String IMEI();
    String adminName();
    String password();
    String ip();
    String port();
    String dbName();
    void setName(String name);
    void setServerTimeout(String serverTime);
    void setClientTimeout(String clientTimeout);
    void setIMEI(String IMEI);
    void setAdminName(String adminName);
    void setPassword(String password);
    void setIp(String ip);
    void setPort(String port);
    void setDbName(String dbName);
}
