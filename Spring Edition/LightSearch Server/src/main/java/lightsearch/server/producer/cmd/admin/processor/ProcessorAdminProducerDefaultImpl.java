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

package lightsearch.server.producer.cmd.admin.processor;

import lightsearch.server.checker.LightSearchChecker;
import lightsearch.server.cmd.admin.processor.AdminProcessor;
import lightsearch.server.data.LightSearchServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service("processorAdminProducerDefault")
public class ProcessorAdminProducerDefaultImpl implements ProcessorAdminProducer {

    @Autowired
    private ApplicationContext ctx;

    @Override
    public AdminProcessor getAddBlacklistProcessorInstance(LightSearchServerService serverService, LightSearchChecker checker) {
        return (AdminProcessor) ctx.getBean("addBlacklistProcessor", serverService, checker);
    }

    @Override
    public AdminProcessor getDelBlacklistProcessorInstance(LightSearchServerService serverService, LightSearchChecker checker) {
        return (AdminProcessor) ctx.getBean("delBlacklistProcessor", serverService, checker);
    }

    @Override
    public AdminProcessor getBlacklistRequestProcessorInstance(LightSearchServerService serverService) {
        return (AdminProcessor) ctx.getBean("blacklistRequestProcessor", serverService);
    }

    @Override
    public AdminProcessor getClientListRequestProcessorInstance(LightSearchServerService serverService) {
        return (AdminProcessor) ctx.getBean("clientListRequestProcessor", serverService);
    }

    @Override
    public AdminProcessor getClientKickProcessorInstance(LightSearchServerService serverService, LightSearchChecker checker) {
        return (AdminProcessor) ctx.getBean("clientKickProcessor", serverService, checker);
    }

    @Override
    public AdminProcessor getChangeDatabaseProcessorInstance(LightSearchChecker checker, String currentDirectory) {
        return (AdminProcessor) ctx.getBean("changeDatabaseProcessor", checker, currentDirectory);
    }

    @Override
    public AdminProcessor getClientTimeoutProcessorInstance(String currentDirectory) {
        return (AdminProcessor) ctx.getBean("clientTimeoutProcessor", currentDirectory);
    }

    @Override
    public AdminProcessor getRestartTimeProcessorInstance(LightSearchChecker checker, String currentDirectory) {
        return (AdminProcessor) ctx.getBean("restartTimeProcessor", checker, currentDirectory);
    }

    @Override
    public AdminProcessor getRestartProcessorInstance() {
        return ctx.getBean("restartProcessor", AdminProcessor.class);
    }
}
