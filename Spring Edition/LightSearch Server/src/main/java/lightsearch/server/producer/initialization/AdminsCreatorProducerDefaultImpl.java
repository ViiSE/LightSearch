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

package lightsearch.server.producer.initialization;

import lightsearch.server.data.AdminsService;
import lightsearch.server.initialization.AdminsCreator;
import lightsearch.server.initialization.CurrentServerDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service("adminsCreatorProducerDefault")
public class AdminsCreatorProducerDefaultImpl implements AdminsCreatorProducer {

    private final String ADMINS_CREATOR = "adminsCreatorFromFile";

    @Autowired
    private ApplicationContext ctx;

    @Override
    public AdminsCreator getAdminsCreatorProducerFromFileInstance(
            CurrentServerDirectory currentServerDirectory, AdminsService adminsService) {
        return (AdminsCreator) ctx.getBean(ADMINS_CREATOR, currentServerDirectory);
    }
}
