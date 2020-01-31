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
import lightsearch.server.producer.cmd.ProcessorServiceProducer;
import lightsearch.server.producer.cmd.client.ClientCommandProducer;
import lightsearch.server.producer.cmd.client.processor.ErrorClientCommandServiceProducer;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients/commands")
public class ClientsController {

    private final ProcessorServiceProducer processorServiceProducer;
    private final ClientCommandProducer clientCommandProducer;
    private final ErrorClientCommandServiceProducer errorClientCommandServiceProducer;

    public ClientsController(
            ProcessorServiceProducer processorServiceProducer,
            ClientCommandProducer clientCommandProducer,
            ErrorClientCommandServiceProducer errorClientCommandServiceProducer) {
        this.processorServiceProducer = processorServiceProducer;
        this.clientCommandProducer = clientCommandProducer;
        this.errorClientCommandServiceProducer = errorClientCommandServiceProducer;
    }

    @PostMapping("/login")
    public ClientCommandResult loginCommand(@RequestBody ClientCommandDTO clientCommandDTO) {
        return doCommand("login", clientCommandDTO);
    }

    @GetMapping("/search")
    public ClientCommandResult searchCommand(@RequestBody ClientCommandDTO clientCommandDTO) {
        return doCommand("login", clientCommandDTO);
    }

    private ClientCommandResult doCommand(String cmdName, ClientCommandDTO clientCommandDTO) {
        try {
            ProcessorService<ClientCommandResult> processorService =
                    processorServiceProducer.getClientProcessorServiceDefaultInstance(cmdName);

            ClientCommand clientCommand = clientCommandProducer.getClientCommandDefaultInstance(clientCommandDTO);
            return processorService.getProcessor().apply(clientCommand);
        } catch (ProcessorNotFoundException ex) {
            return errorClientCommandServiceProducer.getErrorClientCommandServiceDefaultInstance()
                    .createErrorResult("Произошла ошибка. " + "Сообщение: " + ex.getMessage(), ex.getMessage());
        }
    }
}
