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
package lightsearch.server.cmd.admin.processor;

import lightsearch.server.cmd.admin.AdminCommand;
import lightsearch.server.cmd.result.AdminCommandResultCreator;
import lightsearch.server.cmd.result.ResultType;
import lightsearch.server.data.ClientsService;
import lightsearch.server.data.LightSearchServerService;
import lightsearch.server.data.pojo.AdminCommandResult;
import lightsearch.server.data.pojo.Client;
import lightsearch.server.exception.CommandResultException;
import lightsearch.server.log.LoggerServer;
import lightsearch.server.producer.cmd.admin.ErrorAdminCommandServiceProducer;
import lightsearch.server.producer.cmd.result.AdminCommandResultCreatorProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

import static lightsearch.server.log.LogMessageTypeEnum.INFO;

/**
 *
 * @author ViiSE
 */
@Component("clientListRequestProcessor")
@Scope("prototype")
public class ClientListRequestProcessor implements AdminProcessor<AdminCommandResult> {

    private final ClientsService<String, Client> clientsService;

    @Autowired private LoggerServer logger;
    @Autowired private ErrorAdminCommandServiceProducer errAdmCmdServiceProducer;
    @Autowired private AdminCommandResultCreatorProducer admCmdResCrProducer;

    @SuppressWarnings("unchecked")
    public ClientListRequestProcessor(LightSearchServerService serverService) {
        this.clientsService = serverService.clientsService();
    }

    @Override
    synchronized public AdminCommandResult apply(AdminCommand command) {
        try {
            AdminCommandResultCreator commandResultCreator =
                    admCmdResCrProducer.getCommandResultCreatorAdminDefaultInstance(
                            ResultType.TRUE.stringValue(), null, null, new ArrayList<>(clientsService.clients().values()));
            logger.log(ClientListRequestProcessor.class, INFO, "Admin request client list");

            return commandResultCreator.createAdminCommandResult();
        } catch (CommandResultException ex) {
            return errAdmCmdServiceProducer.getErrorAdminCommandServiceDefaultInstance()
                    .createErrorResult(ex.getMessage(), ex.getMessage());
        }
    }
}
