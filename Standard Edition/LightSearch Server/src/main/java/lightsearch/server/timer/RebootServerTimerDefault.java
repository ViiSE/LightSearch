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

import lightsearch.server.daemon.DaemonServer;
import lightsearch.server.daemon.DaemonServerCreator;
import lightsearch.server.daemon.DaemonServerCreatorInit;
import lightsearch.server.initialization.OsDetector;
import lightsearch.server.initialization.OsDetectorInit;
import lightsearch.server.log.LogMessageTypeEnum;
import lightsearch.server.log.LoggerServer;
import lightsearch.server.thread.ThreadManager;
import lightsearch.server.time.CurrentDateTime;

import java.time.LocalDateTime;

/**
 * Таймер перезагрузки LightSearch Server по умолчанию.
 * <p>
 * Время перезагрузки является экземпляром класса {@link java.time.LocalDateTime}. Это время сравнивается с текущим,
 * и если время перезагрузки до текущего времени, то таймер ждет секунду, завершает все активные потоки
 * LightSearch Server, и вызывает демона, который перезагружает LightSearch Server.
 * <p>
 * Каждую секунду проходит проверка значения времени перезагрузки LightSearch Server с значением текущего времени.
 * @author ViiSE
 * @see lightsearch.server.thread.ThreadManager
 * @see lightsearch.server.daemon.DaemonServer
 * @since 2.0.0
 */
public class RebootServerTimerDefault extends SuperRebootServerTimer {

    private final String ID;
    
    public RebootServerTimerDefault(LocalDateTime rebootDateTime, String currentDirectory,
            LoggerServer loggerServer, CurrentDateTime currentDateTime,
            ThreadManager threadManager, TimersIDEnum id) {
        super(rebootDateTime, currentDirectory, loggerServer, currentDateTime, threadManager, id);
        ID = super.id().stringValue();
    }
    
    @Override
    public void run() {
        while(super.threadManager().holder().getThread(ID).isWorked()) {
            try { Thread.sleep(1000); } catch(InterruptedException ignored) {}

            if(super.rebootDateTime().isBefore(LocalDateTime.now())) {
                try { Thread.sleep(1000); } catch(InterruptedException ignored) {}
                
                super.loggerServer().log(LogMessageTypeEnum.INFO, super.currentDateTime(), "Server restarted");

                if(super.threadManager().interruptAll(ID)) {
                    OsDetector osDetector = OsDetectorInit.osDetector();
                    DaemonServerCreator daemonServerCreator = DaemonServerCreatorInit.daemonServerCreator(osDetector, super.currentDirectory());
                    DaemonServer daemonServer = daemonServerCreator.createDaemonServer();
                    daemonServer.exec();
                }
            }
        }
        super.threadManager().holder().getThread(ID).setIsDone(true);
    }
    
}
