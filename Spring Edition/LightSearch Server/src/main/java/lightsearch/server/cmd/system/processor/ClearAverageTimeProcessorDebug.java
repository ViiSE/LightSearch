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
package lightsearch.server.cmd.system.processor;

import lightsearch.server.cmd.result.CommandResult;
import lightsearch.server.cmd.system.SystemCommand;
import lightsearch.server.log.LogMessageTypeEnum;
import lightsearch.server.message.MessageTimeAdder;
import lightsearch.server.producer.cmd.system.CommandResultSystemCreatorProducer;
import lightsearch.server.producer.message.MessageTimeAdderProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author ViiSE
 */
@Component("clearAverageTimeProcessorDebug")
public class ClearAverageTimeProcessorDebug implements ProcessorSystem {

    @Autowired private MessageTimeAdderProducer msgTimeAdderProducer;
    @Autowired private CommandResultSystemCreatorProducer cmdResSysCrProducer;

    @Override
    public CommandResult apply(SystemCommand systemCommand) {
        
        MessageTimeAdder msgTimeAdder = msgTimeAdderProducer.getMessageTimeAdderDefaultInstance();
        msgTimeAdder.clear();

        return commandResult();
    }

    private CommandResult commandResult() {
        return cmdResSysCrProducer.getCommandResultSystemCreatorDefaultInstance().createCommandResult();
    }
}
