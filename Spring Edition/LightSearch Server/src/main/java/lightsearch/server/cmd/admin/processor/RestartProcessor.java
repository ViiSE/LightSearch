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
import lightsearch.server.cmd.result.ResultType;
import lightsearch.server.data.pojo.AdminCommandResult;
import lightsearch.server.exception.CommandResultException;
import lightsearch.server.log.LogMessageTypeEnum;
import lightsearch.server.log.LoggerServer;
import lightsearch.server.producer.cmd.result.AdminCommandResultCreatorProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.restart.RestartEndpoint;
import org.springframework.stereotype.Component;

/**
 *
 * @author ViiSE
 */
@Component("restartProcessor")
public class RestartProcessor implements AdminProcessor<AdminCommandResult> {

    @Autowired private LoggerServer logger;
    @Autowired private RestartEndpoint restartEndpoint;
    @Autowired private AdminCommandResultCreatorProducer admCmdResCrProducer;

    @Override
    synchronized public AdminCommandResult apply(AdminCommand command) {
        try {
            logger.log(RestartProcessor.class, LogMessageTypeEnum.INFO, "Server restarted");
            restartEndpoint.restart();

            return admCmdResCrProducer.getCommandResultCreatorAdminDefaultInstance(
                    ResultType.TRUE.stringValue(), "Server reboot was successful!", null, null)
                    .createAdminCommandResult();
        } catch (CommandResultException ignore) {
            return null; // never happened
        }
    }
}
