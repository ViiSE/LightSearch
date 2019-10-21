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

import lightsearch.server.data.pojo.ClientCommandResult;
import lightsearch.server.log.LoggerServer;
import lightsearch.server.producer.cmd.result.ClientCommandResultCreatorProducer;
import lightsearch.server.producer.cmd.result.ErrorResultClientCommandCreatorProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static lightsearch.server.log.LogMessageTypeEnum.ERROR;

@Service("errorClientCommandServiceDefault")
class ErrorClientCommandServiceDefaultImpl implements ErrorClientCommandService {

    @Autowired private LoggerServer logger;
    @Autowired private ErrorResultClientCommandCreatorProducer errorResultClientCommandCreatorProducer;
    @Autowired private ClientCommandResultCreatorProducer clientCommandResultCreatorProducer;

    public ClientCommandResult createErrorResult(String IMEI, String message, String logMessage) {
        logger.log(ERROR, logMessage);
        return errorResultClientCommandCreatorProducer
                .getErrorResultClientCommandCreatorDefaultInstance(IMEI, message, clientCommandResultCreatorProducer)
                .createErrorResult();
    }
}
