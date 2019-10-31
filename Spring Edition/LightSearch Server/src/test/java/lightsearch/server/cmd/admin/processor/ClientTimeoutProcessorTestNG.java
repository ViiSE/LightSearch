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

import com.fasterxml.jackson.core.JsonProcessingException;
import lightsearch.server.LightSearchServer;
import lightsearch.server.cmd.ProcessorHolder;
import lightsearch.server.cmd.ProcessorHolderAdminTestImpl;
import lightsearch.server.cmd.admin.AdminCommand;
import lightsearch.server.cmd.admin.AdminCommandEnum;
import lightsearch.server.data.*;
import lightsearch.server.data.pojo.AdminCommandDTO;
import lightsearch.server.data.pojo.AdminCommandResult;
import lightsearch.server.data.pojo.Client;
import lightsearch.server.producer.checker.LightSearchCheckerProducer;
import lightsearch.server.producer.cmd.admin.AdminCommandProducer;
import lightsearch.server.producer.cmd.admin.processor.ProcessorAdminProducer;
import lightsearch.server.producer.initialization.CurrentServerDirectoryProducer;
import lightsearch.server.producer.initialization.OsDetectorProducer;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import test.ResourcesFilesPath;

import static org.mockito.Mockito.when;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

@SpringBootTest(classes = LightSearchServer.class)
public class ClientTimeoutProcessorTestNG extends AbstractTestNGSpringContextTests {

    @Autowired private AdminCommandProducer commandProducer;
    @Autowired private OsDetectorProducer osDetectorProducer;
    @Autowired private CurrentServerDirectoryProducer currentServerDirectoryProducer;
    @Autowired private ProcessorAdminProducer processorAdminProducer;
    @Autowired private LightSearchCheckerProducer checkerProducer;

    @Mock
    private LightSearchServerService serverService;

    @InjectMocks
    private ProcessorHolder processorHolder;

    private AdminCommand adminCommand;
    private final BlacklistService<String> blacklistService = new BlacklistServiceDefaultImpl();
    private final ClientsService<String, Client> clientsService = new ClientsServiceDefaultImpl();

    @BeforeClass
    public void setUpClass() {
        processorHolder = new ProcessorHolderAdminTestImpl(processorAdminProducer, checkerProducer);

        MockitoAnnotations.initMocks(this);

        AdminCommandDTO admCmdDTO = new AdminCommandDTO();
        admCmdDTO.setCommand(AdminCommandEnum.CLIENT_TIMEOUT.stringValue());
        admCmdDTO.setClientTimeout(10);
        adminCommand = commandProducer.getAdminCommandDefaultInstance(admCmdDTO);

        String currentDirectory = currentServerDirectoryProducer
                .getCurrentServerDirectoryFromFileInstance(osDetectorProducer.getOsDetectorDefaultInstance())
                .currentDirectory() + ResourcesFilesPath.getResourcesFilesPath();

        when(serverService.currentDirectory()).thenReturn(currentDirectory);
        when(serverService.blacklistService()).thenReturn(blacklistService);
        when(serverService.clientsService()).thenReturn(clientsService);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void apply() throws JsonProcessingException {
        testBegin("ClientListRequestProcessor", "apply()");

        AdminCommandResult result = (AdminCommandResult) processorHolder.get(adminCommand.command()).apply(adminCommand);
        System.out.println("Result: ");
        System.out.println("isDone: "  + result.getIsDone());
        System.out.println("message: " + result.getMessage());

        testEnd("ClientListRequestProcessor", "apply()");
    }
}
