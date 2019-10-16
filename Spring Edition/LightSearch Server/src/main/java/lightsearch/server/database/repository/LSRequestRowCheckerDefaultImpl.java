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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component("lsRequestRowCheckerDefault")
public class LSRequestRowCheckerDefaultImpl implements LSRequestRowChecker {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @SuppressWarnings("ConstantConditions")
    @Override
    public boolean isExist(long lsCode) {
        jdbcTemplate.setQueryTimeout(30);
        int count = jdbcTemplate.queryForObject(
                "SELECT count(*) FROM LS_REQUEST WHERE lsCode = ?",
                new Object[] {lsCode}, Integer.class);

        if(count > 1)
            return true;

        return count == 1;
    }
}
