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

package lightsearch.server.database.repository;

import lightsearch.server.exception.RepositoryException;
import lightsearch.server.log.LoggerServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import static lightsearch.server.log.LogMessageTypeEnum.ERROR;

@Repository("lsRequestRepositoryH2")
public class LSRequestRepositoryH2Impl implements LSRequestRepository {

    @Autowired private LoggerServer logger;
    @Autowired private JdbcTemplate jdbcTemplate;
    @Autowired private LSRequestRowChecker rowChecker;

    @Override
    public void writeCommand(Object... values) throws RepositoryException {
        if(values.length != 4)
            throw new RepositoryException("LS_REQUEST required 4 values!");
        if(!(values[0] instanceof Long))
            throw new RepositoryException("LSCODE must be long!");
        if(!(values[1] instanceof String))
            throw new RepositoryException("DDOC must be string!");
        if(!(values[2] instanceof String))
            throw new RepositoryException("CMDIN must be string!");
        if(!(values[3] instanceof Boolean))
            throw new RepositoryException("STATE must be boolean!");

        try {
            long lsCode   = (long)    values[0];
            String ddoc   = (String)  values[1];
            String cmdin  = ((String) values[2]);
            boolean state = (boolean) values[3];

            if(!rowChecker.isExist(lsCode)) {
                jdbcTemplate.setQueryTimeout(30);
                jdbcTemplate.update("INSERT INTO LS_REQUEST (LSCODE, DDOC, CMDIN, STATE) VALUES (?,?,?,?)",
                        lsCode, ddoc, cmdin, state);
            } else
                throw new RepositoryException("Строка с данным LSCODE уже существует!");
        } catch (QueryTimeoutException ex) {
            logger.log(ERROR, "LSRequestRepositoryJdbcTemplateImpl: " + ex.getMessage());
            throw new RepositoryException("Время ожидания запроса истекло");
        }
    }
}
