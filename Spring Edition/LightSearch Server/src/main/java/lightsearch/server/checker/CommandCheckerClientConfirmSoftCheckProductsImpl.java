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

package lightsearch.server.checker;

import lightsearch.server.cmd.client.ClientCommand;
import lightsearch.server.data.LightSearchServerService;
import lightsearch.server.exception.CheckerException;
import lightsearch.server.producer.checker.CommandCheckerProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("commandCheckerClientConfirmSoftCheckProducts")
@Scope("prototype")
public class CommandCheckerClientConfirmSoftCheckProductsImpl implements CommandChecker {

    private final ClientCommand command;
    private final LightSearchServerService serverService;
    private final LightSearchChecker checker;

    @Autowired
    private CommandCheckerProducer commandCheckerProducer;

    public CommandCheckerClientConfirmSoftCheckProductsImpl(
            ClientCommand command, LightSearchServerService serverService, LightSearchChecker checker) {
        this.command = command;
        this.serverService = serverService;
        this.checker = checker;
    }

    @Override
    public void check() throws CheckerException {
        if(checker.isNull(command.IMEI(), command.userIdentifier(), command.cardCode(), command.data()))
            throw new CheckerException("Неверный формат команды.", "ConfirmSoftCheckProducts: unknown client.");

        commandCheckerProducer.getCommandCheckerClientDefaultInstance(command.IMEI(), serverService).check();
    }
}
