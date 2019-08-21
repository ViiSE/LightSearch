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
package lightsearch.server.timer;

import lightsearch.server.thread.LightSearchThread;

/**
 * Реализация интерфейса {@link lightsearch.server.timer.IteratorDatabaseRecordWriterTimerExecutor} по умолчанию.
 * <p>
 * Запускает таймер сборщика мусора в отдельном потоке-демоне.
 * @author ViiSE
 * @see lightsearch.server.timer.SuperIteratorDatabaseRecordWriterTimer
 * @see lightsearch.server.thread.ThreadManager
 * @see lightsearch.server.daemon.DaemonServer
 * @since 2.0
 */
public class IteratorDatabaseRecordWriterTimerExecutorDefaultImpl implements IteratorDatabaseRecordWriterTimerExecutor {

    private final SuperIteratorDatabaseRecordWriterTimer timer;
    
    public IteratorDatabaseRecordWriterTimerExecutorDefaultImpl(SuperIteratorDatabaseRecordWriterTimer timer) {
        this.timer = timer;
    }

    @Override
    public void startIteratorDatabaseRecordWriterTimer() {
        LightSearchThread thread = new LightSearchThread(timer);
        thread.setDaemon(true);
        thread.start();
        timer.threadManager().holder().add(timer.id().stringValue(), thread);
    }
    
}
