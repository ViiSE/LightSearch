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
import lightsearch.server.exception.CommandResultException;
import lightsearch.server.producer.cmd.result.AdminCommandResultCreatorProducer;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service("errorResultAdminCommandCreatorDefault")
@Scope("prototype")
public class ErrorResultAdminCommandCreatorDefaultImpl implements ErrorResultAdminCommandCreator {

    private final AdminCommandResultCreatorProducer commandResultCreatorProducer;
    private final String message;

    public ErrorResultAdminCommandCreatorDefaultImpl(String message, AdminCommandResultCreatorProducer commandResultCreatorProducer) {
        this.message = message;
        this.commandResultCreatorProducer = commandResultCreatorProducer;
    }

    @Override
    public AdminCommandResult createErrorResult() {
        AdminCommandResultCreator commandResultCreatorError =
                commandResultCreatorProducer.getCommandResultCreatorAdminErrorInstance(message);
        try {
            return commandResultCreatorError.createAdminCommandResult();
        } catch (CommandResultException ignore) {
            return null; // never happened
        }
    }
}
