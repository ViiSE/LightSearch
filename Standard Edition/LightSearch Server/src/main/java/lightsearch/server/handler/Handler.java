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

import lightsearch.server.data.ThreadParametersHolder;
import lightsearch.server.log.LoggerServer;
import lightsearch.server.thread.ThreadManager;
import lightsearch.server.time.CurrentDateTime;
import lightsearch.server.data.LightSearchServerDTO;

/**
 * Абстрактный класс обработчика клиента LightSearch Server.
 * <p>
 * Для каждого клиента LightSearch Server создается обработчик. Обработчик принимает команды от клиента и
 * вызывает соответствующий обработчик команды, возвращая результат клиенту.
 * <p>
 * Обработчик следит за завершением работы клиента и завершением работы потока, а также проверяет команды на соблюдения
 * протокола.
 * @author ViiSE
 * @see lightsearch.server.data.ThreadParametersHolder
 * @see lightsearch.server.data.LightSearchServerDTO
 * @see lightsearch.server.log.LoggerServer
 * @see lightsearch.server.data.LightSearchServerDTO
 * @see lightsearch.server.time.CurrentDateTime
 * @see lightsearch.server.thread.ThreadManager
 * @since 2.0
 */
public abstract class Handler implements Runnable {
    
    private final ThreadParametersHolder threadParametersHolder;
    private final LightSearchServerDTO serverDTO;
    private final LoggerServer logger;
    private final CurrentDateTime currentDateTime;
    private final ThreadManager threadManager;
    
    public Handler(LightSearchServerDTO serverDTO, ThreadParametersHolder threadParametersHolder,
            LoggerServer logger, CurrentDateTime currentDateTime, ThreadManager threadManager) {
        this.serverDTO = serverDTO;
        this.threadParametersHolder = threadParametersHolder;
        this.logger = logger;
        this.currentDateTime = currentDateTime;
        this.threadManager = threadManager;
    }
        
    public LightSearchServerDTO serverDTO() {
        return serverDTO;
    }
    
    public ThreadParametersHolder threadParametersHolder() {
        return threadParametersHolder;
    }
    
    public LoggerServer logger() {
        return logger;
    }
    
    public CurrentDateTime currentDateTime() {
        return currentDateTime;
    }
    
    public ThreadManager threadManager() {
        return threadManager;
    }
}
