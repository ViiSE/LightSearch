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

import lightsearch.server.data.IteratorDatabaseRecordWriterTimerCreatorDTO;
import lightsearch.server.data.LightSearchServerDTO;
import lightsearch.server.log.LogMessageTypeEnum;
import lightsearch.server.log.LoggerServer;
import lightsearch.server.producer.timer.*;
import lightsearch.server.thread.ThreadManager;
import lightsearch.server.time.CurrentDateTime;
import lightsearch.server.timer.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 *
 * @author ViiSE
 */
@Service("serverStateChangerDefault")
@Scope("prototype")
public class ServerStateChangerDefaultImpl implements ServerStateChanger {

    private final LightSearchServerDTO serverDTO;
    private final LoggerServer logger;
    private final CurrentDateTime currentDateTime;
    private final ThreadManager threadManager;

    @Autowired private RebootTimerCreatorProducer timerCrProducer;
    @Autowired private RebootTimerExecutorProducer timerExecProducer;
    @Autowired private IteratorDatabaseRecordWriterTimerCreatorProducer iteratorDbRecWriterTimerCrProducer;
    @Autowired private IteratorDatabaseRecordWriterTimerExecutorProducer iteratorDbRecWriterTimerExecProducer;
    @Autowired private GarbageCollectorTimerCreatorProducer gcTimerCrProducer;
    @Autowired private GarbageCollectorTimerExecutorProducer gcTimerExecProducer;


    @Autowired
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
                
        RebootTimerCreator rebootTimerCreator = timerCrProducer.getRebootTimerCreatorDefaultInstance(
                dateTimeReboot, serverDTO.currentDirectory(), logger, currentDateTime, threadManager, timerId);
                
        RebootTimerExecutor rebootTimerExecutor = timerExecProducer.getRebootTimerExecutorDefaultInstance(rebootTimerCreator.getTimer());
        rebootTimerExecutor.startRebootTimer();
    }

    @Override
    public void destroyRebootTimer(TimersIDEnum id) {
        if(threadManager.interrupt(id.stringValue()))
            logger.log(LogMessageTypeEnum.INFO, currentDateTime, "Reboot timer was destroyed");
    }

    @Override
    public void executeIteratorDatabaseRecordWriterTimer(IteratorDatabaseRecordWriterTimerCreatorDTO iteratorDbRecWriterTimerCrDTO) {
        IteratorDatabaseRecordWriterTimerCreator iteratorTimerCreator =
                iteratorDbRecWriterTimerCrProducer.getIteratorDatabaseRecordWriterTimerCreatorDefaultInstance(
                        logger, currentDateTime, threadManager, iteratorDbRecWriterTimerCrDTO);
        IteratorDatabaseRecordWriterTimerExecutor iteratorTimerExec =
                iteratorDbRecWriterTimerExecProducer.getIteratorDatabaseRecordWriterTimerExecutorDefaultInstance(iteratorTimerCreator.getTimer());
        iteratorTimerExec.startIteratorDatabaseRecordWriterTimer();
    }

    @Override
    public void destroyIteratorDatabaseRecordWriterTimer(TimersIDEnum id) {
        if(threadManager.interrupt(id.stringValue()))
            logger.log(LogMessageTypeEnum.INFO, currentDateTime, "Iterator database record writer timer was destroyed");
    }

    @Override
    public void executeGarbageCollectorTimer(TimersIDEnum id) {
        GarbageCollectorTimerCreator garbageTimerCreator =
                gcTimerCrProducer.getGarbageCollectorTimerCreatorDefaultInstance(logger, currentDateTime, threadManager, id);
        gcTimerExecProducer.getGarbageCollectorTimerExecutorDefaultInstance(garbageTimerCreator.getTimer()).startGarbageCollectorTimerExecutor();
    }

    @Override
    public void destroyGarbageCollectorTimer(TimersIDEnum id) {
        if(threadManager.interrupt(id.stringValue()))
            logger.log(LogMessageTypeEnum.INFO, currentDateTime, "Garbage collector timer was destroyed");
    }
    
}
