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

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;

import ru.viise.lightsearch.activity.ManagerActivityHandler;
import ru.viise.lightsearch.cmd.CommandTypeEnum;
import ru.viise.lightsearch.cmd.manager.CommandManager;
import ru.viise.lightsearch.cmd.manager.task.CommandManagerAsyncTask;
import ru.viise.lightsearch.data.ButtonContentEnum;
import ru.viise.lightsearch.data.CommandCancelSoftCheckDTO;
import ru.viise.lightsearch.data.CommandCancelSoftCheckDTOInit;
import ru.viise.lightsearch.data.CommandManagerAsyncTaskDTO;
import ru.viise.lightsearch.data.CommandManagerAsyncTaskDTOInit;
import ru.viise.lightsearch.pref.PreferencesManager;
import ru.viise.lightsearch.pref.PreferencesManagerInit;
import ru.viise.lightsearch.pref.PreferencesManagerType;

public class CancelSoftCheckAlertDialogCreatorDefaultImpl implements CancelSoftCheckAlertDialogCreator {

    private final String OK       = ButtonContentEnum.POSITIVE_BUTTON.stringValue();
    private final String NEGATIVE = ButtonContentEnum.NEGATIVE_BUTTON.stringValue();

    private final String PREF = "pref";

    private final Fragment fragment;
    private final ManagerActivityHandler managerActivityHandler;
    private final android.app.AlertDialog queryDialog;
    private final CommandManager commandManager;

    public CancelSoftCheckAlertDialogCreatorDefaultImpl(Fragment fragment, ManagerActivityHandler managerActivityHandler,
                android.app.AlertDialog queryDialog, CommandManager commandManager) {
        this.fragment = fragment;
        this.managerActivityHandler = managerActivityHandler;
        this.queryDialog = queryDialog;
        this.commandManager = commandManager;
    }

    @Override
    public AlertDialog createAlertDialog() {
        return new android.support.v7.app.AlertDialog.Builder(fragment.getActivity()).setTitle("Сообщние")
                .setMessage("Вы уверены, что хотите отменить текущий мягкий чек?")
                .setPositiveButton(OK, (dialogInterface, i) -> {
                    SharedPreferences sPref = fragment.getActivity().getSharedPreferences(PREF, Context.MODE_PRIVATE);
                    PreferencesManager prefManager = PreferencesManagerInit.preferencesManager(sPref);

                    CommandCancelSoftCheckDTO cmdCancelSCDTO = CommandCancelSoftCheckDTOInit.commandCancelSoftCheckDTO(
                            prefManager.load(PreferencesManagerType.USER_IDENT_MANAGER),
                            prefManager.load(PreferencesManagerType.CARD_CODE_MANAGER));
                    CommandManagerAsyncTaskDTO cmdManagerATDTO =
                            CommandManagerAsyncTaskDTOInit.commandManagerAsyncTaskDTO(commandManager,
                                    CommandTypeEnum.CANCEL_SOFT_CHECK, cmdCancelSCDTO);
                    CommandManagerAsyncTask cmdManagerAT = new CommandManagerAsyncTask(managerActivityHandler,
                            queryDialog);
                    cmdManagerAT.execute(cmdManagerATDTO);
                    dialogInterface.dismiss();
                })
                .setNegativeButton(NEGATIVE, (dialogInterface, i) -> dialogInterface.dismiss())
                .create();
    }
}
