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

package lightsearch.server.producer.data;

import lightsearch.server.cmd.admin.AdminCommand;
import lightsearch.server.cmd.result.CommandResult;
import lightsearch.server.data.AdminParametersHolder;
import lightsearch.server.data.stream.DataStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.net.Socket;
import java.util.Map;
import java.util.function.Function;

@Service("adminParametersHolderProducerDefault")
public class AdminParametersHolderProducerDefaultImpl implements AdminParametersHolderProducer {

    private final String ADMIN_PARAMETERS_HOLDER = "adminParametersHolderDefault";

    @Autowired
    private ApplicationContext ctx;

    @Override
    public AdminParametersHolder getAdminParametersHolderDefaultInstance(
            Socket adminSocket, DataStream dataStream, Map<String, Function<AdminCommand, CommandResult>> commandHolder) {
        return (AdminParametersHolder) ctx.getBean(ADMIN_PARAMETERS_HOLDER, adminSocket, dataStream, commandHolder);
    }
}
