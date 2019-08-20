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
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;

import ru.viise.lightsearch.data.ButtonContentEnum;

public class ErrorAlertDialogCreatorDefaultImpl implements ErrorAlertDialogCreator {

    private final String OK = ButtonContentEnum.POSITIVE_BUTTON.stringValue();

    private final Activity rootActivity;
    private final String errorMessage;

    public ErrorAlertDialogCreatorDefaultImpl(Activity rootActivity, String errorMessage) {
        this.rootActivity = rootActivity;
        this.errorMessage = errorMessage;
    }

    @Override
    public AlertDialog createAlertDialog() {
        return new android.support.v7.app.AlertDialog.Builder(rootActivity).setTitle("Ошибка").setMessage(errorMessage)
                .setPositiveButton(OK, (dialogInterface, i) -> dialogInterface.dismiss())
                .setOnKeyListener((dialogInterface, keyCode, event) -> {
                    if(keyCode == KeyEvent.KEYCODE_BACK) {
                        dialogInterface.dismiss();
                    }
                    return true;
                }).create();
    }
}
