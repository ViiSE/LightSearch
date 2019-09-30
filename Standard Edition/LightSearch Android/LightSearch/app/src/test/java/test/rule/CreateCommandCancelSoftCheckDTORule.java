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

import ru.viise.lightsearch.data.CommandCancelSoftCheckDTO;
import ru.viise.lightsearch.data.CommandCancelSoftCheckDTOInit;

public class CreateCommandCancelSoftCheckDTORule implements TestRule {

    private String IMEI;
    private CommandCancelSoftCheckDTO cmdCancelSCDTO;

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                IMEI = "123456789123456";

                String userIdent = "007";
                String cardCode = "777";
                cmdCancelSCDTO = CommandCancelSoftCheckDTOInit.commandCancelSoftCheckDTO(
                        userIdent, cardCode, false);
                base.evaluate();
            }
        };
    }

    public CommandCancelSoftCheckDTO getCommandCancelSoftCheckDTO() {
        return cmdCancelSCDTO;
    }

    public String getIMEI() {
        return IMEI;
    }
}
