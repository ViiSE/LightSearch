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
import lightsearch.server.cmd.client.ClientCommand;
import lightsearch.server.data.BlacklistService;
import lightsearch.server.data.LightSearchServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service("commandCheckerProducerDefault")
public class CommandCheckerProducerDefaultImpl implements CommandCheckerProducer {

    private final String COMMAND_CHECKER_CLIENT_AUTHORIZATION = "commandCheckerClientAuthorization";
    private final String COMMAND_CHECKER_CLIENT_SEARCH = "commandCheckerClientSearch";
    private final String COMMAND_CHECKER_CLIENT_OPEN_SOFT_CHECK = "commandCheckerClientOpenSoftCheck";
    private final String COMMAND_CHECKER_CLIENT_CANCEL_SOFT_CHECK = "commandCheckerClientCancelSoftCheck";
    private final String COMMAND_CHECKER_CLIENT_DEFAULT = "commandCheckerClientDefault";
    private final String COMMAND_CHECKER_CLIENT_CLOSE_SOFT_CHECK = "commandCheckerClientCloseSoftCheck";
    private final String COMMAND_CHECKER_CLIENT_CONFIRM_SOFT_CHECK_PRODUCTS = "commandCheckerClientConfirmSoftCheckProducts";

    @Autowired
    private ApplicationContext ctx;

    @Override
    public CommandChecker getCommandCheckerClientAuthorizationInstance(
            ClientCommand command, BlacklistService blacklistService, LightSearchChecker checker) {
        return (CommandChecker) ctx.getBean(COMMAND_CHECKER_CLIENT_AUTHORIZATION, command, blacklistService, checker);
    }

    @Override
    public CommandChecker getCommandCheckerClientSearchInstance(
            ClientCommand command, LightSearchServerService serverService, LightSearchChecker checker) {
        return (CommandChecker) ctx.getBean(COMMAND_CHECKER_CLIENT_SEARCH, command, serverService, checker);
    }

    @Override
    public CommandChecker getCommandCheckerClientOpenSoftCheckInstance(
            ClientCommand command, LightSearchServerService serverService, LightSearchChecker checker) {
        return (CommandChecker) ctx.getBean(COMMAND_CHECKER_CLIENT_OPEN_SOFT_CHECK, command, serverService, checker);
    }

    @Override
    public CommandChecker getCommandCheckerClientCancelSoftCheckInstance(
            ClientCommand command, LightSearchServerService serverService, LightSearchChecker checker) {
        return (CommandChecker) ctx.getBean(COMMAND_CHECKER_CLIENT_CANCEL_SOFT_CHECK, command, serverService, checker);
    }

    @Override
    public CommandChecker getCommandCheckerClientDefaultInstance(String IMEI, LightSearchServerService serverService) {
        return (CommandChecker) ctx.getBean(COMMAND_CHECKER_CLIENT_DEFAULT, IMEI, serverService);
    }

    @Override
    public CommandChecker getCommandCheckerClientCloseSoftCheckInstance(
            ClientCommand command, LightSearchServerService serverService, LightSearchChecker checker) {
        return (CommandChecker) ctx.getBean(COMMAND_CHECKER_CLIENT_CLOSE_SOFT_CHECK, command, serverService, checker);
    }

    @Override
    public CommandChecker getCommandCheckerClientConfirmSoftCheckProductsInstance(
            ClientCommand command, LightSearchServerService serverService, LightSearchChecker checker) {
        return (CommandChecker) ctx.getBean(COMMAND_CHECKER_CLIENT_CONFIRM_SOFT_CHECK_PRODUCTS, command, serverService, checker);
    }
}
