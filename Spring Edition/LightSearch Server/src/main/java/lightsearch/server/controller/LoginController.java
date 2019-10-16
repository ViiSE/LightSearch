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

import lightsearch.server.cmd.CommandConverter;
import lightsearch.server.cmd.ProcessorHolder;
import lightsearch.server.cmd.client.ClientCommand;
import lightsearch.server.cmd.client.processor.ClientProcessor;
import lightsearch.server.cmd.result.ErrorResultCreator;
import lightsearch.server.data.pojo.ClientCommandDTO;
import lightsearch.server.data.pojo.ClientCommandResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

//    @Autowired
//    @Qualifier("processorHolderProducerDefault")
//    private ProcessorHolderProducer holderProducer;

//    @Autowired
//    @Qualifier("errorResultCreatorProducerDefault")
//    private ErrorResultCreatorProducer errorResultCreatorProducer;

//    @Autowired
//    @Qualifier("commandConverterProducerDefault")
//    private CommandConverterProducer commandConverterProducer;

    @RequestMapping(value = "/client", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ClientCommandResult loginClient(@RequestBody ClientCommandDTO clientCommandDTO) {
        String command = clientCommandDTO.getCommand();
        if(command == null)
            return generateErrorResult(clientCommandDTO.getIMEI(), "Missing 'command' field");

//        ProcessorHolder holder = holderProducer.getClientProcessorHolderInstance();
//        ClientProcessor<CommandResult> processor = (ClientProcessor) holder.get(command);
//        if(processor == null)
//            return generateErrorResult(clientCommandDTO.getIMEI(), "Command '" + command + "' not found");
//
//        CommandConverter commandConverter = commandConverterProducer.getCommandConverterClientInstance(clientCommandDTO);
//        ClientCommand clientCommand = (ClientCommand) commandConverter.convert();
//
//        return (ClientCommandResult) processor.apply(clientCommand);
        return null;
    }

    private ClientCommandResult generateErrorResult(String IMEI, String message) {
//        ErrorResultCreator<ClientCommandResult> errorResultCreator =
//                errorResultCreatorProducer.getErrorResultCreatorClientInstance(IMEI, message);
//        return errorResultCreator.create();
        return null;
    }
}
