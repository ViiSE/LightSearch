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
package lightsearch.server.handler;

import lightsearch.server.thread.LightSearchThreadInit;
import lightsearch.server.thread.ThreadManager;

/**
 * Реализация интерфейса {@link lightsearch.server.handler.HandlerExecutor} по умолчанию.
 * <p>
 * Запускает обработчик клиента LightSearch в отдельном потоке.
 * @author ViiSE
 * @since 2.0
 */
public class HandlerExecutorDefaultImpl implements HandlerExecutor {

    private final ThreadManager threadManager;
    
    public HandlerExecutorDefaultImpl(ThreadManager threadManager) {
        this.threadManager = threadManager;
    }

    @Override
    public void executeHandler(Handler handler) {
        if(handler != null) {
            threadManager.holder().add(handler.threadParametersHolder().id(), LightSearchThreadInit.lightSearchThread(handler));
            threadManager.holder().getThread(handler.threadParametersHolder().id()).start();
        }
    }
    
}
