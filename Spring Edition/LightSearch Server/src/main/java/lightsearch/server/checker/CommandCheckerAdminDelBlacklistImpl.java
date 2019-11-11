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

import lightsearch.server.cmd.admin.AdminCommand;
import lightsearch.server.data.BlacklistService;
import lightsearch.server.exception.CheckerException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("commandCheckerAdminDelBlacklist")
@Scope("prototype")
public class CommandCheckerAdminDelBlacklistImpl implements CommandChecker {

    private final BlacklistService blacklistService;
    private final AdminCommand command;
    private final LightSearchChecker checker;

    public CommandCheckerAdminDelBlacklistImpl(AdminCommand command, BlacklistService blacklistService, LightSearchChecker checker) {
        this.blacklistService = blacklistService;
        this.command = command;
        this.checker = checker;
    }

    @Override
    public void check() throws CheckerException {
        if(checker.isNull(command.IMEI()))
            throw new CheckerException("Wrong command format. IMEI is null!", "AddBlacklist: unknown client: IMEI is null!");

        if(checker.isEmpty(command.IMEI()))
            throw new CheckerException("Wrong command format. IMEI is empty!", "AddBlacklist: unknown client: IMEI is empty!");

        if(!blacklistService.blacklist().contains(command.IMEI()))
            throw new CheckerException("This client is not in the blacklist!", "Client " + command.IMEI() + " already in the blacklist.");
    }
}
