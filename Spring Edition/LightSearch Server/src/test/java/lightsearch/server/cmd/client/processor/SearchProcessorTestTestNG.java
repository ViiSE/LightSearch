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

package lightsearch.server.cmd.client.processor;

import lightsearch.server.LightSearchServer;
import lightsearch.server.cmd.ProcessorHolder;
import lightsearch.server.cmd.client.ClientCommand;
import lightsearch.server.data.pojo.ClientCommandDTO;
import lightsearch.server.data.pojo.ClientCommandResult;
import lightsearch.server.producer.cmd.ProcessorHolderProducer;
import lightsearch.server.producer.cmd.client.ClientCommandProducer;
import lightsearch.server.producer.identifier.DatabaseRecordIdentifierProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

@SpringBootTest(classes = LightSearchServer.class)
public class SearchProcessorTestTestNG extends AbstractTestNGSpringContextTests {

    @Autowired private ProcessorHolderProducer holderProducer;
    @Autowired private ClientCommandProducer commandProducer;
    @Autowired private DatabaseRecordIdentifierProducer identifierProducer;

    private ProcessorHolder processorHolder;
    private ClientCommand clientCommand;

    @BeforeClass
    public void setUpClass() {
        processorHolder = holderProducer.getProcessorHolderClientTestInstance();
        ClientCommandDTO clientCommandDTO = new ClientCommandDTO();
        clientCommandDTO.setCommand("search");
        clientCommandDTO.setIMEI("111111111111111");
        clientCommandDTO.setBarcode("22505");
        clientCommandDTO.setSklad("Sklad 1");
        clientCommandDTO.setTK("null");

        clientCommand = commandProducer.getClientCommandDefaultInstance(clientCommandDTO);
        identifierProducer.getDatabaseRecordIdentifierDefaultInstance(1);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void apply() {
        testBegin("SearchProcessor", "apply()");

        ClientCommandResult result = (ClientCommandResult) processorHolder.get(clientCommand.command()).apply(clientCommand);
        System.out.println("Result: ");
        System.out.println("IMEI: " + result.getIMEI());
        System.out.println("isDone: " + result.getIsDone());
        System.out.println("data: ");
        result.getData().forEach(product -> {
            System.out.println("--------------------------------------------------");
            System.out.println("name: " + product.getName());
            System.out.println("id: " + product.getId());
            System.out.println("amount: " + product.getAmount());
            System.out.println("price: " + product.getPrice());
            System.out.println("subdivision: " + product.getSubdiv());
            System.out.println("ei: " + product.getEi());
        });

        testEnd("SearchProcessor", "apply()");
    }
}