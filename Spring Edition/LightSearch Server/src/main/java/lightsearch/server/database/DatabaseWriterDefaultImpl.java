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
package lightsearch.server.database;

import lightsearch.server.database.repository.LSRequestRepository;
import lightsearch.server.exception.DatabaseWriterException;
import lightsearch.server.exception.RepositoryException;
import lightsearch.server.producer.time.CurrentDateTimeProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author ViiSE
 */
@Component("databaseWriterDefault")
@Scope("prototype")
public class DatabaseWriterDefaultImpl implements DatabaseWriter {

    private final long lsCode;
    private final String dateTime;
    private final String command;

    @Autowired
    private LSRequestRepository lsRequestRepository;

    public DatabaseWriterDefaultImpl(long lsCode, String dateTime, String command) {
        this.lsCode = lsCode;
        this.dateTime = dateTime;
        this.command = command;
    }

    @Override
    public void write() throws DatabaseWriterException {
        try {
            lsRequestRepository.writeCommand(lsCode, dateTime, command, true);
        } catch (RepositoryException ex) {
            throw new DatabaseWriterException(ex.getMessage(),
                    "Не удалось записать команду в базу данных. Сообщение: " + ex.getMessage());
        }
    }
    
}
