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

package ru.viise.lightsearch.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.List;

import ru.viise.lightsearch.R;
import ru.viise.lightsearch.activity.result.holder.ResultCommandHolderUI;
import ru.viise.lightsearch.activity.result.holder.ResultCommandUICreator;
import ru.viise.lightsearch.activity.result.holder.ResultCommandUICreatorInit;
import ru.viise.lightsearch.cmd.CommandTypeEnum;
import ru.viise.lightsearch.cmd.manager.CommandManager;
import ru.viise.lightsearch.cmd.manager.CommandManagerInit;
import ru.viise.lightsearch.cmd.manager.task.CommandManagerAsyncTask;
import ru.viise.lightsearch.cmd.manager.task.ConnectionAsyncTask;
import ru.viise.lightsearch.cmd.result.CommandResult;
import ru.viise.lightsearch.data.AuthorizationDTO;
import ru.viise.lightsearch.data.AuthorizationDTOInit;
import ru.viise.lightsearch.data.ClientHandlerCreatorDTO;
import ru.viise.lightsearch.data.ClientHandlerCreatorDTOInit;
import ru.viise.lightsearch.data.CommandAuthorizationDTO;
import ru.viise.lightsearch.data.CommandManagerAsyncTaskDTO;
import ru.viise.lightsearch.data.CommandManagerAsyncTaskDTOInit;
import ru.viise.lightsearch.data.ConnectionDTO;
import ru.viise.lightsearch.data.ReconnectDTO;
import ru.viise.lightsearch.data.ScanType;
import ru.viise.lightsearch.data.SearchRecord;
import ru.viise.lightsearch.data.SoftCheckRecord;
import ru.viise.lightsearch.data.creator.CommandAuthorizationDTOCreator;
import ru.viise.lightsearch.data.creator.CommandAuthorizationDTOCreatorInit;
import ru.viise.lightsearch.dialog.alert.ErrorAlertDialogCreator;
import ru.viise.lightsearch.dialog.alert.ErrorAlertDialogCreatorInit;
import ru.viise.lightsearch.dialog.alert.NoResultAlertDialogCreator;
import ru.viise.lightsearch.dialog.alert.NoResultAlertDialogCreatorInit;
import ru.viise.lightsearch.dialog.alert.OneResultAlertDialogCreator;
import ru.viise.lightsearch.dialog.alert.OneResultAlertDialogCreatorInit;
import ru.viise.lightsearch.dialog.alert.SuccessAlertDialogCreator;
import ru.viise.lightsearch.dialog.alert.SuccessAlertDialogCreatorInit;
import ru.viise.lightsearch.dialog.spots.SpotsDialogCreatorInit;
import ru.viise.lightsearch.exception.FindableException;
import ru.viise.lightsearch.find.ImplFinder;
import ru.viise.lightsearch.find.ImplFinderFragmentFromActivityDefaultImpl;
import ru.viise.lightsearch.fragment.IAuthorizationFragment;
import ru.viise.lightsearch.fragment.IContainerFragment;
import ru.viise.lightsearch.fragment.transaction.FragmentTransactionManager;
import ru.viise.lightsearch.fragment.transaction.FragmentTransactionManagerInit;
import ru.viise.lightsearch.pref.PreferencesManager;
import ru.viise.lightsearch.pref.PreferencesManagerInit;
import ru.viise.lightsearch.pref.PreferencesManagerType;
import ru.viise.lightsearch.request.PhonePermission;
import ru.viise.lightsearch.request.PhonePermissionInit;

public class ManagerActivity extends AppCompatActivity implements ManagerActivityConnectionHandler, ManagerActivityHandler, ManagerActivityUI {

    private String IMEI;
    private AlertDialog authDialog;
    private AlertDialog connectDialog;
    private CommandManager commandManager;
    private ScanType scanType;
    private ResultCommandHolderUI commandHolderUI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if(ContextCompat.checkSelfPermission(ManagerActivity.this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED)
            IMEI = tm.getDeviceId();
        else
            reqPhonePermission();

        connectDialog = SpotsDialogCreatorInit.spotsDialogCreator(ManagerActivity.this, R.string.spots_dialog_connect)
                .create();
        authDialog = SpotsDialogCreatorInit.spotsDialogCreator(ManagerActivity.this, R.string.spots_dialog_auth)
                .create();

        ResultCommandUICreator resCmdUiCr = ResultCommandUICreatorInit.resultCommandUICreator(this);
        commandHolderUI = resCmdUiCr.createResultCommandHolderUI();

        doAuthorizationFragmentTransaction();
    }

    @Override
    public void onBackPressed() {
        try {
            ImplFinder<OnBackPressedListener> finder = new ImplFinderFragmentFromActivityDefaultImpl<>(this);
            OnBackPressedListener backPressedListener = finder.findImpl(OnBackPressedListener.class);
            backPressedListener.onBackPressed();
        }
        catch(FindableException ex) { super.onBackPressed(); }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if(scanningResult.getContents() != null) {
            String scanContent = scanningResult.getContents();
            IContainerFragment containerFragment = getContainerFragment();

            if (containerFragment != null)
                if(scanType == ScanType.SEARCH)
                    containerFragment.setSearchBarcode(scanContent);
                else if(scanType == ScanType.OPEN_SOFT_CHECK)
                    containerFragment.setCardCode(scanContent);
                else if(scanType == ScanType.SEARCH_SOFT_CHECK)
                    containerFragment.setSoftCheckBarcode(scanContent);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if(view instanceof EditText) {
                Rect r = new Rect();
                view.getGlobalVisibleRect(r);
                int rawX = (int)event.getRawX();
                int rawY = (int)event.getRawY();
                if(!r.contains(rawX, rawY)) {
                    view.clearFocus();
                    KeyboardHideToolInit.keyboardHideTool(this).hideKeyboard();
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    private void reqPhonePermission() {
        PhonePermission phonePermission = PhonePermissionInit.phonePermission(this);
        phonePermission.requestPhonePermission();
    }

    private void doAuthorizationFragmentTransaction() {
        FragmentTransactionManager fragmentTransactionManager =
                FragmentTransactionManagerInit.fragmentTransactionManager(this);
        fragmentTransactionManager.doAuthorizationFragmentTransaction();
    }

    public void doContainerFragmentTransaction(String[] skladArr, String[] TKArr) {
        FragmentTransactionManager fragmentTransactionManager =
                FragmentTransactionManagerInit.fragmentTransactionManager(this);
        fragmentTransactionManager.doContainerFragmentTransaction(skladArr, TKArr);
    }

    public void doContainerFragmentTransactionFromCart() {
        FragmentTransactionManager fragmentTransactionManager =
                FragmentTransactionManagerInit.fragmentTransactionManager(this);
        fragmentTransactionManager.doContainerFragmentTransactionFromCart();
    }

    public void doResultSearchFragmentTransaction(String title, List<SearchRecord> searchRecords) {
        FragmentTransactionManager fragmentTransactionManager =
                FragmentTransactionManagerInit.fragmentTransactionManager(this);
        fragmentTransactionManager.doResultSearchFragmentTransaction(title, searchRecords);
    }

    public void doCartFragmentTransaction(List<SoftCheckRecord> cartRecords) {
        IContainerFragment containerFragment = getContainerFragment();
        containerFragment.switchToOpenSoftCheckFragment();
        FragmentTransactionManager fragmentTransactionManager =
                FragmentTransactionManagerInit.fragmentTransactionManager(this);
        fragmentTransactionManager.doCartFragmentTransaction(cartRecords);
    }

    public void callDialogError(String errorMessage) {
        ErrorAlertDialogCreator errADCr =
                ErrorAlertDialogCreatorInit.errorAlertDialogCreator(this, errorMessage);
        errADCr.createAlertDialog().show();
    }

    public void callDialogSuccess(String message) {
        SuccessAlertDialogCreator successADCr =
                SuccessAlertDialogCreatorInit.successAlertDialogCreator(this, message);
        successADCr.createAlertDialog().show();
    }

    public void callDialogNoResult() {
        String message = "Запрос не дал результата.";
        NoResultAlertDialogCreator noResADCr =
                NoResultAlertDialogCreatorInit.noResultAlertDialogCreator(this, message);
        noResADCr.createAlertDialog().show();
    }

    public void callDialogOneResult(SearchRecord searchRecord) {
        OneResultAlertDialogCreator oneResADCr =
                OneResultAlertDialogCreatorInit.oneResultAlertDialogCreator(this, searchRecord);
        android.support.v7.app.AlertDialog oneResAD = oneResADCr.createAlertDialog();
        oneResAD.setCanceledOnTouchOutside(false);
        oneResAD.show();
    }

    public IContainerFragment getContainerFragment() {
        ImplFinder<IContainerFragment> finder = new ImplFinderFragmentFromActivityDefaultImpl<>(this);
        try { return finder.findImpl(IContainerFragment.class); }
        catch(FindableException ignore) { return null; }
    }

    @Override
    public void connect(ConnectionDTO connDTO) {
        ClientHandlerCreatorDTO clHandlerCrDTO =
                ClientHandlerCreatorDTOInit.clientHandlerCreatorDTO(IMEI, connDTO);
        commandManager = CommandManagerInit.commandManager(clHandlerCrDTO);
        ConnectionAsyncTask connAT = new ConnectionAsyncTask(this,
                commandManager, connectDialog, null);
        connAT.execute();
    }

    @Override
    public void reconnect(ConnectionDTO connDTO, ReconnectDTO reconnectDTO) {
        commandManager.closeConnection();
        ConnectionAsyncTask connAT = new ConnectionAsyncTask(this,
                commandManager, connectDialog, reconnectDTO);
        connAT.execute();
    }

    @Override
    public void setScanType(ScanType type) {
        scanType = type;
    }

    @Override
    public void handleConnectionResult(String message, ReconnectDTO reconnectDTO) {
        if(message == null) {
            AuthorizationDTO authDTO;
            try {
                ImplFinder<IAuthorizationFragment> finder = new ImplFinderFragmentFromActivityDefaultImpl<>(this);
                IAuthorizationFragment authFragment = finder.findImpl(IAuthorizationFragment.class);
                authDTO = authFragment.authorizationData();
            }
            catch(FindableException ignore) {
                SharedPreferences sPref = this.getSharedPreferences("pref", Context.MODE_PRIVATE);
                PreferencesManager prefManager = PreferencesManagerInit.preferencesManager(sPref);
                authDTO = AuthorizationDTOInit.authorizationDTO(
                        prefManager.load(PreferencesManagerType.USERNAME_MANAGER),
                        prefManager.load(PreferencesManagerType.PASS_MANAGER),
                        prefManager.load(PreferencesManagerType.USER_IDENT_MANAGER));

            }
            CommandAuthorizationDTOCreator cmdAuthDTOCreator =
                    CommandAuthorizationDTOCreatorInit.commandAuthorizationDTOCreator(IMEI, authDTO, reconnectDTO);
            CommandAuthorizationDTO cmdAuthDTO = cmdAuthDTOCreator.createCommandDTO();

            CommandTypeEnum cmdTypeEnum;
            if(reconnectDTO != null)
                cmdTypeEnum = CommandTypeEnum.RECONNECT;
            else
                cmdTypeEnum = CommandTypeEnum.AUTHORIZATION;

            CommandManagerAsyncTaskDTO cmdManagerATDTO =
                    CommandManagerAsyncTaskDTOInit.commandManagerAsyncTaskDTO(commandManager,
                            cmdTypeEnum, cmdAuthDTO);

            CommandManagerAsyncTask cmdManagerAT = new CommandManagerAsyncTask(this, authDialog);
            cmdManagerAT.execute(cmdManagerATDTO);
        }
        else
            callDialogError(message);
    }

    @Override
    public CommandManager commandManager() {
        return commandManager;
    }

    @Override
    public void handleResult(CommandResult commandResult) {
        if(commandHolderUI.get(commandResult) != null)
            commandHolderUI.get(commandResult).apply(commandResult);
    }
}
