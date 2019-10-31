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

package lightsearch.server.cmd;

import lightsearch.server.data.pojo.AdminCommandResult;
import lightsearch.server.exception.ProcessorNotFoundException;
import lightsearch.server.producer.cmd.ProcessorHolderProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service("adminProcessorServiceDefaultImpl")
@Scope("prototype")
public class AdminProcessorServiceDefaultImpl implements ProcessorService<AdminCommandResult> {

    private final String command;

    @Autowired
    @Qualifier("processorHolderProducerDefault")
    private ProcessorHolderProducer holderProducer;

    public AdminProcessorServiceDefaultImpl(String command) {
        this.command = command;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Processor<? super Command, AdminCommandResult> getProcessor() throws ProcessorNotFoundException {
        if(command == null)
            throw new ProcessorNotFoundException("Missing 'command' field");

        ProcessorHolder holder = holderProducer.getProcessorHolderAdminInstance();

        if(holder.get(command) == null)
            throw new ProcessorNotFoundException("Command '" + command + "' not found");

        return holder.get(command);
    }
}