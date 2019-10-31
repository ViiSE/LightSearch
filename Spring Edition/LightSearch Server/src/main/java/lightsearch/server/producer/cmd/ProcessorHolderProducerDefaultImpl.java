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

package lightsearch.server.producer.cmd;

import lightsearch.server.cmd.ProcessorHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service("processorHolderProducerDefault")
public class ProcessorHolderProducerDefaultImpl implements ProcessorHolderProducer {

    @Autowired
    private ApplicationContext ctx;

    @Override
    public ProcessorHolder getProcessorHolderClientInstance() {
        return ctx.getBean("clientProcessorHolder", ProcessorHolder.class);
    }

    @Override
    public ProcessorHolder getProcessorHolderClientTestInstance() {
        return ctx.getBean("clientProcessorHolderTest", ProcessorHolder.class);
    }

    @Override
    public ProcessorHolder getProcessorHolderAdminInstance() {
        return ctx.getBean("adminProcessorHolder", ProcessorHolder.class);
    }

    @Override
    public ProcessorHolder getProcessorHolderAdminTestInstance() {
        return ctx.getBean("adminProcessorHolderTest", ProcessorHolder.class);
    }
}
