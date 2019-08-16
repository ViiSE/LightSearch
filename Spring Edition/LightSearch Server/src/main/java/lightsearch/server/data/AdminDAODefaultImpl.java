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

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author ViiSE
 */
@Component("adminDAODefault")
@Scope("prototype")
public class AdminDAODefaultImpl implements AdminDAO {

    private String name;
    private boolean isFirst = true;
    private final short MAX_TRY = 3;
    private short tryNumber = 0;
    
    @Override
    public String name() {
        return name;
    }

    @Override
    public boolean isFirst() {
        return isFirst;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setIsFirst(boolean isFirst) {
        this.isFirst = isFirst;
    }

    @Override
    public short tryNumber() {
        return tryNumber;
    }

    @Override
    public void iterateTryNumber() {
        ++tryNumber;
    }

    @Override
    public short maxTryNumber() {
        return MAX_TRY;
    }
}
