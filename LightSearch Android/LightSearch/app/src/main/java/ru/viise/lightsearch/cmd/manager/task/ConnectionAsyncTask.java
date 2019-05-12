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

package ru.viise.lightsearch.cmd.manager.task;

import android.app.AlertDialog;
import android.os.AsyncTask;

import ru.viise.lightsearch.activity.ManagerActivityConnectionHandler;
import ru.viise.lightsearch.cmd.manager.CommandManager;

public class ConnectionAsyncTask extends AsyncTask<Void, Void, String> {

    private final ManagerActivityConnectionHandler managerActivityConnectionHandler;
    private final CommandManager commandManager;
    private final AlertDialog spotsDialog;

    public ConnectionAsyncTask(ManagerActivityConnectionHandler managerActivityConnectionHandler,
               CommandManager commandManager, AlertDialog spotsDialog) {
        this.managerActivityConnectionHandler = managerActivityConnectionHandler;
        this.commandManager = commandManager;
        this.spotsDialog = spotsDialog;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        spotsDialog.show();
    }

    @Override
    protected String doInBackground(Void... voids) {
        return commandManager.createClientHandler();
    }

    @Override
    protected void onPostExecute(String message) {
        super.onPostExecute(message);
        spotsDialog.dismiss();
        managerActivityConnectionHandler.handleConnectionResult(message);
    }
}
