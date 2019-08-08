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

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.support.v4.app.ActivityCompat;

import ru.viise.lightsearch.data.ButtonContentEnum;

public class RequestPhonePermissionAlertDialogCreatorDefaultImpl implements RequestPhonePermissionAlertDialogCreator {

    private final int PHONE_STATE_CODE = 1;
    private final String OK       = ButtonContentEnum.POSITIVE_BUTTON.stringValue();
    private final String NEGATIVE = ButtonContentEnum.NEGATIVE_BUTTON.stringValue();

    private final Activity activity;

    public RequestPhonePermissionAlertDialogCreatorDefaultImpl(Activity activity) {
        this.activity = activity;
    }

    @Override
    public AlertDialog createAlertDialog() {
        return new android.app.AlertDialog.Builder(activity).setTitle("Запрос на права").setMessage("Нужны права на отправку IMEI.")
                .setPositiveButton(OK, (dialogInterface, i) -> {
                    ActivityCompat.requestPermissions(activity,
                            new String[]{Manifest.permission.READ_PHONE_STATE}, PHONE_STATE_CODE);
                    dialogInterface.dismiss();
                }).setNegativeButton(NEGATIVE, (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(0);
                }).create();
    }
}
