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

package test.rule;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import ru.viise.lightsearch.cmd.CommandTypeEnum;
import ru.viise.lightsearch.data.CommandDTO;
import ru.viise.lightsearch.data.CommandOpenSoftCheckDTOInit;
import ru.viise.lightsearch.data.ReconnectDTO;
import ru.viise.lightsearch.data.ReconnectDTOInit;

public class CreateReconnectDTORule implements TestRule {

    private CommandDTO lastCommand;
    private ReconnectDTO reconnectDTO;


    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                lastCommand = CommandOpenSoftCheckDTOInit.commandOpenSoftCheckDTO("007", "777");
                reconnectDTO = ReconnectDTOInit.reconnectDTO(lastCommand, CommandTypeEnum.OPEN_SOFT_CHECK);

                base.evaluate();
            }
        };
    }

    public ReconnectDTO getReconnectDTO() {
        return reconnectDTO;
    }
}
