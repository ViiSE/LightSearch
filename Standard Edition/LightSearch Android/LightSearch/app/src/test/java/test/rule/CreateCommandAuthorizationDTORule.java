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

import ru.viise.lightsearch.data.CommandAuthorizationDTO;
import ru.viise.lightsearch.data.CommandAuthorizationDTOInit;

public class CreateCommandAuthorizationDTORule implements TestRule {

    private CommandAuthorizationDTO cmdAuthDTO;

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                String IMEI = "123456789123456";
                String ip = "127.0.0.1";
                String os = "Android 8.1 Oreo";
                String model = "Pixel 2";
                String username = "androidClient";
                String password = "pass12!";
                String userIdent = "007";
                cmdAuthDTO = CommandAuthorizationDTOInit.commandAuthorizationDTO(
                        IMEI, ip, os, model, username, password, userIdent, null);
                base.evaluate();
            }
        };
    }

    public CommandAuthorizationDTO getCommandAuthorizationDTO() {
        return cmdAuthDTO;
    }
}
