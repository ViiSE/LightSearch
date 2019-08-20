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

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import lightsearch.server.cmd.client.processor.AuthenticationProcessor;
import lightsearch.server.cmd.client.processor.CancelSoftCheckProcessor;
import lightsearch.server.cmd.client.processor.CloseSoftCheckProcessor;
import lightsearch.server.cmd.client.processor.ConfirmSoftCheckProductsProcessor;
import lightsearch.server.cmd.client.processor.OpenSoftCheckProcessor;
import lightsearch.server.cmd.client.processor.SearchProcessor;
import lightsearch.server.cmd.result.CommandResult;
import lightsearch.server.data.ClientDAO;
import lightsearch.server.data.LightSearchListenerDTO;
import lightsearch.server.data.LightSearchServerDTO;

/**
 *
 * @author ViiSE
 */
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
    
    public ClientCommandCreatorDefaultImpl(LightSearchServerDTO serverDTO, 
            LightSearchListenerDTO listenerDTO, ClientDAO clientDAO) {
        this.serverDTO = serverDTO;
        this.listenerDTO = listenerDTO;
        this.clientDAO = clientDAO;
    }

    @Override
    public Map<String, Function<ClientCommand, CommandResult>> createCommandHolder() {
        Map<String, Function<ClientCommand, CommandResult>> result = new HashMap<>();
        
        result.put(CONNECT, new AuthenticationProcessor(serverDTO, listenerDTO.checker(), clientDAO, 
                listenerDTO.currentDateTime(), listenerDTO.iteratorDatabaseRecord()));
        result.put(SEARCH,  new SearchProcessor(serverDTO, listenerDTO.checker(), clientDAO, 
                listenerDTO.currentDateTime(), listenerDTO.iteratorDatabaseRecord()));
        result.put(OPEN_SOFT_CHECK, new OpenSoftCheckProcessor(serverDTO, listenerDTO.checker(), clientDAO, 
                listenerDTO.currentDateTime(), listenerDTO.iteratorDatabaseRecord()));
        result.put(CLOSE_SOFT_CHECK, new CloseSoftCheckProcessor(serverDTO, listenerDTO.checker(), clientDAO, 
                listenerDTO.currentDateTime(), listenerDTO.iteratorDatabaseRecord()));
        result.put(CANCEL_SOFT_CHECK, new CancelSoftCheckProcessor(serverDTO, listenerDTO.checker(), 
                clientDAO, listenerDTO.currentDateTime(), listenerDTO.iteratorDatabaseRecord()));
        result.put(CONFIRM_SOFT_CHECK_PRODUCTS, new ConfirmSoftCheckProductsProcessor(serverDTO, 
                listenerDTO.checker(), clientDAO, listenerDTO.currentDateTime(), 
                listenerDTO.iteratorDatabaseRecord()));
        
        return result;
    }
    
}