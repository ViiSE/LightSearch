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
package lightsearch.server.cmd.changer;

import java.time.LocalDateTime;
import lightsearch.server.iterator.IteratorDatabaseRecord;
import lightsearch.server.iterator.IteratorDatabaseRecordWriter;
import lightsearch.server.log.LoggerServer;
import lightsearch.server.thread.ThreadManager;
import lightsearch.server.time.CurrentDateTime;
import lightsearch.server.timer.IteratorDatabaseRecordWriterTimerCreator;
import lightsearch.server.timer.IteratorDatabaseRecordWriterTimerCreatorInit;
import lightsearch.server.timer.IteratorDatabaseRecordWriterTimerExecutor;
import lightsearch.server.timer.IteratorDatabaseRecordWriterTimerExecutorInit;
import lightsearch.server.timer.RebootTimerCreator;
import lightsearch.server.timer.RebootTimerCreatorInit;
import lightsearch.server.timer.RebootTimerExecutor;
import lightsearch.server.timer.RebootTimerExecutorInit;
import lightsearch.server.data.LightSearchServerDTO;
import lightsearch.server.log.LogMessageTypeEnum;
import lightsearch.server.timer.TimersIDEnum;


/**
 *
 * @author ViiSE
 */
public class ServerStateChangerDefaultImpl implements ServerStateChanger {

    private final LightSearchServerDTO serverDTO;
    private final LoggerServer logger;
    private final CurrentDateTime currentDateTime;
    private final ThreadManager threadManager;
    
    public ServerStateChangerDefaultImpl(LightSearchServerDTO serverDTO, LoggerServer logger,
            CurrentDateTime currentDateTime, ThreadManager threadManager) {
        this.serverDTO = serverDTO;
        this.logger = logger;
        this.currentDateTime = currentDateTime;
        this.threadManager = threadManager;
    }
    
    @Override
    public void executeRebootTimer(TimersIDEnum timerId) {
        LocalDateTime dateTimeReboot = LocalDateTime.now().plusHours(serverDTO.settingsDAO().serverRebootValue());
                
        RebootTimerCreator rebootTimerCreator = RebootTimerCreatorInit.rebootTimerCreator(dateTimeReboot, serverDTO.currentDirectory(),
                logger, currentDateTime, threadManager, timerId);
                
        RebootTimerExecutor rebootTimerExecutor = RebootTimerExecutorInit.rebootTimerExecutor(rebootTimerCreator.getTimer());
        rebootTimerExecutor.startRebootTimer();
    }

    @Override
    public void destroyRebootTimer(TimersIDEnum id) {
        if(threadManager.interrupt(id.stringValue()))
            logger.log(LogMessageTypeEnum.INFO, currentDateTime, "Reboot timer was destroyed");
    }

    @Override
    public void executeIteratorDatabaseRecordWriterTimer(IteratorDatabaseRecord iterator,
            IteratorDatabaseRecordWriter iteratorWriter, long minutesToWrite, TimersIDEnum timerId) {
        IteratorDatabaseRecordWriterTimerCreator iteratorTimerCreator = IteratorDatabaseRecordWriterTimerCreatorInit.iteratorDatabaseRecordWriterTimerCreator(
                logger, currentDateTime, threadManager, iteratorWriter, 
                iterator, minutesToWrite, timerId);
        IteratorDatabaseRecordWriterTimerExecutor iteratorTimerExec = 
                IteratorDatabaseRecordWriterTimerExecutorInit.iteratorDatabaseRecordWriterTimerExecutor(iteratorTimerCreator.getTimer());
        iteratorTimerExec.startIteratorDatabaseRecordWriterTimer();
    }

    @Override
    public void destroyIteratorDatabaseRecordWriterTimer(TimersIDEnum id) {
        if(threadManager.interrupt(id.stringValue()))
            logger.log(LogMessageTypeEnum.INFO, currentDateTime, "Iterator database record writer timer was destroyed");
    }
    
}
