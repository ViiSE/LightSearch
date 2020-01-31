/*
 *  Copyright 2019 ViiSE.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package lightsearch.server.cmd.admin;

import lightsearch.server.data.AdminCommandDTO;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("adminCommandDefault")
@Scope("prototype")
public class AdminCommandDefaultImpl implements AdminCommand {

    private final String username;
    private final String restartTime;
    private final int clientTimeout;
    private final String IMEI;
    private final String password;
    private final String ip;
    private final int port;
    private final String dbName;
    private final String command;

    public AdminCommandDefaultImpl(AdminCommandDTO adminCommandDTO) {
        this.username = adminCommandDTO.getUsername();
        this.restartTime = adminCommandDTO.getRestartTime();
        this.clientTimeout = adminCommandDTO.getClientTimeout();
        this.IMEI = adminCommandDTO.getIMEI();
        this.password = adminCommandDTO.getPassword();
        this.ip = adminCommandDTO.getIp();
        this.port = adminCommandDTO.getPort();
        this.dbName = adminCommandDTO.getDbName();
        this.command = adminCommandDTO.getCommand();
    }

    @Override
    public String username() {
        return username;
    }

    @Override
    public String restartTime() {
        return restartTime;
    }

    @Override
    public int clientTimeout() {
        return clientTimeout;
    }

    @Override
    public String IMEI() {
        return IMEI;
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
    public int port() {
        return port;
    }

    @Override
    public String dbName() {
        return dbName;
    }

    @Override
    public String command() {
        return command;
    }
}
