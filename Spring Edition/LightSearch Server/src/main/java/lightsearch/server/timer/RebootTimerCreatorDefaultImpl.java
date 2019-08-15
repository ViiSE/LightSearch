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

import lightsearch.server.log.LoggerServer;
import lightsearch.server.producer.timer.RebootServerTimerProducer;
import lightsearch.server.thread.ThreadManager;
import lightsearch.server.time.CurrentDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 *
 * @author ViiSE
 */
@Service("rebootTimerCreatorDefault")
@Scope("prototype")
public class RebootTimerCreatorDefaultImpl implements RebootTimerCreator {

    private final LocalDateTime serverDateRebootValue;
    private final String currentDirectory;
    private final LoggerServer loggerServer;
    private final CurrentDateTime currentDateTime;
    private final ThreadManager threadManager;
    private final TimersIDEnum id;

    @Autowired
    RebootServerTimerProducer producer;

    @Autowired
    RebootTimerCreatorDefaultImpl(LocalDateTime serverDateTimeRebootValue, String currentDirectory, 
            LoggerServer loggerServer, CurrentDateTime currentDateTime, ThreadManager threadManager, TimersIDEnum id) {
        this.serverDateRebootValue = serverDateTimeRebootValue;
        this.currentDirectory = currentDirectory;
        this.loggerServer = loggerServer;
        this.currentDateTime = currentDateTime;
        this.threadManager = threadManager;
        this.id = id;
    }

    @Override
    public SuperRebootServerTimer getTimer() {
        SuperRebootServerTimer serverTimer = producer.getRebootServerTimerDefaultInstance(
                serverDateRebootValue, currentDirectory, loggerServer, currentDateTime, threadManager, id);
        return serverTimer;
    }
    
}
