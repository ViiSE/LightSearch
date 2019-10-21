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

import lightsearch.server.cmd.client.ClientCommand;
import lightsearch.server.data.BlacklistService;
import lightsearch.server.exception.CheckerException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("commandCheckerClientAuthorization")
@Scope("prototype")
public class CommandCheckerClientAuthorizationImpl implements CommandChecker {

    private final BlacklistService blacklistService;
    private final ClientCommand command;
    private final LightSearchChecker checker;

    public CommandCheckerClientAuthorizationImpl(ClientCommand command, BlacklistService blacklistService, LightSearchChecker checker) {
        this.blacklistService = blacklistService;
        this.command = command;
        this.checker = checker;
    }

    @Override
    public void check() throws CheckerException {
        if(checker.isNull(command.username(), command.password(), command.IMEI(), command.ip(), command.os(),
                command.model(), command.userIdentifier()))
            throw new CheckerException("Неверный формат команды.", "Authentication: unknown client.");

        if(blacklistService.blacklist().contains(command.IMEI()))
            throw new CheckerException("Извините, но вы находитесь в черном списке.", "Client " + command.IMEI() + " in the blacklist.");
    }
}
