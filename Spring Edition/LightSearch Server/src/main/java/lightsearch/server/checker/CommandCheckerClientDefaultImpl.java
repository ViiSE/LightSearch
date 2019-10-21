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

package lightsearch.server.checker;

import lightsearch.server.data.BlacklistService;
import lightsearch.server.data.ClientsService;
import lightsearch.server.data.LightSearchServerService;
import lightsearch.server.data.pojo.Client;
import lightsearch.server.exception.CheckerException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("commandCheckerClientDefault")
@Scope("prototype")
public class CommandCheckerClientDefaultImpl implements CommandChecker {

    private final ClientsService<String, Client> clientsService;
    private final BlacklistService blacklistService;
    private final String IMEI;

    @SuppressWarnings("unchecked")
    public CommandCheckerClientDefaultImpl(String IMEI, LightSearchServerService serverService) {
        this.IMEI = IMEI;
        this.clientsService = serverService.clientsService();
        this.blacklistService = serverService.blacklistService();
    }

    @Override
    public void check() throws CheckerException {
        if(blacklistService.blacklist().contains(IMEI))
            throw new CheckerException("Извините, но вы находитесь в черном списке.",
                    "Client " + IMEI + " in the blacklist.");

        if(!clientsService.clients().containsKey(IMEI))
            throw new CheckerException("Для того, чтобы пользоваться функциями LightSearch, необходимо авторизоваться.",
                    "Client " + IMEI + " not authorized.");
    }
}
