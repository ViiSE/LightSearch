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

import ru.viise.lightsearch.activity.ManagerActivityHandler;
import ru.viise.lightsearch.cmd.result.CommandResult;
import ru.viise.lightsearch.data.CommandManagerAsyncTaskDTO;

public class CommandManagerAsyncTask extends AsyncTask<CommandManagerAsyncTaskDTO, Void, CommandResult> {

    private final ManagerActivityHandler managerActivityHandler;
    private final AlertDialog spotsDialog;

    public CommandManagerAsyncTask(ManagerActivityHandler managerActivityHandler, AlertDialog spotsDialog) {
        this.managerActivityHandler = managerActivityHandler;
        this.spotsDialog = spotsDialog;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        spotsDialog.show();
    }

    @Override
    protected CommandResult doInBackground(CommandManagerAsyncTaskDTO... commandManagerAsyncTaskDTOS) {
        CommandManagerAsyncTaskDTO cmdMATDTO = commandManagerAsyncTaskDTOS[0];
        return cmdMATDTO.commandManager().doCommand(cmdMATDTO.type(), cmdMATDTO.commandDTO());
    }

    @Override
    protected void onPostExecute(CommandResult cmdRes) {
        super.onPostExecute(cmdRes);
        spotsDialog.dismiss();
        managerActivityHandler.handleResult(cmdRes);
    }
}
