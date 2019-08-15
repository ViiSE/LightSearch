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

package lightsearch.server.producer.cmd.admin;

import lightsearch.server.cmd.result.CommandResultAdminCreator;
import lightsearch.server.log.LogMessageTypeEnum;
import lightsearch.server.message.result.ResultTypeMessageEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service("commandResultAdminCreatorProducerDefault")
public class CommandResultAdminCreatorProducerDefaultImpl implements CommandResultAdminCreatorProducer {

    private final String COMMAND_RESULT_ADMIN_CREATOR = "commandResultAdminCreatorDefault";

    @Autowired
    private ApplicationContext ctx;

    @Override
    public CommandResultAdminCreator getCommandResultAdminCreatorDefaultInstance(
            String name, LogMessageTypeEnum type, ResultTypeMessageEnum resultValue, Object message, String logMessage) {
        return (CommandResultAdminCreator) ctx.getBean(COMMAND_RESULT_ADMIN_CREATOR, name, type, resultValue, message, logMessage);
    }
}
