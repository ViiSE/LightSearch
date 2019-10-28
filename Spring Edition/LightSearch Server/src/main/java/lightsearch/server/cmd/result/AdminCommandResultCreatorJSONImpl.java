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

package lightsearch.server.cmd.result;

import lightsearch.server.data.pojo.AdminCommandResult;
import lightsearch.server.data.pojo.Client;
import lightsearch.server.exception.CommandResultException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("adminCommandResultCreatorJSON")
@Scope("prototype")
public class AdminCommandResultCreatorJSONImpl implements AdminCommandResultCreator {

    private final String isDone;
    private final String message;
    private final List<String> blacklist;
    private final List<Client> clients;

    public AdminCommandResultCreatorJSONImpl(String isDone, String message, List<String> blacklist, List<Client> clients) {
        this.isDone = isDone;
        this.message = message;
        this.blacklist = blacklist;
        this.clients = clients;
    }

    @Override
    public AdminCommandResult createAdminCommandResult() throws CommandResultException {
        return new AdminCommandResult(isDone, message, blacklist, clients);
    }
}
