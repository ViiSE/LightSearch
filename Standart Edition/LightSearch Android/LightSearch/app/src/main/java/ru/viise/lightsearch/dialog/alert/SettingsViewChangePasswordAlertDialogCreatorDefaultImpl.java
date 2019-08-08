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
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import ru.viise.lightsearch.R;
import ru.viise.lightsearch.data.AuthorizationPreferenceEnum;
import ru.viise.lightsearch.data.ButtonContentEnum;
import ru.viise.lightsearch.data.SettingsViewChangePasswordAlertDialogCreatorDTO;

public class SettingsViewChangePasswordAlertDialogCreatorDefaultImpl implements SettingsViewChangePasswordAlertDialogCreator {

    private final String SUPERUSER = AuthorizationPreferenceEnum.SUPERUSER.stringValue();
    private final String OK        = ButtonContentEnum.POSITIVE_BUTTON.stringValue();
    private final String NEGATIVE  = ButtonContentEnum.NEGATIVE_BUTTON.stringValue();

    private final SettingsViewChangePasswordAlertDialogCreatorDTO creatorDTO;
    private final LayoutInflater inflater;
    private final Activity rootActivity;
    private final SharedPreferences sPref;

    public SettingsViewChangePasswordAlertDialogCreatorDefaultImpl(
            SettingsViewChangePasswordAlertDialogCreatorDTO creatorDTO) {
        this.creatorDTO = creatorDTO;
        inflater = this.creatorDTO.alertDialogCreatorDTO().inflater();
        rootActivity = this.creatorDTO.alertDialogCreatorDTO().rootActivity();
        sPref = this.creatorDTO.alertDialogCreatorDTO().sharedPreferences();
    }

    @Override
    public AlertDialog createAlertDialog() {
        View settingsViewChangePass = inflater.inflate(R.layout.dialog_settings, null);
        AlertDialog.Builder dialogSettingsChangePass =
                new AlertDialog.Builder(rootActivity);
        dialogSettingsChangePass.setView(settingsViewChangePass);
        final EditText userInputChangePass = settingsViewChangePass.findViewById(R.id.editTextPasswordSettings);
        dialogSettingsChangePass
                .setCancelable(true)
                .setPositiveButton(OK,
                        (dialog, id) -> {
                            SharedPreferences.Editor ed = sPref.edit();
                            ed.putString(SUPERUSER, creatorDTO.hashAlgorithms().sha256(userInputChangePass.getText().toString()));
                            ed.apply();
                            userInputChangePass.setText("");
                            Toast t =
                                    Toast.makeText(rootActivity.getApplicationContext(), "Пароль изменен!", Toast.LENGTH_SHORT);
                            t.show();
                            dialog.dismiss();
                        })
                .setNegativeButton(NEGATIVE,
                        (dialog, id) -> dialog.dismiss());
        return dialogSettingsChangePass.create();
    }
}
