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

@Component("commandCheckerAdminChangeDatabase")
@Scope("prototype")
public class CommandCheckerAdminChangeDatabaseImpl implements CommandChecker {

    private final AdminCommand command;
    private final LightSearchChecker checker;

    @Autowired
    private ValidatorProducer validatorProducer;

    public CommandCheckerAdminChangeDatabaseImpl(AdminCommand command, LightSearchChecker checker) {
        this.command = command;
        this.checker = checker;
    }

    @Override
    public void check() throws CheckerException {
        if(checker.isNull(command.dbName()))
            throw new CheckerException("Неверный формат команды. Имя базы данных имеет значение null!", "ChangeDatabase: dbName is null!");
        if(checker.isEmpty(command.dbName()))
            throw new CheckerException("Неверный формат команды. Имя базы данных имеет пустое значение!", "ChangeDatabase: dbName is empty!");
        if(checker.isNull(command.ip()))
            throw new CheckerException("Неверный формат команды. IP адрес базы данных имеет значение null!", "ChangeDatabase: ip is null!");
        if(checker.isEmpty(command.ip()))
            throw new CheckerException("Неверный формат команды. IP адрес базы данных имеет пустое значение!", "ChangeDatabase: ip is empty!");

        try {
            validatorProducer.getIpValidatorInstance().validate(command.ip());
            validatorProducer.getPortValidatorInstance().validate(command.port());
        } catch (ValidatorException ex) {
            throw new CheckerException(ex.getMessage(), ex.getLogMessage());
        }
    }
}
