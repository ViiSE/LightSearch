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

import lightsearch.server.data.pojo.ResponseResult;
import lightsearch.server.database.repository.LSResponseRepository;
import lightsearch.server.exception.DatabaseReaderException;
import lightsearch.server.exception.RepositoryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author ViiSE
 */
@Component("databaseReaderDefault")
@Scope("prototype")
public class DatabaseReaderDefaultImpl implements DatabaseReader {

    private final long lsCode;

    @Autowired
    @Qualifier("lsResponseRepositoryFirebird")
    private LSResponseRepository lsResponseRepository;

    public DatabaseReaderDefaultImpl(long lsCode) {
        this.lsCode = lsCode;
    }
    
    @Override
    public String read() throws DatabaseReaderException {
        ResponseResult result = null;
        try {
            result = lsResponseRepository.readResult(lsCode, false);
            lsResponseRepository.updateResultRecord(lsCode, true);
            return result.getCmdOut();
        } catch (RepositoryException ex) {
            throw new DatabaseReaderException(ex.getMessage(),
                    "Не удалось считать результат команды из базы данных. Сообщение: " + ex.getMessage());
        }
    }
}
