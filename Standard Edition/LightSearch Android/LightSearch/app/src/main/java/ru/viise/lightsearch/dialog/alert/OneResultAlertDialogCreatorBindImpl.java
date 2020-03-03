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

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.text.HtmlCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;

import ru.viise.lightsearch.R;
import ru.viise.lightsearch.activity.ManagerActivity;
import ru.viise.lightsearch.activity.ManagerActivityHandler;
import ru.viise.lightsearch.activity.ManagerActivityUI;
import ru.viise.lightsearch.cmd.CommandTypeEnum;
import ru.viise.lightsearch.cmd.manager.task.CommandManagerAsyncTask;
import ru.viise.lightsearch.data.BindRecord;
import ru.viise.lightsearch.data.CommandBindDTO;
import ru.viise.lightsearch.data.CommandManagerAsyncTaskDTO;
import ru.viise.lightsearch.data.CommandManagerAsyncTaskDTOInit;
import ru.viise.lightsearch.data.creator.CommandBindDTOCreator;
import ru.viise.lightsearch.data.creator.CommandBindDTOCreatorInit;
import ru.viise.lightsearch.pref.UserIdentifierPreferencesManager;
import ru.viise.lightsearch.pref.UserIdentifierPreferencesManagerInit;

public class OneResultAlertDialogCreatorBindImpl implements OneResultAlertDialogCreator {

    private final Activity activity;
    private final BindRecord bindRecord;
    private final android.app.AlertDialog queryDialog;
    private final String factoryBarcode;

    public OneResultAlertDialogCreatorBindImpl(
            Activity activity,
            BindRecord bindRecord,
            android.app.AlertDialog queryDialog,
            String factoryBarcode) {
        this.activity = activity;
        this.bindRecord = bindRecord;
        this.queryDialog = queryDialog;
        this.factoryBarcode = factoryBarcode;
    }

    @Override
    public AlertDialog create() {
        String id = "<b>" + activity.getString(R.string.dialog_res_prod_id) + "</b>";
        String name = "<b>" + activity.getString(R.string.dialog_res_prod_name) + "</b>";

        String result = id + ": " + bindRecord.barcode() + "<br>"+
                        name + ": " + bindRecord.name() + "<br>";

        DialogOKCancelContainer okCancelContainer = DialogOKCancelContainerCreatorInit
                .dialogOKCancelContainerCreator(activity)
                .create();


        okCancelContainer.textViewResult().setText(HtmlCompat.fromHtml(result, HtmlCompat.FROM_HTML_MODE_LEGACY));
        okCancelContainer.textViewTitle().setText(activity.getString(R.string.dialog_res_bind));

        AlertDialog dialog = new AlertDialog.Builder(activity)
                .setView(okCancelContainer.dialogOKCancelView())
                .create();

        SharedPreferences sPref = activity.getSharedPreferences("pref", Context.MODE_PRIVATE);
        UserIdentifierPreferencesManager userPrefManager =
                UserIdentifierPreferencesManagerInit.userIdentifierPreferencesManager(sPref);

        okCancelContainer.buttonOK().setOnClickListener(viewOK -> {
            CommandBindDTOCreator cmdBindDTOCr =
                    CommandBindDTOCreatorInit.commandBindDTOCreator(
                            bindRecord.barcode(), factoryBarcode, userPrefManager.loadUserIdentifier(), 2);
            CommandBindDTO cmdBindDTO = cmdBindDTOCr.create();
            CommandManagerAsyncTaskDTO cmdManagerATDTO =
                    CommandManagerAsyncTaskDTOInit.commandManagerAsyncTaskDTO(
                            ((ManagerActivityUI) activity).commandManager(),
                            CommandTypeEnum.BIND,
                            cmdBindDTO);
            CommandManagerAsyncTask cmdManagerAT = new CommandManagerAsyncTask(
                    (ManagerActivityHandler) activity, queryDialog);
            cmdManagerAT.execute(cmdManagerATDTO);
            dialog.dismiss();});

        okCancelContainer.buttonCancel().setOnClickListener(viewOK -> dialog.dismiss());

        AlertDialogUtil.setTransparentBackground(dialog);

        return dialog;
    }
}
