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
package lightsearch.server.data;

import lightsearch.server.checker.LightSearchChecker;
import lightsearch.server.identifier.DatabaseRecordIdentifier;
import lightsearch.server.identifier.DatabaseRecordIdentifierWriter;
import lightsearch.server.thread.ThreadManager;
import lightsearch.server.time.CurrentDateTime;
import lightsearch.server.timer.TimersIDEnum;

/**
 * Реализация интерфейса {@link lightsearch.server.data.LightSearchListenerDTO} по умолчанию.
 * @author ViiSE
 * @since 2.0.0
 */
public class LightSearchListenerDTODefaultImpl implements LightSearchListenerDTO {

    private final LightSearchChecker checker;
    private final CurrentDateTime currentDateTime;
    private final ThreadManager threadManager;
    private final DatabaseRecordIdentifier databaseRecordIdentifier;
    private final DatabaseRecordIdentifierWriter databaseRecordIdentifierWriter;
    private final TimersIDEnum timerRebootId;
    
    public LightSearchListenerDTODefaultImpl(LightSearchChecker checker, CurrentDateTime currentDateTime,
                                             ThreadManager threadManager, DatabaseRecordIdentifier databaseRecordIdentifier,
                                             DatabaseRecordIdentifierWriter databaseRecordIdentifierWriter, TimersIDEnum timerRebootId) {
        this.checker = checker;
        this.currentDateTime = currentDateTime;
        this.threadManager = threadManager;
        this.databaseRecordIdentifier = databaseRecordIdentifier;
        this.databaseRecordIdentifierWriter = databaseRecordIdentifierWriter;
        this.timerRebootId = timerRebootId;
    }

    @Override
    public LightSearchChecker checker() {
        return checker;
    }

    @Override
    public CurrentDateTime currentDateTime() {
        return currentDateTime;
    }

    @Override
    public ThreadManager threadManager() {
        return threadManager;
    }

    @Override
    public DatabaseRecordIdentifier databaseRecordIdentifier() {
        return databaseRecordIdentifier;
    }

    @Override
    public DatabaseRecordIdentifierWriter databaseRecordIdentifierWriter() {
        return databaseRecordIdentifierWriter;
    }

    @Override
    public TimersIDEnum timerRebootId() {
        return timerRebootId;
    }
    
}
