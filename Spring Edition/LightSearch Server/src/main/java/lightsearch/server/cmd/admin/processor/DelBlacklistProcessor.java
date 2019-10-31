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
package lightsearch.server.cmd.admin.processor;

import lightsearch.server.checker.LightSearchChecker;
import lightsearch.server.cmd.admin.AdminCommand;
import lightsearch.server.cmd.result.AdminCommandResultCreator;
import lightsearch.server.cmd.result.ResultType;
import lightsearch.server.data.BlacklistService;
import lightsearch.server.data.LightSearchServerService;
import lightsearch.server.data.pojo.AdminCommandResult;
import lightsearch.server.exception.CheckerException;
import lightsearch.server.exception.CommandResultException;
import lightsearch.server.log.LoggerServer;
import lightsearch.server.producer.checker.CommandCheckerProducer;
import lightsearch.server.producer.cmd.admin.ErrorAdminCommandServiceProducer;
import lightsearch.server.producer.cmd.result.AdminCommandResultCreatorProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import static lightsearch.server.log.LogMessageTypeEnum.INFO;

/**
 *
 * @author ViiSE
 */
@Component("delBlacklistProcessor")
@Scope("prototype")
public class DelBlacklistProcessor implements AdminProcessor<AdminCommandResult> {

    private final BlacklistService<String> blacklistService;
    private final LightSearchChecker checker;
    private final String blacklistDirectory;

    @Autowired private LoggerServer logger;
    @Autowired private ErrorAdminCommandServiceProducer errAdmCmdServiceProducer;
    @Autowired private CommandCheckerProducer cmdCheckerProducer;
    @Autowired private AdminCommandResultCreatorProducer admCmdResCrProducer;

    @SuppressWarnings("unchecked")
    public DelBlacklistProcessor(LightSearchServerService serverService, LightSearchChecker checker) {
        this.checker = checker;
        this.blacklistService = serverService.blacklistService();
        this.blacklistDirectory = serverService.currentDirectory() + "blacklist";
    }

    @Override
    synchronized public AdminCommandResult apply(AdminCommand command) {
        try {
            cmdCheckerProducer.getCommandCheckerAdminDelBlacklistInstance(command, blacklistService, checker).check();
            blacklistService.blacklist().remove(command.IMEI());
            try (FileOutputStream fout = new FileOutputStream(blacklistDirectory, true);
                 BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fout))) {
                List<String> blacklist = blacklistService.blacklist();
                for (String IMEI : blacklist) {
                    bw.write(IMEI);
                    bw.newLine();
                }
            } catch (IOException ex) {
                blacklistService.blacklist().add(command.IMEI());
                return errAdmCmdServiceProducer.getErrorAdminCommandServiceDefaultInstance()
                        .createErrorResult("Невозможно удалить клиента из черного списка. Исключение: " + ex.getMessage(),
                                "Cannot removed client from the blacklist. Exception: " + ex.getMessage());
            }

            AdminCommandResultCreator commandResultCreator =
                    admCmdResCrProducer.getCommandResultCreatorAdminDefaultInstance(
                            ResultType.TRUE.stringValue(), "Данный клиент был удален из черного списка.", null, null);
            logger.log(DelBlacklistProcessor.class, INFO, "Client has been removed from the blacklist: IMEI - " + command.IMEI());

            return commandResultCreator.createAdminCommandResult();
        } catch (CheckerException ex) {
            return errAdmCmdServiceProducer.getErrorAdminCommandServiceDefaultInstance()
                    .createErrorResult(ex.getMessage(), ex.getLogMessage());
        } catch (CommandResultException ex) {
            return errAdmCmdServiceProducer.getErrorAdminCommandServiceDefaultInstance()
                    .createErrorResult(ex.getMessage(), ex.getMessage());
        }
    }
}
