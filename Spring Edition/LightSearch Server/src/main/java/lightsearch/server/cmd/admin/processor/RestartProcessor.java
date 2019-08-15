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
import lightsearch.server.data.LightSearchServerDTO;
import lightsearch.server.initialization.OsDetector;
import lightsearch.server.log.LogMessageTypeEnum;
import lightsearch.server.log.LoggerServer;
import lightsearch.server.producer.cmd.admin.CommandResultAdminCreatorProducer;
import lightsearch.server.producer.daemon.DaemonServerCreatorProducer;
import lightsearch.server.producer.initialization.OsDetectorProducer;
import lightsearch.server.thread.ThreadManager;
import lightsearch.server.time.CurrentDateTime;
import lightsearch.server.timer.TimersIDEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author ViiSE
 */
@Component("restartProcessorAdmin")
@Scope("prototype")
public class RestartProcessor implements ProcessorAdmin {

    private final LightSearchChecker checker;
    private final String currentDirectory;
    private final LoggerServer logger;
    private final CurrentDateTime currentDateTime;
    private final TimersIDEnum timerId;
    private final ThreadManager threadManager;

    @Autowired
    private CommandResultAdminCreatorProducer producer;

    @Autowired private OsDetectorProducer osDetectorProducer;
    @Autowired private DaemonServerCreatorProducer daemonServerCreatorProducer;

    public RestartProcessor(LightSearchServerDTO serverDTO, LightSearchChecker checker,
            LoggerServer logger, CurrentDateTime currentDateTime, 
            ThreadManager threadManager, TimersIDEnum timerId) {
        this.checker = checker;
        this.currentDirectory = serverDTO.currentDirectory();
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
            OsDetector osDetector = osDetectorProducer.getOsDetectorDefaultInstance();
            DaemonServerCreator daemonServerCreator = daemonServerCreatorProducer.getDaemonServerCreatorDefaultInstance(
                    osDetector, currentDirectory);
            DaemonServer daemonServer = daemonServerCreator.createDaemonServer();
            daemonServer.exec();
        }
        
        return null;
    }
}
