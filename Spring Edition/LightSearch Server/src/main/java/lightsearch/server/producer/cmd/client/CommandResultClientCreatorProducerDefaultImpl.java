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

package lightsearch.server.producer.cmd.client;

import lightsearch.server.cmd.result.CommandResultClientCreator;
import lightsearch.server.log.LogMessageTypeEnum;
import lightsearch.server.message.result.ResultTypeMessageEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service("commandResultClientCreatorProducerDefault")
public class CommandResultClientCreatorProducerDefaultImpl implements CommandResultClientCreatorProducer {

    private final String COMMAND_RESULT_CLIENT_CREATOR = "commandResultClientCreatorDefault";

    @Autowired
    private ApplicationContext ctx;

    @Override
    public CommandResultClientCreator getCommandResultClientCreatorDefaultInstance(
            String name, LogMessageTypeEnum type, ResultTypeMessageEnum resultValue, Object message, String logMessage) {
        return (CommandResultClientCreator) ctx.getBean(COMMAND_RESULT_CLIENT_CREATOR, name, type, resultValue, message, logMessage);
    }
}
