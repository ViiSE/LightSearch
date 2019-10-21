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

package lightsearch.server.controller;

import lightsearch.server.cmd.ProcessorService;
import lightsearch.server.cmd.client.ClientCommand;
import lightsearch.server.data.pojo.ClientCommandDTO;
import lightsearch.server.data.pojo.ClientCommandResult;
import lightsearch.server.exception.ProcessorNotFoundException;
import lightsearch.server.log.LoggerServer;
import lightsearch.server.producer.cmd.ProcessorServiceProducer;
import lightsearch.server.producer.cmd.client.ClientCommandProducer;
import lightsearch.server.producer.cmd.client.processor.ErrorClientCommandServiceProducer;
import lightsearch.server.producer.cmd.result.ClientCommandResultCreatorProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/commands/type/client")
public class ClientCommandsController {

    @Autowired
    private LoggerServer logger;

    @Autowired
    @Qualifier("processorServiceProducerDefault")
    private ProcessorServiceProducer processorServiceProducer;

    @Autowired
    @Qualifier("clientCommandProducerDefault")
    private ClientCommandProducer clientCommandProducer;

    @Autowired
    @Qualifier("commandResultCreatorProducerDefault")
    private ClientCommandResultCreatorProducer clientCommandResultCreatorProducer;

    @Autowired
    @Qualifier("errorClientCommandServiceProducerDefault")
    private ErrorClientCommandServiceProducer errorClientCommandServiceProducer;

    @RequestMapping(method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ClientCommandResult commandClient(@RequestBody ClientCommandDTO clientCommandDTO) {
        try {
            String command = clientCommandDTO.getCommand();
            ProcessorService<ClientCommandResult> processorService =
                    processorServiceProducer.getClientProcessorServiceDefaultInstance(command);

            ClientCommand clientCommand = clientCommandProducer.getClientCommandDefaultInstance(clientCommandDTO);
            return processorService.getProcessor().apply(clientCommand);
        } catch (ProcessorNotFoundException ex) {
            return errorClientCommandServiceProducer.getErrorClientCommandServiceDefaultInstance()
                    .createErrorResult(clientCommandDTO.getIMEI(), "Произошла ошибка. Сообщение: " + ex.getMessage(),
                            ex.getMessage());
        }
    }
}
