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
import lightsearch.server.cmd.client.ClientCommandEnum;
import lightsearch.server.data.pojo.ClientCommandDTO;
import lightsearch.server.data.pojo.ClientCommandResult;
import lightsearch.server.exception.ProcessorNotFoundException;
import lightsearch.server.producer.cmd.ProcessorServiceProducer;
import lightsearch.server.producer.cmd.client.ClientCommandProducer;
import lightsearch.server.producer.cmd.client.processor.ErrorClientCommandServiceProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class ClientLoginController {

    @Autowired
    @Qualifier("processorServiceProducerDefault")
    private ProcessorServiceProducer processorServiceProducer;

    @Autowired
    @Qualifier("clientCommandProducerDefault")
    private ClientCommandProducer clientCommandProducer;

    @Autowired
    @Qualifier("errorClientCommandServiceProducerDefault")
    private ErrorClientCommandServiceProducer errorClientCommandServiceProducer;

    @RequestMapping(value = "/clients", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ClientCommandResult loginClient(@RequestBody ClientCommandDTO clientCommandDTO) {
        try {
            ProcessorService<ClientCommandResult> processorService =
                    processorServiceProducer.getClientProcessorServiceDefaultInstance(ClientCommandEnum.LOGIN.stringValue());

            ClientCommand clientCommand = clientCommandProducer.getClientCommandDefaultInstance(clientCommandDTO);
            return processorService.getProcessor().apply(clientCommand);
        } catch (ProcessorNotFoundException ex) {
            return sendError(clientCommandDTO.getIMEI(), "Произошла ошибка. Сообщение: " + ex.getMessage(),
                    ex.getMessage());
        }
    }

    private ClientCommandResult sendError(String IMEI, String message, String logMessage) {
        return errorClientCommandServiceProducer.getErrorClientCommandServiceDefaultInstance()
                .createErrorResult(IMEI, message, logMessage);
    }
}
