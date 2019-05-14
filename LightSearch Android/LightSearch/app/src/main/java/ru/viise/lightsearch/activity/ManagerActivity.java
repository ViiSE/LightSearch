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
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.List;

import dmax.dialog.SpotsDialog;
import ru.viise.lightsearch.R;
import ru.viise.lightsearch.cmd.CommandTypeEnum;
import ru.viise.lightsearch.cmd.manager.CommandManager;
import ru.viise.lightsearch.cmd.manager.CommandManagerInit;
import ru.viise.lightsearch.cmd.manager.task.CommandManagerAsyncTask;
import ru.viise.lightsearch.cmd.manager.task.ConnectionAsyncTask;
import ru.viise.lightsearch.cmd.result.AuthorizationCommandResult;
import ru.viise.lightsearch.cmd.result.CancelSoftCheckCommandResult;
import ru.viise.lightsearch.cmd.result.CommandResult;
import ru.viise.lightsearch.cmd.result.OpenSoftCheckCommandResult;
import ru.viise.lightsearch.cmd.result.SearchCommandResult;
import ru.viise.lightsearch.data.AuthorizationDTO;
import ru.viise.lightsearch.data.ClientHandlerCreatorDTO;
import ru.viise.lightsearch.data.ClientHandlerCreatorDTOInit;
import ru.viise.lightsearch.data.CommandAuthorizationDTO;
import ru.viise.lightsearch.data.CommandManagerAsyncTaskDTO;
import ru.viise.lightsearch.data.CommandManagerAsyncTaskDTOInit;
import ru.viise.lightsearch.data.ConnectionDTO;
import ru.viise.lightsearch.data.SearchRecordDTO;
import ru.viise.lightsearch.data.ScanType;
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
import ru.viise.lightsearch.find.IAuthorizationFragmentImplFinder;
import ru.viise.lightsearch.find.IAuthorizationFragmentImplFinderInit;
import ru.viise.lightsearch.find.IContainerFragmentImplFinder;
import ru.viise.lightsearch.find.IContainerFragmentImplFinderInit;
import ru.viise.lightsearch.find.OnBackPressedListenerImplFinder;
import ru.viise.lightsearch.find.OnBackPressedListenerImplFinderInit;
import ru.viise.lightsearch.fragment.IAuthorizationFragment;
import ru.viise.lightsearch.fragment.IContainerFragment;
import ru.viise.lightsearch.fragment.transaction.FragmentTransactionManager;
import ru.viise.lightsearch.fragment.transaction.FragmentTransactionManagerInit;
import ru.viise.lightsearch.fragment.util.SearchResultTitleCreator;
import ru.viise.lightsearch.fragment.util.SearchResultTitleCreatorInit;
import ru.viise.lightsearch.request.PhonePermission;
import ru.viise.lightsearch.request.PhonePermissionInit;

public class ManagerActivity extends AppCompatActivity implements ManagerActivityConnectionHandler, ManagerActivityHandler, ManagerActivityUI {

    private String IMEI;
    private AlertDialog authDialog;
    private AlertDialog connectDialog;
    private CommandManager commandManager;
    private ScanType scanType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if(ContextCompat.checkSelfPermission(ManagerActivity.this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED)
            IMEI = tm.getDeviceId();
        else
            reqPhonePermission();

        connectDialog = new SpotsDialog.Builder().setContext(ManagerActivity.this).setMessage("Подключение").setCancelable(false).build();
        authDialog = new SpotsDialog.Builder().setContext(ManagerActivity.this).setMessage("Авторизация").setCancelable(false).build();

        doAuthorizationFragmentTransaction();
    }

    @Override
    public void onBackPressed() {
        OnBackPressedListenerImplFinder onBackPressedImplFinder =
                OnBackPressedListenerImplFinderInit.onBackPressedListenerImplFinder(this);
        OnBackPressedListener backPressedListener = onBackPressedImplFinder.findImpl();

        if (backPressedListener != null)
            backPressedListener.onBackPressed();
        else
            super.onBackPressed();
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

        }
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

    private void doContainerFragmentTransaction(String[] skladArr, String[] TKArr) {
        FragmentTransactionManager fragmentTransactionManager =
                FragmentTransactionManagerInit.fragmentTransactionManager(this);
        fragmentTransactionManager.doContainerFragmentTransaction(skladArr, TKArr);
    }

    private void doResultSearchFragmentTransaction(String title, List<SearchRecordDTO> searchRecords) {
        FragmentTransactionManager fragmentTransactionManager =
                FragmentTransactionManagerInit.fragmentTransactionManager(this);
        fragmentTransactionManager.doResultSearchFragmentTransaction(title, searchRecords);
    }

    private void callDialogError(String errorMessage) {
        ErrorAlertDialogCreator errADCr =
                ErrorAlertDialogCreatorInit.errorAlertDialogCreator(this, errorMessage);
        errADCr.createAlertDialog().show();
    }

    private void callDialogSuccess(String message) {
        SuccessAlertDialogCreator successADCr =
                SuccessAlertDialogCreatorInit.successAlertDialogCreator(this, message);
        successADCr.createAlertDialog().show();
    }

    private void callDialogNoResult() {
        String message = "Запрос не дал результата.";
        NoResultAlertDialogCreator noResADCr =
                NoResultAlertDialogCreatorInit.noResultAlertDialogCreator(this, message);
        noResADCr.createAlertDialog().show();
    }

    private void callDialogOneResult(SearchRecordDTO searchRecord) {
        OneResultAlertDialogCreator oneResADCr =
                OneResultAlertDialogCreatorInit.oneResultAlertDialogCreator(this, searchRecord);
        oneResADCr.createAlertDialog().show();
    }

    private IContainerFragment getContainerFragment() {
        IContainerFragmentImplFinder containerFragmentImplFinder =
                IContainerFragmentImplFinderInit.containerFragmentImplFinder(this);
        return containerFragmentImplFinder.findImpl();
    }

    @Override
    public void connect(ConnectionDTO connDTO) {
        ClientHandlerCreatorDTO clHandlerCrDTO =
                ClientHandlerCreatorDTOInit.clientHandlerCreatorDTO(IMEI, connDTO);
        commandManager = CommandManagerInit.commandManager(clHandlerCrDTO);
        ConnectionAsyncTask connAT = new ConnectionAsyncTask(this, commandManager, connectDialog);
        connAT.execute();
    }

    @Override
    public void setScanType(ScanType type) {
        scanType = type;
    }

    @Override
    public void handleConnectionResult(String message) {
        if(message == null) {
            IAuthorizationFragmentImplFinder authFragmentFinder =
                    IAuthorizationFragmentImplFinderInit.authorizationFragmentImplFinder(this);
            IAuthorizationFragment authFragment = authFragmentFinder.findImpl();

            AuthorizationDTO authDTO = authFragment.authorizationData();

            CommandAuthorizationDTOCreator cmdAuthDTOCreator =
                    CommandAuthorizationDTOCreatorInit.commandAuthorizationDTOCreator(IMEI, authDTO);
            CommandAuthorizationDTO cmdAuthDTO = cmdAuthDTOCreator.createCommandDTO();
            CommandManagerAsyncTaskDTO cmdManagerATDTO =
                    CommandManagerAsyncTaskDTOInit.commandManagerAsyncTaskDTO(commandManager,
                            CommandTypeEnum.AUTHORIZATION, cmdAuthDTO);
            CommandManagerAsyncTask cmdManagerAT = new CommandManagerAsyncTask(this, authDialog);
            cmdManagerAT.execute(cmdManagerATDTO);
        }
        else {
            callDialogError(message);
        }
    }

    @Override
    public CommandManager commandManager() {
        return commandManager;
    }

    @Override
    public void handleResult(CommandResult commandResult) {
        if(commandResult instanceof AuthorizationCommandResult) {
            AuthorizationCommandResult authCmdRes = (AuthorizationCommandResult)commandResult;
            if(authCmdRes.isDone()) {
                callDialogSuccess(authCmdRes.message());
                doContainerFragmentTransaction(authCmdRes.skladList(), authCmdRes.TKList());
            }
            else {
                callDialogError(authCmdRes.message());
            }
        }
        else if(commandResult instanceof SearchCommandResult) {
            SearchCommandResult searchCmdRes = (SearchCommandResult)commandResult;
            if (searchCmdRes.records().size() != 0) {
                SearchResultTitleCreator searchResTitleCr =
                        SearchResultTitleCreatorInit.searchResultTitleCreator(searchCmdRes.podrazdelenie(),
                                searchCmdRes.records().get(0).id());
                String title = searchResTitleCr.createTitle();
                if (searchCmdRes.records().size() == 1)
                    callDialogOneResult(searchCmdRes.records().get(0));
                else
                    doResultSearchFragmentTransaction(title, searchCmdRes.records());
            } else
                callDialogNoResult();
        }
        else if(commandResult instanceof OpenSoftCheckCommandResult) {
            OpenSoftCheckCommandResult openSCCmdRes = (OpenSoftCheckCommandResult)commandResult;
            if(openSCCmdRes.isDone()) {
                callDialogSuccess(openSCCmdRes.message());
                IContainerFragment containerFragment = getContainerFragment();

                if(containerFragment != null)
                    containerFragment.switchToSoftCheckFragment();
            }
            else {
                callDialogError(openSCCmdRes.message());
            }
        }
        else if(commandResult instanceof CancelSoftCheckCommandResult) {
            CancelSoftCheckCommandResult cancelSCCmdRes = (CancelSoftCheckCommandResult)commandResult;
            if(cancelSCCmdRes.isDone()) {
                callDialogSuccess(cancelSCCmdRes.message());
                IContainerFragment containerFragment = getContainerFragment();

                if(containerFragment != null)
                    containerFragment.switchToOpenSoftCheckFragment();
            }
            else {
                callDialogError(cancelSCCmdRes.message());
            }
        }
    }
}
