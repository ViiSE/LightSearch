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
import ru.viise.lightsearch.data.InputPasswordAlertDialogCreatorDTO;

import static android.view.View.VISIBLE;

public class InputPasswordAlertDialogCreatorDefaultImpl implements InputPasswordAlertDialogCreator {

    private final String SUPERUSER  = AuthorizationPreferenceEnum.SUPERUSER.stringValue();
    private final String OK         = ButtonContentEnum.POSITIVE_BUTTON.stringValue();
    private final String NEGATIVE   = ButtonContentEnum.NEGATIVE_BUTTON.stringValue();

    private final InputPasswordAlertDialogCreatorDTO creatorDTO;
    private final LayoutInflater inflater;
    private final Activity rootActivity;
    private final SharedPreferences sPref;

    public InputPasswordAlertDialogCreatorDefaultImpl(InputPasswordAlertDialogCreatorDTO creatorDTO) {
        this.creatorDTO = creatorDTO;
        inflater = this.creatorDTO.alertDialogCreatorDTO().inflater();
        rootActivity = this.creatorDTO.alertDialogCreatorDTO().rootActivity();
        sPref = this.creatorDTO.alertDialogCreatorDTO().sharedPreferences();
    }

    @Override
    public AlertDialog createAlertDialog() {
        View settingsView = inflater.inflate(R.layout.dialog_settings, null);
        AlertDialog.Builder dialogSettingsPass = new AlertDialog.Builder(rootActivity);
        dialogSettingsPass.setView(settingsView);
        final EditText userInput = settingsView.findViewById(R.id.editTextPasswordSettings);
        dialogSettingsPass
                .setCancelable(false)
                .setPositiveButton(OK,
                        (dialog, id) -> {
                            String password = sPref.getString(SUPERUSER, "");
                            if (creatorDTO.hashAlgorithms().sha256(userInput.getText().toString()).equals(password)) {
                                creatorDTO.twHost().setVisibility(VISIBLE);
                                creatorDTO.twPort().setVisibility(VISIBLE);
                                creatorDTO.etHost().setVisibility(VISIBLE);
                                creatorDTO.etPort().setVisibility(VISIBLE);
                                creatorDTO.bChangePassword().setVisibility(VISIBLE);
                                userInput.setText("");
                                dialog.dismiss();
                            } else {
                                new AlertDialog.Builder(rootActivity).setTitle("Провал").setMessage("Введен неверный пароль!")
                                        .setPositiveButton(OK, (dialogInterface, i) -> {
                                            creatorDTO.cbSettings().setChecked(false);
                                            dialogInterface.dismiss();
                                        }).create().show();
                                userInput.setText("");
                                dialog.dismiss();
                            }
                        })
                .setNegativeButton(NEGATIVE,
                        (dialog, id) -> {
                            creatorDTO.cbSettings().setChecked(false);
                            dialog.dismiss();
                        });
        return dialogSettingsPass.create();
    }
}
