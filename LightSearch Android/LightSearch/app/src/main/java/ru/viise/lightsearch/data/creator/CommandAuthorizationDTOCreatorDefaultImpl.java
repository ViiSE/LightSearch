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

package ru.viise.lightsearch.data.creator;

import android.os.Build;

import ru.viise.lightsearch.data.AuthorizationDTO;
import ru.viise.lightsearch.data.CommandAuthorizationDTO;
import ru.viise.lightsearch.data.CommandAuthorizationDTOInit;
import ru.viise.lightsearch.data.ReconnectDTO;
import ru.viise.lightsearch.util.IPAddressProvider;
import ru.viise.lightsearch.util.IPAddressProviderInit;

public class CommandAuthorizationDTOCreatorDefaultImpl implements CommandAuthorizationDTOCreator {

    private final String IMEI;
    private final String username;
    private final String password;
    private final String userIdent;
    private final String ip;
    private final String os;
    private final String model;
    private final ReconnectDTO reconnectDTO;

    public CommandAuthorizationDTOCreatorDefaultImpl(String IMEI, AuthorizationDTO authDTO,
                ReconnectDTO reconnectDTO) {
        this.IMEI = IMEI;
        this.username  = authDTO.username();
        this.password  = authDTO.password();
        this.userIdent = authDTO.userIdent();
        this.reconnectDTO = reconnectDTO;

        IPAddressProvider ipAddrProvider = IPAddressProviderInit.ipAddressProvider();
        ip = ipAddrProvider.ipAddress(true);
        os = Build.VERSION.RELEASE;
        model = Build.MODEL;
    }

    @Override
    public CommandAuthorizationDTO createCommandDTO() {
        return CommandAuthorizationDTOInit.commandAuthorizationDTO(IMEI, ip, os, model, username, password, userIdent, reconnectDTO);
    }
}
