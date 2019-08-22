/* 
 * Copyright 2019 ViiSE.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package lightsearch.server.cmd.client;

import lightsearch.server.cmd.result.CommandResult;
import lightsearch.server.data.ClientDAO;
import lightsearch.server.data.LightSearchListenerDTO;
import lightsearch.server.data.LightSearchServerDTO;
import lightsearch.server.producer.cmd.client.ProcessorClientProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 *
 * @author ViiSE
 */
@Service("clientCommandCreatorDefault")
@Scope("prototype")
public class ClientCommandCreatorDefaultImpl implements ClientCommandCreator {

    private final String CONNECT                     = ClientCommandEnum.CONNECT.stringValue();
    private final String OPEN_SOFT_CHECK             = ClientCommandEnum.OPEN_SOFT_CHECK.stringValue();
    private final String CLOSE_SOFT_CHECK            = ClientCommandEnum.CLOSE_SOFT_CHECK.stringValue();
    private final String CANCEL_SOFT_CHECK           = ClientCommandEnum.CANCEL_SOFT_CHECK.stringValue();
    private final String CONFIRM_SOFT_CHECK_PRODUCTS = ClientCommandEnum.CONFIRM_SOFT_CHECK_PRODUCTS.stringValue();
    private final String SEARCH                      = ClientCommandEnum.SEARCH.stringValue();
    
    private final LightSearchServerDTO serverDTO;
    private final LightSearchListenerDTO listenerDTO;
    private final ClientDAO clientDAO;

    @Autowired
    private ProcessorClientProducer producer;

    public ClientCommandCreatorDefaultImpl(LightSearchServerDTO serverDTO, 
            LightSearchListenerDTO listenerDTO, ClientDAO clientDAO) {
        this.serverDTO = serverDTO;
        this.listenerDTO = listenerDTO;
        this.clientDAO = clientDAO;
    }

    @Override
    public Map<String, Function<ClientCommand, CommandResult>> createCommandHolder() {
        Map<String, Function<ClientCommand, CommandResult>> result = new HashMap<>();
        
        result.put(CONNECT, producer.getAuthenticationProcessorInstance(serverDTO, listenerDTO.checker(), clientDAO,
                listenerDTO.currentDateTime(), listenerDTO.identifierDatabaseRecord()));
        result.put(SEARCH,  producer.getSearchProcessorInstance(serverDTO, listenerDTO.checker(), clientDAO,
                listenerDTO.currentDateTime(), listenerDTO.identifierDatabaseRecord()));
        result.put(OPEN_SOFT_CHECK, producer.getOpenSoftCheckProcessorInstance(serverDTO, listenerDTO.checker(), clientDAO,
                listenerDTO.currentDateTime(), listenerDTO.identifierDatabaseRecord()));
        result.put(CLOSE_SOFT_CHECK, producer.getCloseSoftCheckProcessorInstance(serverDTO, listenerDTO.checker(), clientDAO,
                listenerDTO.currentDateTime(), listenerDTO.identifierDatabaseRecord()));
        result.put(CANCEL_SOFT_CHECK, producer.getCancelSoftCheckProcessorInstance(serverDTO, listenerDTO.checker(),
                clientDAO, listenerDTO.currentDateTime(), listenerDTO.identifierDatabaseRecord()));
        result.put(CONFIRM_SOFT_CHECK_PRODUCTS, producer.getConfirmSoftCheckProductsProcessorInstance(serverDTO,
                listenerDTO.checker(), clientDAO, listenerDTO.currentDateTime(), 
                listenerDTO.identifierDatabaseRecord()));
        
        return result;
    }
    
}