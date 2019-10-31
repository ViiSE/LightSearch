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

import lightsearch.server.cmd.admin.AdminCommand;
import lightsearch.server.exception.CheckerException;
import lightsearch.server.exception.ValidatorException;
import lightsearch.server.producer.validator.ValidatorProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("commandCheckerAdminRestartTime")
@Scope("prototype")
public class CommandCheckerAdminRestartTimeImpl implements CommandChecker {

    private final LightSearchChecker checker;
    private final AdminCommand command;

    @Autowired
    private ValidatorProducer validatorProducer;

    public CommandCheckerAdminRestartTimeImpl(LightSearchChecker checker, AdminCommand command) {
        this.checker = checker;
        this.command = command;
    }

    @Override
    public void check() throws CheckerException {
        if(checker.isNull(command.restartTime()))
            throw new CheckerException("Неверный формат команды. Время перезагрузки сервера имеет нулевое значение!",
                    "RestartTime: restartTime is null");
        if(checker.isEmpty(command.restartTime()))
            throw new CheckerException("Неверный формат команды. Время перезагрузки сервера имеет пустое значение!",
                    "RestartTime: restartTime is empty");

        try {
            validatorProducer.getRestartTimeValidatorInstance().validate(command.restartTime());
        } catch (ValidatorException ex) {
            throw new CheckerException(ex.getMessage(), ex.getLogMessage());
        }
    }
}
