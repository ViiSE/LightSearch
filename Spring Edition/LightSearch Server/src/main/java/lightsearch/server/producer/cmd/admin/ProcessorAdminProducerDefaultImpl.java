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

package lightsearch.server.producer.cmd.admin;

import lightsearch.server.checker.LightSearchChecker;
import lightsearch.server.cmd.admin.processor.ProcessorAdmin;
import lightsearch.server.cmd.changer.ServerStateChanger;
import lightsearch.server.data.AdminDAO;
import lightsearch.server.data.LightSearchServerDTO;
import lightsearch.server.log.LoggerServer;
import lightsearch.server.thread.ThreadManager;
import lightsearch.server.time.CurrentDateTime;
import lightsearch.server.timer.TimersIDEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service("processorAdminProducerDefault")
public class ProcessorAdminProducerDefaultImpl implements ProcessorAdminProducer {

    private final String AUTHENTICATION_PROCESSOR      = "authenticationProcessorAdmin";
    private final String BLACKLIST_REQUEST_PROCESSOR   = "blacklistRequestProcessorAdmin";
    private final String ADD_BLACKLIST_PROCESSOR       = "addBlacklistProcessorAdmin";
    private final String CHANGE_DATABASE_PROCESSOR     = "changeDatabaseProcessorAdmin";
    private final String CLIENT_KICK_PROCESSOR         = "clientKickProcessorAdmin";
    private final String CLIENT_LIST_REQUEST_PROCESSOR = "clientListRequestProcessorAdmin";
    private final String CREATE_ADMIN_PROCESSOR        = "createAdminProcessorAdmin";
    private final String DEL_BLACKLIST_PROCESSOR       = "delBlacklistProcessorAdmin";
    private final String RESTART_PROCESSOR             = "restartProcessorAdmin";
    private final String TIMEOUT_CLIENT_PROCESSOR      = "timeoutClientProcessorAdmin";
    private final String TIMEOUT_SERVER_PROCESSOR      = "timeoutServerProcessorAdmin";

    @Autowired
    private ApplicationContext ctx;

    @Override
    public ProcessorAdmin getAuthenticationProcessorInstance(LightSearchServerDTO serverDTO, LightSearchChecker checker,
                                                             AdminDAO adminDAO) {
        return (ProcessorAdmin) ctx.getBean(AUTHENTICATION_PROCESSOR, serverDTO, checker, adminDAO);
    }

    @Override
    public ProcessorAdmin getBlacklistRequestProcessorInstance(LightSearchServerDTO serverDTO, LightSearchChecker checker) {
        return (ProcessorAdmin) ctx.getBean(BLACKLIST_REQUEST_PROCESSOR, serverDTO, checker);
    }

    @Override
    public ProcessorAdmin getAddBlacklistProcessorInstance(LightSearchServerDTO serverDTO, LightSearchChecker checker) {
        return (ProcessorAdmin) ctx.getBean(ADD_BLACKLIST_PROCESSOR, serverDTO, checker);
    }

    @Override
    public ProcessorAdmin getChangeDatabaseProcessorInstance(LightSearchServerDTO serverDTO, LightSearchChecker checker) {
        return (ProcessorAdmin) ctx.getBean(CHANGE_DATABASE_PROCESSOR, serverDTO, checker);
    }

    @Override
    public ProcessorAdmin getClientKickProcessorInstance(LightSearchServerDTO serverDTO, LightSearchChecker checker) {
        return (ProcessorAdmin) ctx.getBean(CLIENT_KICK_PROCESSOR, serverDTO, checker);
    }

    @Override
    public ProcessorAdmin getClientListRequestProcessorInstance(LightSearchServerDTO serverDTO, LightSearchChecker checker) {
        return (ProcessorAdmin) ctx.getBean(CLIENT_LIST_REQUEST_PROCESSOR, serverDTO, checker);
    }

    @Override
    public ProcessorAdmin getCreateAdminProcessorInstance(LightSearchServerDTO serverDTO, LightSearchChecker checker) {
        return (ProcessorAdmin) ctx.getBean(CREATE_ADMIN_PROCESSOR, serverDTO, checker);
    }

    @Override
    public ProcessorAdmin getDelBlacklistProcessorInstance(LightSearchServerDTO serverDTO, LightSearchChecker checker) {
        return (ProcessorAdmin) ctx.getBean(DEL_BLACKLIST_PROCESSOR, serverDTO, checker);
    }

    @Override
    public ProcessorAdmin getRestartProcessorInstance(LightSearchServerDTO serverDTO, LightSearchChecker checker,
                                                      LoggerServer logger, CurrentDateTime currentDateTime,
                                                      ThreadManager threadManager, TimersIDEnum timerId) {
        return (ProcessorAdmin) ctx.getBean(RESTART_PROCESSOR, serverDTO, checker, logger, currentDateTime,
                threadManager, timerId);
    }

    @Override
    public ProcessorAdmin getTimeoutClientProcessorInstance(LightSearchServerDTO serverDTO, LightSearchChecker checker) {
        return (ProcessorAdmin) ctx.getBean(TIMEOUT_CLIENT_PROCESSOR, serverDTO, checker);
    }

    @Override
    public ProcessorAdmin getTimeoutServerProcessor(LightSearchServerDTO serverDTO, LightSearchChecker checker,
                                                            ServerStateChanger changer, TimersIDEnum timerId) {
        return (ProcessorAdmin) ctx.getBean(TIMEOUT_SERVER_PROCESSOR, serverDTO, checker, changer, timerId);
    }
}
