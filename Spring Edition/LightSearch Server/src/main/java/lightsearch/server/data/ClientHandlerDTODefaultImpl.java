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
package lightsearch.server.data;

import lightsearch.server.thread.ThreadManager;
import lightsearch.server.time.CurrentDateTime;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author ViiSE
 */
@Component("clientHandlerDTODefault")
@Scope("prototype")
public class ClientHandlerDTODefaultImpl implements ClientHandlerDTO {

    private final ClientParametersHolder clientParametersHolder;
    private final ThreadParametersHolder threadParametersHolder;
    private final CurrentDateTime currentDateTime;
    private final ThreadManager threadManager;
    private final ClientDAO clientDAO;
    
    public ClientHandlerDTODefaultImpl(ClientParametersHolder clientParametersHolder, 
            ThreadParametersHolder threadParametersHolder, CurrentDateTime currentDateTime, 
            ThreadManager threadManager, ClientDAO clientDAO) {
        this.clientParametersHolder = clientParametersHolder;
        this.threadParametersHolder = threadParametersHolder;
        this.currentDateTime = currentDateTime;
        this.threadManager = threadManager;
        this.clientDAO = clientDAO;
    }

    @Override
    public ClientParametersHolder clientParametersHolder() {
        return clientParametersHolder;
    }

    @Override
    public ThreadParametersHolder threadParametersHolder() {
        return threadParametersHolder;
    }

    @Override
    public CurrentDateTime currentDateTime() {
        return currentDateTime;
    }

    @Override
    public ThreadManager threadManager() {
        return threadManager;
    }

    @Override
    public ClientDAO clientDAO() {
        return clientDAO;
    }
    
}
