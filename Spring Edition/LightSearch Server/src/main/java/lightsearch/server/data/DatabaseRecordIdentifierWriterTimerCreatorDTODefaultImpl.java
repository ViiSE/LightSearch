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

import lightsearch.server.identifier.DatabaseRecordIdentifier;
import lightsearch.server.identifier.DatabaseRecordIdentifierWriter;
import lightsearch.server.timer.TimersIDEnum;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("databaseRecordIdentifierWriterTimerCreatorDTODefault")
@Scope("prototype")
public class DatabaseRecordIdentifierWriterTimerCreatorDTODefaultImpl implements DatabaseRecordIdentifierWriterTimerCreatorDTO {

    private final DatabaseRecordIdentifierWriter databaseRecordIdentifierWriter;
    private final DatabaseRecordIdentifier databaseRecordIdentifier;
    private final long minutesToWrite;
    private final TimersIDEnum id;

    public DatabaseRecordIdentifierWriterTimerCreatorDTODefaultImpl(
            DatabaseRecordIdentifier databaseRecordIdentifier, DatabaseRecordIdentifierWriter databaseRecordIdentifierWriter,
            long minutesToWrite, TimersIDEnum id) {
        this.databaseRecordIdentifierWriter = databaseRecordIdentifierWriter;
        this.databaseRecordIdentifier = databaseRecordIdentifier;
        if(minutesToWrite < 0)
            this.minutesToWrite = 0;
        else
            this.minutesToWrite = minutesToWrite;
        this.id = id;
    }

    @Override
    public DatabaseRecordIdentifierWriter databaseRecordIdentifierWriter() {
        return databaseRecordIdentifierWriter;
    }

    @Override
    public DatabaseRecordIdentifier databaseRecordIdentifier() {
        return databaseRecordIdentifier;
    }

    @Override
    public long minutesToWrite() {
        return minutesToWrite;
    }

    @Override
    public TimersIDEnum id() {
        return id;
    }
}
