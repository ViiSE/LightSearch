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

/**
 * Реализация интерфейса {@link lightsearch.server.data.SystemHandlerDTO} по умолчанию.
 * @author ViiSE
 * @since 2.0
 */
public class SystemHandlerDTODefaultImpl implements SystemHandlerDTO {

    private final SystemParametersHolder systemParametersHolder;
    private final ThreadParametersHolder threadParametersHolder;
    private final ThreadManager threadManager;
    private final CurrentDateTime currentDateTime;
    
    public SystemHandlerDTODefaultImpl(SystemParametersHolder systemParametersHolder, 
            ThreadParametersHolder threadParametersHolder, ThreadManager threadManager,
            CurrentDateTime currentDateTime) {
        this.systemParametersHolder = systemParametersHolder;
        this.threadParametersHolder = threadParametersHolder;
        this.threadManager = threadManager;
        this.currentDateTime = currentDateTime;
    }

    @Override
    public SystemParametersHolder systemParametersHolder() {
        return systemParametersHolder;
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
    
}
