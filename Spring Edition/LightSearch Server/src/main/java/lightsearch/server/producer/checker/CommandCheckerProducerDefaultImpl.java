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

package lightsearch.server.producer.checker;

import lightsearch.server.checker.CommandChecker;
import lightsearch.server.checker.LightSearchChecker;
import lightsearch.server.cmd.admin.AdminCommand;
import lightsearch.server.cmd.client.ClientCommand;
import lightsearch.server.data.BlacklistService;
import lightsearch.server.data.ClientsService;
import lightsearch.server.data.LightSearchServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service("commandCheckerProducerDefault")
public class CommandCheckerProducerDefaultImpl implements CommandCheckerProducer {

    @Autowired
    private ApplicationContext ctx;

    @Override
    public CommandChecker getCommandCheckerClientAuthorizationInstance(
            ClientCommand command, BlacklistService blacklistService, LightSearchChecker checker) {
        return (CommandChecker) ctx.getBean("commandCheckerClientAuthorization", command, blacklistService, checker);
    }

    @Override
    public CommandChecker getCommandCheckerClientSearchInstance(
            ClientCommand command, LightSearchServerService serverService, LightSearchChecker checker) {
        return (CommandChecker) ctx.getBean("commandCheckerClientSearch", command, serverService, checker);
    }

    @Override
    public CommandChecker getCommandCheckerClientOpenSoftCheckInstance(
            ClientCommand command, LightSearchServerService serverService, LightSearchChecker checker) {
        return (CommandChecker) ctx.getBean("commandCheckerClientOpenSoftCheck", command, serverService, checker);
    }

    @Override
    public CommandChecker getCommandCheckerClientCancelSoftCheckInstance(
            ClientCommand command, LightSearchServerService serverService, LightSearchChecker checker) {
        return (CommandChecker) ctx.getBean("commandCheckerClientCancelSoftCheck", command, serverService, checker);
    }

    @Override
    public CommandChecker getCommandCheckerClientDefaultInstance(String IMEI, LightSearchServerService serverService) {
        return (CommandChecker) ctx.getBean("commandCheckerClientDefault", IMEI, serverService);
    }

    @Override
    public CommandChecker getCommandCheckerClientCloseSoftCheckInstance(
            ClientCommand command, LightSearchServerService serverService, LightSearchChecker checker) {
        return (CommandChecker) ctx.getBean("commandCheckerClientCloseSoftCheck", command, serverService, checker);
    }

    @Override
    public CommandChecker getCommandCheckerClientConfirmSoftCheckProductsInstance(
            ClientCommand command, LightSearchServerService serverService, LightSearchChecker checker) {
        return (CommandChecker) ctx.getBean("commandCheckerClientConfirmSoftCheckProducts", command, serverService, checker);
    }

    @Override
    public CommandChecker getCommandCheckerAdminAddBlacklistInstance(
            AdminCommand command, BlacklistService blacklistService, LightSearchChecker checker) {
        return (CommandChecker) ctx.getBean("commandCheckerAdminAddBlacklist", command, blacklistService, checker);
    }

    @Override
    public CommandChecker getCommandCheckerAdminKickClientInstance(
            AdminCommand command, ClientsService clientsService, LightSearchChecker checker) {
        return (CommandChecker) ctx.getBean("commandCheckerAdminKickClient", command, clientsService, checker);
    }

    @Override
    public CommandChecker getCommandCheckerAdminDelBlacklistInstance(
            AdminCommand command, BlacklistService blacklistService, LightSearchChecker checker) {
        return (CommandChecker) ctx.getBean("commandCheckerAdminDelBlacklist", command, blacklistService, checker);
    }

    @Override
    public CommandChecker getCommandCheckerAdminChangeDatabaseInstance(AdminCommand command, LightSearchChecker checker) {
        return (CommandChecker) ctx.getBean("commandCheckerAdminChangeDatabase", command, checker);
    }

    @Override
    public CommandChecker getCommandCheckerAdminClientTimeoutInstance(AdminCommand command) {
        return (CommandChecker) ctx.getBean("commandCheckerAdminClientTimeout", command);
    }

    @Override
    public CommandChecker getCommandCheckerAdminRestartTimeInstance(AdminCommand command, LightSearchChecker checker) {
        return (CommandChecker) ctx.getBean("commandCheckerAdminRestartTime", checker, command);
    }
}
