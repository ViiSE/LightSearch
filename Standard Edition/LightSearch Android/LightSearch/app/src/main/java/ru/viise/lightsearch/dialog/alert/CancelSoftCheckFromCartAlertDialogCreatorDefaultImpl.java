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
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.View;

import ru.viise.lightsearch.activity.ManagerActivityHandler;
import ru.viise.lightsearch.cmd.CommandTypeEnum;
import ru.viise.lightsearch.cmd.manager.CommandManager;
import ru.viise.lightsearch.cmd.manager.task.CommandManagerAsyncTask;
import ru.viise.lightsearch.data.CommandCancelSoftCheckDTO;
import ru.viise.lightsearch.data.CommandCancelSoftCheckDTOInit;
import ru.viise.lightsearch.data.CommandManagerAsyncTaskDTO;
import ru.viise.lightsearch.data.CommandManagerAsyncTaskDTOInit;
import ru.viise.lightsearch.pref.PreferencesManager;
import ru.viise.lightsearch.pref.PreferencesManagerInit;
import ru.viise.lightsearch.pref.PreferencesManagerType;

public class CancelSoftCheckFromCartAlertDialogCreatorDefaultImpl implements CancelSoftCheckFromCartAlertDialogCreator {

    private final String PREF = "pref";

    private final Fragment fragment;
    private final FragmentActivity activity;
    private final ManagerActivityHandler managerActivityHandler;
    private final CommandManager commandManager;
    private final android.app.AlertDialog queryDialog;

    public CancelSoftCheckFromCartAlertDialogCreatorDefaultImpl(Fragment fragment,
                ManagerActivityHandler managerActivityHandler, CommandManager commandManager,
                android.app.AlertDialog queryDialog) {
        this.fragment = fragment;
        this.activity = this.fragment.getActivity();
        this.managerActivityHandler = managerActivityHandler;
        this.commandManager = commandManager;
        this.queryDialog = queryDialog;
    }

    @Override
    public AlertDialog createAlertDialog() {
        DialogOKCancelContainer dialogOKCancelContainer = DialogOKCancelContainerCreatorInit.
                dialogOKCancelContainerCreator(activity).createDialogOkCancelContainer();

        dialogOKCancelContainer.textViewTitle().setVisibility(View.GONE);
        dialogOKCancelContainer.textViewResult().setText("Вы уверены, что хотите выйти из корзины? Это отменит текущий мягкий чек.");

        AlertDialog dialog = new AlertDialog.Builder(activity).setView(dialogOKCancelContainer.dialogOKCancelView()).create();

        dialogOKCancelContainer.buttonOK().setOnClickListener(viewOK -> {
            SharedPreferences sPref = fragment.getActivity().getSharedPreferences(PREF, Context.MODE_PRIVATE);
            PreferencesManager prefManager = PreferencesManagerInit.preferencesManager(sPref);

            CommandCancelSoftCheckDTO cmdCancelSCDTO = CommandCancelSoftCheckDTOInit.commandCancelSoftCheckDTO(
                    prefManager.load(PreferencesManagerType.USER_IDENT_MANAGER),
                    prefManager.load(PreferencesManagerType.CARD_CODE_MANAGER),
                    true);
            CommandManagerAsyncTaskDTO cmdManagerATDTO =
                    CommandManagerAsyncTaskDTOInit.commandManagerAsyncTaskDTO(commandManager,
                            CommandTypeEnum.CANCEL_SOFT_CHECK, cmdCancelSCDTO);
            CommandManagerAsyncTask cmdManagerAT = new CommandManagerAsyncTask(managerActivityHandler,
                    queryDialog);
            cmdManagerAT.execute(cmdManagerATDTO);

            dialog.dismiss();
        });

        dialogOKCancelContainer.buttonCancel().setOnClickListener(viewCancel -> dialog.dismiss());
        AlertDialogUtil.setTransparentBackground(dialog);

        return dialog;
    }
}
