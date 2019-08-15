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

public interface ProcessorAdminProducer {
    ProcessorAdmin getAuthenticationProcessorInstance(LightSearchServerDTO serverDTO, LightSearchChecker checker,
                                                      AdminDAO adminDAO);
    ProcessorAdmin getBlacklistRequestProcessorInstance(LightSearchServerDTO serverDTO, LightSearchChecker checker);
    ProcessorAdmin getAddBlacklistProcessorInstance(LightSearchServerDTO serverDTO, LightSearchChecker checker);
    ProcessorAdmin getChangeDatabaseProcessorInstance(LightSearchServerDTO serverDTO, LightSearchChecker checker);
    ProcessorAdmin getClientKickProcessorInstance(LightSearchServerDTO serverDTO, LightSearchChecker checker);
    ProcessorAdmin getClientListRequestProcessorInstance(LightSearchServerDTO serverDTO, LightSearchChecker checker);
    ProcessorAdmin getCreateAdminProcessorInstance(LightSearchServerDTO serverDTO, LightSearchChecker checker);
    ProcessorAdmin getDelBlacklistProcessorInstance(LightSearchServerDTO serverDTO, LightSearchChecker checker);
    ProcessorAdmin getRestartProcessorInstance(LightSearchServerDTO serverDTO, LightSearchChecker checker,
                                                       LoggerServer logger, CurrentDateTime currentDateTime,
                                                       ThreadManager threadManager, TimersIDEnum timerId);
    ProcessorAdmin getTimeoutClientProcessorInstance(LightSearchServerDTO serverDTO, LightSearchChecker checker);
    ProcessorAdmin getTimeoutServerProcessor(LightSearchServerDTO serverDTO, LightSearchChecker checker,
                                                     ServerStateChanger changer, TimersIDEnum timerId);
}
