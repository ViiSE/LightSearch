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
import lightsearch.server.initialization.OsDetector;
import lightsearch.server.log.LogMessageTypeEnum;
import lightsearch.server.log.LoggerServer;
import lightsearch.server.producer.daemon.DaemonServerCreatorProducer;
import lightsearch.server.producer.initialization.OsDetectorProducer;
import lightsearch.server.thread.ThreadManager;
import lightsearch.server.time.CurrentDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 *
 * @author ViiSE
 */
@Component("rebootServerTimerDefault")
@Scope("prototype")
public class RebootServerTimerDefault extends SuperRebootServerTimer {

    private final String ID;

    @Autowired private OsDetectorProducer osDetectorProducer;
    @Autowired private DaemonServerCreatorProducer daemonServerCreatorProducer;

    @Autowired
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
            }
            catch(InterruptedException ignored) {}

            if(super.rebootDateTime().isBefore(LocalDateTime.now())) {
                // ждем секунду
                try { Thread.sleep(1000); }
                catch(InterruptedException ignored) {}
                
                super.loggerServer().log(LogMessageTypeEnum.INFO, super.currentDateTime(), "Server restarted");

                if(super.threadManager().interruptAll(ID)) {
                    OsDetector osDetector = osDetectorProducer.getOsDetectorDefaultInstance();
                    DaemonServerCreator daemonServerCreator = daemonServerCreatorProducer.getDaemonServerCreatorDefaultInstance(osDetector, super.currentDirectory());
                    DaemonServer daemonServer = daemonServerCreator.createDaemonServer();
                    daemonServer.exec();
                }
            }
        }
        super.threadManager().holder().getThread(ID).setIsDone(true);
    }
    
}
