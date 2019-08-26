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

import lightsearch.server.identifier.DatabaseRecordIdentifier;
import lightsearch.server.identifier.DatabaseRecordIdentifierWriter;
import lightsearch.server.log.LoggerServer;
import lightsearch.server.thread.ThreadManager;
import lightsearch.server.time.CurrentDateTime;
import lightsearch.server.timer.DatabaseRecordIdentifierWriterTimerCreator;
import lightsearch.server.timer.DatabaseRecordIdentifierWriterTimerCreatorInit;
import lightsearch.server.timer.DatabaseRecordIdentifierWriterTimerExecutor;
import lightsearch.server.timer.DatabaseRecordIdentifierWriterTimerExecutorInit;
import lightsearch.server.timer.RebootTimerCreator;
import lightsearch.server.timer.RebootTimerCreatorInit;
import lightsearch.server.timer.RebootTimerExecutor;
import lightsearch.server.timer.RebootTimerExecutorInit;
import lightsearch.server.data.LightSearchServerDTO;
import lightsearch.server.log.LogMessageTypeEnum;
import lightsearch.server.timer.GarbageCollectorTimerCreator;
import lightsearch.server.timer.GarbageCollectorTimerCreatorInit;
import lightsearch.server.timer.GarbageCollectorTimerExecutorInit;
import lightsearch.server.timer.TimersIDEnum;


/**
 * Реализация интерфейса {@link lightsearch.server.cmd.changer.ServerStateChanger} по умолчанию.
 * @author ViiSE
 * @since 2.0
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
    public void executeDatabaseRecordIdentifierWriterTimer(DatabaseRecordIdentifier identifier,
                                                           DatabaseRecordIdentifierWriter identifierWriter, long minutesToWrite, TimersIDEnum timerId) {
        DatabaseRecordIdentifierWriterTimerCreator identifierTimerCreator = DatabaseRecordIdentifierWriterTimerCreatorInit.databaseRecordIdentifierWriterTimerCreator(
                logger, currentDateTime, threadManager, identifierWriter,
                identifier, minutesToWrite, timerId);
        DatabaseRecordIdentifierWriterTimerExecutor identifierTimerExec =
                DatabaseRecordIdentifierWriterTimerExecutorInit.databaseRecordIdentifierWriterTimerExecutor(identifierTimerCreator.getTimer());
        identifierTimerExec.startDatabaseRecordIdentifierWriterTimer();
    }

    @Override
    public void destroyDatabaseRecordIdentifierWriterTimer(TimersIDEnum id) {
        if(threadManager.interrupt(id.stringValue()))
            logger.log(LogMessageTypeEnum.INFO, currentDateTime, "Identifier database record writer timer was destroyed");
    }

    @Override
    public void executeGarbageCollectorTimer(TimersIDEnum id) {
        GarbageCollectorTimerCreator garbageTimerCreator = GarbageCollectorTimerCreatorInit.garbageCollectorTimerCreator(logger, currentDateTime, threadManager, id);
        GarbageCollectorTimerExecutorInit.garbageCollectorTimerExecutor(garbageTimerCreator.getTimer())
                        .startGarbageCollectorTimerExecutor();
    }

    @Override
    public void destroyGarbageCollectorTimer(TimersIDEnum id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
