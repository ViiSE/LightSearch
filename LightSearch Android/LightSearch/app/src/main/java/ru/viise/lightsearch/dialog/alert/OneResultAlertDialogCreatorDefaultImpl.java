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
import ru.viise.lightsearch.data.SearchRecordDTO;

public class OneResultAlertDialogCreatorDefaultImpl implements OneResultAlertDialogCreator {

    private final String OK = ButtonContentEnum.POSITIVE_BUTTON.stringValue();

    private final Activity activity;
    private final SearchRecordDTO searchRecord;

    public OneResultAlertDialogCreatorDefaultImpl(Activity activity, SearchRecordDTO searchRecord) {
        this.activity = activity;
        this.searchRecord = searchRecord;
    }

    @Override
    public AlertDialog createAlertDialog() {
        return new android.support.v7.app.AlertDialog.Builder(activity).setTitle("").setMessage(
                "Подразделение: " + searchRecord.subdivision() + "\n" +
                        "ИД: " + searchRecord.id() + "\n" +
                        "Наименование: " + searchRecord.name() + "\n" +
                        "Цена: " + searchRecord.price() + " руб.\n" +
                        "Кол-во: " + searchRecord.amount() + " " + searchRecord.unit() + "\n")
                .setPositiveButton(OK, (dialogInterface, i) -> dialogInterface.dismiss()).create();
    }
}
