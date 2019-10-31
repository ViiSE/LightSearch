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

import lightsearch.server.LightSearchServer;
import lightsearch.server.cmd.client.ClientCommandEnum;
import lightsearch.server.data.pojo.ClientCommandResult;
import lightsearch.server.exception.ProcessorNotFoundException;
import lightsearch.server.producer.cmd.ProcessorServiceProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static test.message.TestMessage.*;

@SpringBootTest(classes = LightSearchServer.class)
public class ProcessorClientServiceTestNG extends AbstractTestNGSpringContextTests {

    @Autowired
    private ProcessorServiceProducer processorServiceProducer;

    private ProcessorService<ClientCommandResult> processorService;

    @BeforeClass
    public void setUpClass() {
        processorService = processorServiceProducer.getClientProcessorServiceDefaultInstance(ClientCommandEnum.LOGIN.stringValue());
    }

    @Test
    public void getProcessor() {
        testBegin("ProcessorClientService", "getProcessor()");

        try {
            System.out.println("Processor: " + processorService.getProcessor());
        } catch (ProcessorNotFoundException ex) {
            catchMessage(ex);
        }

        testEnd("ProcessorClientService", "getProcessor()");
    }
}
