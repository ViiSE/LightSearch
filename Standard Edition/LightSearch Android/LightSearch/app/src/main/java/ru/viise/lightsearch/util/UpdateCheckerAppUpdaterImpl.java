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

package ru.viise.lightsearch.util;

import android.app.Activity;

import com.github.javiersantos.appupdater.AppUpdater;
import com.github.javiersantos.appupdater.enums.Display;
import com.github.javiersantos.appupdater.enums.UpdateFrom;

import ru.viise.lightsearch.R;

public class UpdateCheckerAppUpdaterImpl implements UpdateChecker {

    private final Activity activity;

    public UpdateCheckerAppUpdaterImpl(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void checkUpdate() {
        new AppUpdater(activity)
                .setUpdateFrom(UpdateFrom.JSON)
                .setUpdateJSON(activity.getString(R.string.update_url))
                .setDisplay(Display.DIALOG)
                .showAppUpdated(true)
                .start();
    }
}
