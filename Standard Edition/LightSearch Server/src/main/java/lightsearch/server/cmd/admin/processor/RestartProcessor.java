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
package lightsearch.server.cmd.admin.processor;

import lightsearch.server.checker.LightSearchChecker;
import lightsearch.server.cmd.admin.AdminCommand;
import lightsearch.server.cmd.result.CommandResult;
import lightsearch.server.daemon.DaemonServer;
import lightsearch.server.daemon.DaemonServerCreator;
import lightsearch.server.daemon.DaemonServerCreatorInit;
import lightsearch.server.initialization.OsDetector;
import lightsearch.server.initialization.OsDetectorInit;
import lightsearch.server.log.LoggerServer;
import lightsearch.server.thread.ThreadManager;
import lightsearch.server.time.CurrentDateTime;
import lightsearch.server.data.LightSearchServerDTO;
import lightsearch.server.log.LogMessageTypeEnum;
import lightsearch.server.timer.TimersIDEnum;

/**
 * Обработчик команды перезагрузки LightSearch Server.
 * <p>
 * Немедленно перезагружает LightSearch Server.
 * @author ViiSE
 * @since 1.0.0
 */
public class RestartProcessor extends AbstractProcessorAdmin {

    private final LoggerServer logger;
    private final CurrentDateTime currentDateTime;
    private final TimersIDEnum timerId;
    private final ThreadManager threadManager;
    
    public RestartProcessor(LightSearchServerDTO serverDTO, LightSearchChecker checker,
            LoggerServer logger, CurrentDateTime currentDateTime, 
            ThreadManager threadManager, TimersIDEnum timerId) {
        super(serverDTO, checker);
        this.logger = logger;
        this.currentDateTime = currentDateTime;
        this.threadManager = threadManager;
        this.timerId = timerId;
    }

    @Override
    public CommandResult apply(AdminCommand admCommand) {

        try { Thread.sleep(1000); }
        catch(InterruptedException ignore) {}
        logger.log(LogMessageTypeEnum.INFO, currentDateTime, "Server restarted");
          
        if(threadManager.interruptAll(timerId.stringValue())) {
            OsDetector osDetector = OsDetectorInit.osDetector();
            DaemonServerCreator daemonServerCreator = DaemonServerCreatorInit.daemonServerCreator(osDetector, serverDTO.currentDirectory());
            DaemonServer daemonServer = daemonServerCreator.createDaemonServer();
            daemonServer.exec();
        }
        
        return null;
    }
}
