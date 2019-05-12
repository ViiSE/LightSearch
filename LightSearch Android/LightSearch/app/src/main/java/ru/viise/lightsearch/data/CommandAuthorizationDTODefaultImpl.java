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

package ru.viise.lightsearch.data;

public class CommandAuthorizationDTODefaultImpl implements CommandAuthorizationDTO {

    private final String IMEI;
    private final String ip;
    private final String os;
    private final String model;
    private final String username;
    private final String password;

    public CommandAuthorizationDTODefaultImpl(String IMEI, String ip, String os, String model,
                  String username, String password) {
        this.IMEI = IMEI;
        this.ip = ip;
        this.os = os;
        this.model = model;
        this.username = username;
        this.password = password;
    }

    @Override
    public String IMEI() {
        return IMEI;
    }

    @Override
    public String ip() {
        return ip;
    }

    @Override
    public String os() {
        return os;
    }

    @Override
    public String model() {
        return model;
    }

    @Override
    public String username() {
        return username;
    }

    @Override
    public String password() {
        return password;
    }
}
