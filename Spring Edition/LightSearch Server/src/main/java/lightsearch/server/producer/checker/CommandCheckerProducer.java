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
import lightsearch.server.data.LightSearchServerService;

public interface CommandCheckerProducer {
    CommandChecker getCommandCheckerClientAuthorizationInstance(
            ClientCommand command, BlacklistService blacklistService, LightSearchChecker checker);
    CommandChecker getCommandCheckerClientSearchInstance(
            ClientCommand command, LightSearchServerService serverService, LightSearchChecker checker);
    CommandChecker getCommandCheckerClientOpenSoftCheckInstance(
            ClientCommand command, LightSearchServerService serverService, LightSearchChecker checker);
    CommandChecker getCommandCheckerClientCancelSoftCheckInstance(
            ClientCommand command, LightSearchServerService serverService, LightSearchChecker checker);
    CommandChecker getCommandCheckerClientDefaultInstance(String IMEI, LightSearchServerService serverService);
    CommandChecker getCommandCheckerClientCloseSoftCheckInstance(
            ClientCommand command, LightSearchServerService serverService, LightSearchChecker checker);
    CommandChecker getCommandCheckerClientConfirmSoftCheckProductsInstance(
            ClientCommand command, LightSearchServerService serverService, LightSearchChecker checker);

    CommandChecker getCommandCheckerAdminAddBlacklistInstance(
            AdminCommand command, BlacklistService blacklistService, LightSearchChecker checker);
}
