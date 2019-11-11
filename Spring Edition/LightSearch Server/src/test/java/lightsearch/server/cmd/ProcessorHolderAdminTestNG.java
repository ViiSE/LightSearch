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
import lightsearch.server.cmd.admin.AdminCommandEnum;
import lightsearch.server.producer.cmd.ProcessorHolderProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

@SpringBootTest(classes = LightSearchServer.class)
public class ProcessorHolderAdminTestNG extends AbstractTestNGSpringContextTests {

    @Autowired
    private ProcessorHolderProducer processorHolderProducer;

    private ProcessorHolder processorHolder;

    @BeforeClass
    public void setUpClass() {
        processorHolder = processorHolderProducer.getProcessorHolderAdminPostInstance();
    }

    @Test
    public void get() {
        testBegin("ProcessorHolderAdmin", "get()");

        Processor proc = processorHolder.get(AdminCommandEnum.BLACKLIST.stringValue());
        assertNotNull(proc, "Processor is null!");

        System.out.println("Processor: " + proc);

        testEnd("ProcessorHolderAdmin", "get()");
    }
}
