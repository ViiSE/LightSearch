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

import lightsearch.server.data.pojo.ResponseResult;
import lightsearch.server.exception.RepositoryException;
import lightsearch.server.log.LoggerServer;
import lightsearch.server.producer.time.DateTimeComparatorProducer;
import lightsearch.server.time.CurrentDateTime;
import lightsearch.server.time.CurrentDateTimePattern;
import lightsearch.server.time.DateTimeComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static lightsearch.server.log.LogMessageTypeEnum.ERROR;

@Repository("lsResponseRepositoryH2")
public class LSResponseRepositoryH2Impl implements LSResponseRepository {

    private final String pattern = CurrentDateTimePattern.dateTimeInStandardFormWithMs();

    @Autowired private LoggerServer logger;
    @Autowired private CurrentDateTime currentDateTime;
    @Autowired private JdbcTemplate jdbcTemplate;
    @Autowired private DateTimeComparatorProducer dateTimeComparatorProducer;

    @Override
    public ResponseResult readResult(Object... values) throws RepositoryException {
        if (values.length != 2)
            throw new RepositoryException("LS_RESPONSE required 2 values!");
        if (!(values[0] instanceof Long))
            throw new RepositoryException("LSCODE must be long!");
        if (!(values[1] instanceof Boolean))
            throw new RepositoryException("STATE must be boolean!");

        long lsCode = (long) values[0];
        boolean state = (boolean) values[1];

        LocalDateTime dateTimeStop = LocalDateTime.now().plusSeconds(30);
        DateTimeComparator dateTimeComparator = dateTimeComparatorProducer.getDateTimeComparatorDefaultInstance(pattern);

        jdbcTemplate.setQueryTimeout(30);
        try {
            while (dateTimeStop.isAfter(LocalDateTime.now())) {
                try {
                    ResponseResult result = jdbcTemplate.queryForObject(
                            "SELECT LSCODE, DDOC, CMDOUT, STATE FROM LS_RESPONSE WHERE (LSCODE = ?) AND (STATE = ?)",
                            new Object[]{lsCode, state},
                            new ResponseResultRowMapper());
                    LocalDateTime nowMax = LocalDateTime.now().with(LocalTime.MAX);
                    LocalDateTime nowMin = LocalDateTime.now().with(LocalTime.MIN);
                    if (result != null)
                        if (dateTimeComparator.isBefore(result.getDdoc(), nowMax)
                                && dateTimeComparator.isAfter(result.getDdoc(), nowMin))
                            return result;
                } catch(DataAccessException ex) {
                    if(ex.getMessage() != null)
                        if(!ex.getMessage().contains("Incorrect result size")) {
                            logger.log(LSResponseRepositoryH2Impl.class, ERROR, ex.getMessage());
                            throw new RepositoryException("Произошла ошибка на сервере. Сообщение: " + ex.getLocalizedMessage());
                        }
                }
            }
            logger.log(LSResponseRepositoryH2Impl.class, ERROR, "Время ожидания запроса истекло");
            throw new RepositoryException("Request timed out");
        } catch (QueryTimeoutException ex) {
            logger.log(LSResponseRepositoryH2Impl.class, ERROR, ex.getMessage());
            throw new RepositoryException("Время ожидания запроса истекло");
        } catch (DataAccessException ex) {
            logger.log(LSResponseRepositoryH2Impl.class, ERROR, ex.getMessage());
            throw new RepositoryException("Произошла ошибка на сервере. Сообщение: " + ex.getLocalizedMessage());
        }
    }

    @Override
    public void updateResultRecord(Object... values) throws RepositoryException {
        if(values.length != 2)
            throw new RepositoryException("LS_RESPONSE required 2 values!");
        if(!(values[0] instanceof Long))
            throw new RepositoryException("LSCODE must be long!");
        if(!(values[1] instanceof Boolean))
            throw new RepositoryException("STATE must be boolean!");

        try {
            long lsCode   = (long)    values[0];
            boolean state = (boolean) values[1];

            jdbcTemplate.setQueryTimeout(30);
            jdbcTemplate.update("UPDATE LS_RESPONSE SET STATE = ? WHERE LSCODE = ?", state, lsCode);
        } catch (QueryTimeoutException ex) {
            logger.log(LSResponseRepositoryH2Impl.class, ERROR, ex.getMessage());
            throw new RepositoryException("Время ожидания запроса истекло");
        }
    }
}
