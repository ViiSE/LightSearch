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
import lightsearch.server.cmd.admin.AdminCommand;
import lightsearch.server.data.pojo.AdminCommandDTO;
import lightsearch.server.data.pojo.AdminCommandResult;
import lightsearch.server.exception.ProcessorNotFoundException;
import lightsearch.server.producer.cmd.ProcessorServiceProducer;
import lightsearch.server.producer.cmd.admin.AdminCommandProducer;
import lightsearch.server.producer.cmd.admin.ErrorAdminCommandServiceProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/commands/type/admin")
public class AdminCommandsController {

    @Autowired
    @Qualifier("processorServiceProducerDefault")
    private ProcessorServiceProducer processorServiceProducer;

    @Autowired
    @Qualifier("adminCommandProducerDefault")
    private AdminCommandProducer adminCommandProducer;

    @Autowired
    @Qualifier("errorAdminCommandServiceProducerDefault")
    private ErrorAdminCommandServiceProducer errorAdminCommandServiceProducer;

    @RequestMapping(method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public AdminCommandResult adminCommand(@RequestBody AdminCommandDTO adminCommandDTO) {
        try {
            String command = adminCommandDTO.getCommand();
            ProcessorService<AdminCommandResult> processorService =
                    processorServiceProducer.getAdminProcessorServiceDefaultInstance(command);

            AdminCommand adminCommand = adminCommandProducer.getAdminCommandDefaultInstance(adminCommandDTO);
            return processorService.getProcessor().apply(adminCommand);
        } catch (ProcessorNotFoundException ex) {
            return errorAdminCommandServiceProducer.getErrorAdminCommandServiceDefaultInstance()
                    .createErrorResult("Произошла ошибка. Сообщение: " + ex.getMessage(), ex.getMessage());
        }
    }
}
