/*
 *  Copyright 2019 ViiSE.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package lightsearch.server.producer.identifier;

import lightsearch.server.data.LightSearchServerService;
import lightsearch.server.identifier.DatabaseRecordIdentifierWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service("databaseRecordIdentifierWriterProducerDefault")
public class DatabaseRecordIdentifierWriterProducerDefaultImpl implements DatabaseRecordIdentifierWriterProducer {

    private final String DATABASE_RECORD_IDENT_WRITER = "databaseRecordIdentifierWriterDefault";

    @Autowired private LightSearchServerService serverService;
    @Autowired private ApplicationContext ctx;

    @Override
    public DatabaseRecordIdentifierWriter getDatabaseRecordIdentifierWriterDefaultInstance() {
        return (DatabaseRecordIdentifierWriter) ctx.getBean(DATABASE_RECORD_IDENT_WRITER, serverService);
    }
}
