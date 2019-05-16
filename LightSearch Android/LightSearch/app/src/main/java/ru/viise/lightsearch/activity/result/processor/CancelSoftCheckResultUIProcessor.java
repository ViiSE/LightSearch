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
import ru.viise.lightsearch.cmd.result.CancelSoftCheckCommandResult;
import ru.viise.lightsearch.cmd.result.CommandResult;
import ru.viise.lightsearch.fragment.IContainerFragment;

public class CancelSoftCheckResultUIProcessor implements Function<CommandResult, Void> {

    private final ManagerActivity activity;

    public CancelSoftCheckResultUIProcessor(ManagerActivity activity) {
        this.activity = activity;
    }

    @Override
    public Void apply(CommandResult commandResult) {
        CancelSoftCheckCommandResult cancelSCCmdRes = (CancelSoftCheckCommandResult)commandResult;
        if(cancelSCCmdRes.isDone()) {
            activity.callDialogSuccess(cancelSCCmdRes.message());
            IContainerFragment containerFragment = activity.getContainerFragment();

            if(containerFragment != null)
                containerFragment.switchToOpenSoftCheckFragment();
        }
        else
            activity.callDialogError(cancelSCCmdRes.message());

        return null;
    }
}
