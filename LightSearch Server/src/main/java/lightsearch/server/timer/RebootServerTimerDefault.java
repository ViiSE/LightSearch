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

import java.time.LocalDateTime;
import lightsearch.server.daemon.DaemonServer;
import lightsearch.server.daemon.DaemonServerCreator;
import lightsearch.server.daemon.DaemonServerCreatorInit;
import lightsearch.server.initialization.OsDetector;
import lightsearch.server.initialization.OsDetectorInit;
import lightsearch.server.log.LogMessageTypeEnum;
import lightsearch.server.thread.ThreadManager;
import lightsearch.server.log.LoggerServer;
import lightsearch.server.time.CurrentDateTime;

/**
 *
 * @author ViiSE
 */
public class RebootServerTimerDefault extends SuperRebootServerTimer {

    private final String ID;
    
    public RebootServerTimerDefault(LocalDateTime rebootDateTime, String currentDirectory,
            LoggerServer loggerServer, CurrentDateTime currentDateTime,
            ThreadManager threadManager, TimersIDEnum id) {
        super(rebootDateTime, currentDirectory, loggerServer, currentDateTime, 
                threadManager, id);
        ID = super.id().stringValue();
    }
    
    @Override
    public void run() {
        while(super.threadManager().holder().getThread(ID).isWorked()) {
            try { 
                Thread.sleep(1000); 
//                System.out.println("I am RebootTimer! My id: " + super.id() + 
//                        " time is: " + LocalDateTime.now() + 
//                        ". The time, when i must restart server: " + super.rebootDateTime());
            }
            catch(InterruptedException ignored) {}

            if(super.rebootDateTime().isBefore(LocalDateTime.now())) {
                // ждем секунду
                try { Thread.sleep(1000); }
                catch(InterruptedException ignored) {}
                
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
