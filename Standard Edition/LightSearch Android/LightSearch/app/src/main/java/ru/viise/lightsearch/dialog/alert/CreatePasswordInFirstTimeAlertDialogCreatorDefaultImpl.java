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

import ru.viise.lightsearch.R;
import ru.viise.lightsearch.data.AuthorizationPreferenceEnum;
import ru.viise.lightsearch.data.ButtonContentEnum;
import ru.viise.lightsearch.data.CreatePasswordInFirstTimeAlertDialogCreatorDTO;

public class CreatePasswordInFirstTimeAlertDialogCreatorDefaultImpl implements CreatePasswordInFirstTimeAlertDialogCreator {

    private final String FIRST_TIME = AuthorizationPreferenceEnum.FIRST_TIME.stringValue();
    private final String SUPERUSER  = AuthorizationPreferenceEnum.SUPERUSER.stringValue();
    private final String TRUE       = "True";
    private final String OK         = ButtonContentEnum.POSITIVE_BUTTON.stringValue();

    private final CreatePasswordInFirstTimeAlertDialogCreatorDTO creatorDTO;
    private final LayoutInflater inflater;
    private final Activity rootActivity;
    private final SharedPreferences sPref;

    public CreatePasswordInFirstTimeAlertDialogCreatorDefaultImpl(
            CreatePasswordInFirstTimeAlertDialogCreatorDTO creatorDTO) {
        this.creatorDTO = creatorDTO;
        inflater = this.creatorDTO.alertDialogCreatorDTO().inflater();
        rootActivity = this.creatorDTO.alertDialogCreatorDTO().rootActivity();
        sPref = this.creatorDTO.alertDialogCreatorDTO().sharedPreferences();
    }

    @Override
    public AlertDialog createAlertDialog() {
        View settingsViewCreate = inflater.inflate(R.layout.dialog_settings, null);
        AlertDialog.Builder dialogSettingsCreate =
                new AlertDialog.Builder(rootActivity);
        dialogSettingsCreate.setView(settingsViewCreate);
        final EditText userInputCreate = settingsViewCreate.findViewById(R.id.editTextPasswordSettings);
        dialogSettingsCreate
                .setTitle("Создать пароль администратора")
                .setCancelable(false)
                .setPositiveButton(OK,
                        (dialog, id) -> {
                            SharedPreferences.Editor ed = sPref.edit();
                            ed.putString(SUPERUSER,
                                    creatorDTO.hashAlgorithms().sha256(userInputCreate.getText().toString()));
                            ed.putString(FIRST_TIME, TRUE);
                            ed.apply();
                            userInputCreate.setText("");
                            dialog.dismiss();
                        });
        return dialogSettingsCreate.create();
    }
}
