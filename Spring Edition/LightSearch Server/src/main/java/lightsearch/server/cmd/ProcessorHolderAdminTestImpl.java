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

package lightsearch.server.cmd;

import lightsearch.server.checker.LightSearchChecker;
import lightsearch.server.cmd.admin.processor.AdminProcessor;
import lightsearch.server.data.LightSearchServerService;
import lightsearch.server.producer.checker.LightSearchCheckerProducer;
import lightsearch.server.producer.cmd.admin.processor.ProcessorAdminProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static lightsearch.server.cmd.admin.AdminCommandEnum.*;

@Component("adminProcessorHolderTest")
@Scope("prototype")
public class ProcessorHolderAdminTestImpl implements ProcessorHolder {

    private final Map<String, AdminProcessor> holder = new HashMap<>();
    private final ProcessorAdminProducer producer;
    private final LightSearchCheckerProducer checkerProducer;

    @Autowired
    private LightSearchServerService serverService;

    public ProcessorHolderAdminTestImpl(ProcessorAdminProducer producer, LightSearchCheckerProducer checkerProducer) {
        this.producer = producer;
        this.checkerProducer = checkerProducer;
    }

    @Override
    public Processor get(String command) {
        if(holder.isEmpty())
            initHolder();

        return holder.get(command);
    }

    private void initHolder() {
        LightSearchChecker checker = checkerProducer.getLightSearchCheckerDefaultInstance();

        holder.put(ADD_BLACKLIST.stringValue(),  producer.getAddBlacklistProcessorInstance(serverService, checker));
        holder.put(DEL_BLACKLIST.stringValue(), producer.getDelBlacklistProcessorInstance(serverService, checker));
        holder.put(BLACKLIST.stringValue(), producer.getBlacklistRequestProcessorInstance(serverService));
        holder.put(CLIENT_LIST.stringValue(), producer.getClientListRequestProcessorInstance(serverService));
        holder.put(KICK.stringValue(), producer.getClientKickProcessorInstance(serverService, checker));
        holder.put(CHANGE_DATABASE.stringValue(), producer.getChangeDatabaseProcessorInstance(checker, serverService.currentDirectory()));
        holder.put(CLIENT_TIMEOUT.stringValue(), producer.getClientTimeoutProcessorInstance(serverService.currentDirectory()));
        holder.put(RESTART_TIME.stringValue(), producer.getRestartTimeProcessorInstance(checker, serverService.currentDirectory()));
        holder.put(RESTART.stringValue(), producer.getRestartProcessorInstance());
    }
}
