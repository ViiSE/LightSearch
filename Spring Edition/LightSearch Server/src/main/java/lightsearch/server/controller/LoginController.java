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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

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
//            String command = "login"; //clientCommandDTO.getCommand();
//            if(!command.equals(ClientCommandEnum.CONNECT.stringValue()))
//                return sendError(clientCommandDTO.getIMEI(), "Wrong command.", "LoginController: Wrong command.");

            ProcessorService<ClientCommandResult> processorService =
                    processorServiceProducer.getClientProcessorServiceDefaultInstance("login");

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

    // FIXME: 25.10.2019 API?
    //----------------------------------------
    // login/clients
    // commands/type/client/search
    // commands/type/client/open_soft_check
    // commands/type/client/confirm_prod_sf
    // commands/type/client/cancel_soft_check
    // commands/type/client/close_soft_check

    // login/admins
    // commands/type/admin/add_bl
    // commands/type/admin/ch_db
    // commands/type/admin/kick
    // commands/type/admin/cl_list
    // commands/type/admin/cr_admin
    // commands/type/admin/del_bl
    // commands/type/admin/restart
    // commands/type/admin/tout_cl
    // commands/type/admin/tout_server
    //----------------------------------------
}
