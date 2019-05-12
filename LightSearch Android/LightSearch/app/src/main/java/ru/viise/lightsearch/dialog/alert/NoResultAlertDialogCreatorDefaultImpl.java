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

import ru.viise.lightsearch.data.ButtonContentEnum;

public class NoResultAlertDialogCreatorDefaultImpl implements NoResultAlertDialogCreator {

    private final String OK = ButtonContentEnum.POSITIVE_BUTTON.stringValue();

    private final Activity activity;
    private final String message;

    public NoResultAlertDialogCreatorDefaultImpl(Activity activity, String message) {
        this.activity = activity;
        this.message = message;
    }

    @Override
    public AlertDialog createAlertDialog() {
        return new android.support.v7.app.AlertDialog.Builder(activity).setTitle("Сообщение").setMessage(message)
                .setPositiveButton(OK, (dialogInterface, i) -> dialogInterface.dismiss()).create();
    }
}