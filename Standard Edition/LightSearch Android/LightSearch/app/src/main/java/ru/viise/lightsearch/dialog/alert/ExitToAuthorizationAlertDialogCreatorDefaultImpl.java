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

package ru.viise.lightsearch.dialog.alert;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;

import ru.viise.lightsearch.cmd.manager.CommandManager;
import ru.viise.lightsearch.data.ButtonContentEnum;
import ru.viise.lightsearch.fragment.StackFragmentTitle;

public class ExitToAuthorizationAlertDialogCreatorDefaultImpl implements ExitToAuthorizationAlertDialogCreator {

    private final String OK = ButtonContentEnum.POSITIVE_BUTTON.stringValue();
    private final String NEGATIVE = ButtonContentEnum.NEGATIVE_BUTTON.stringValue();

    private final FragmentActivity activity;
    private final CommandManager commandManager;

    public ExitToAuthorizationAlertDialogCreatorDefaultImpl(FragmentActivity activity,
                CommandManager commandManager) {
        this.activity = activity;
        this.commandManager = commandManager;
    }

    @Override
    public AlertDialog createAlertDialog() {
        return new android.support.v7.app.AlertDialog.Builder(activity).setTitle("Выход").setMessage("Вы уверены, что хотите выйти в меню авторизации?")
                .setPositiveButton(OK, (dialogInterface, i) -> {
                    commandManager.closeConnection();
                    dialogInterface.dismiss();
                    activity.setTitle(StackFragmentTitle.pop());
                    activity.getSupportFragmentManager().popBackStack();
                })
                .setNegativeButton(NEGATIVE, (dialogInterface, i) -> dialogInterface.dismiss())
                .create();
    }
}
