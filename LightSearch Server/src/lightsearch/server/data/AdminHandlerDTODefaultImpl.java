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

import lightsearch.server.thread.ThreadManager;
import lightsearch.server.time.CurrentDateTime;

/**
 *
 * @author ViiSE
 */
public class AdminHandlerDTODefaultImpl implements AdminHandlerDTO {

    private final AdminParametersHolder adminParametersHolder;
    private final ThreadParametersHolder threadParametersHolder;
    private final CurrentDateTime currentDateTime;
    private final ThreadManager threadManager;
    private final AdminDAO adminDAO;
    
    public AdminHandlerDTODefaultImpl(AdminParametersHolder adminParametersHolder, 
            ThreadParametersHolder threadParametersHolder, CurrentDateTime currentDateTime, 
            ThreadManager threadManager, AdminDAO adminDAO) {
        this.adminParametersHolder = adminParametersHolder;
        this.threadParametersHolder = threadParametersHolder;
        this.currentDateTime = currentDateTime;
        this.threadManager = threadManager;
        this.adminDAO = adminDAO;
    }

    @Override
    public AdminParametersHolder adminParametersHolder() {
        return adminParametersHolder;
    }

    @Override
    public ThreadParametersHolder threadParametersHolder() {
        return threadParametersHolder;
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
    public AdminDAO adminDAO() {
        return adminDAO;
    }
    
}
