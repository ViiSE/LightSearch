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

package ru.viise.lightsearch.activity.result.processor;

import java.util.function.Function;

import ru.viise.lightsearch.activity.ManagerActivity;
import ru.viise.lightsearch.cmd.result.AuthorizationCommandResult;
import ru.viise.lightsearch.cmd.result.CommandResult;

public class AuthorizationResultUIProcessor implements Function<CommandResult, Void> {

    private final ManagerActivity activity;

    public AuthorizationResultUIProcessor(ManagerActivity activity) {
        this.activity = activity;
    }

    @Override
    public Void apply(CommandResult commandResult) {
        AuthorizationCommandResult authCmdRes = (AuthorizationCommandResult)commandResult;
        if(authCmdRes.isDone()) {
            activity.callDialogSuccess(authCmdRes.message());
            activity.doContainerFragmentTransaction(authCmdRes.skladList(), authCmdRes.TKList());
        }
        else
            activity.callDialogError(authCmdRes.message());

        return null;
    }
}
