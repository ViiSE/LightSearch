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
import ru.viise.lightsearch.data.CartRecord;

public class InfoProductAlertDialogCreatorCartDefaultImpl implements InfoProductAlertDialogCreator {

    private final String OK = ButtonContentEnum.POSITIVE_BUTTON.stringValue();

    private final Activity activity;
    private final CartRecord record;

    public InfoProductAlertDialogCreatorCartDefaultImpl(Activity activity, CartRecord record) {
        this.record = record;
        this.activity = activity;
    }

    @Override
    public AlertDialog createAlertDialog() {
        String message = "ИД: " + record.barcode() + "\n" +
                "Наименование: " + record.name() + "\n" +
                "Цена: " + record.priceWithUnit() + "\n";
        if(record.isConfirmed())
            message += "Общее кол-во: " + record.maxAmountWithUnit();
        else
            message += "Общее кол-во ДО: " + record.oldMaxAmountWithUnit() + "\n" +
                    "Общее кол-во ПОСЛЕ: " + record.maxAmountWithUnit();

        return new android.support.v7.app.AlertDialog.Builder(activity).setTitle("").setMessage(message)
                .setPositiveButton(OK, (dialogInterface, i) -> dialogInterface.dismiss()).create();
    }
}