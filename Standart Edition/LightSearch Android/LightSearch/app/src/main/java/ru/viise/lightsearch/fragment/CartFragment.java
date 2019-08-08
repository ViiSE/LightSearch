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

package ru.viise.lightsearch.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import dmax.dialog.SpotsDialog;
import ru.viise.lightsearch.R;
import ru.viise.lightsearch.activity.ManagerActivityHandler;
import ru.viise.lightsearch.activity.ManagerActivityUI;
import ru.viise.lightsearch.activity.OnBackPressedListener;
import ru.viise.lightsearch.cmd.CommandTypeEnum;
import ru.viise.lightsearch.cmd.manager.CommandManager;
import ru.viise.lightsearch.cmd.manager.task.CommandManagerAsyncTask;
import ru.viise.lightsearch.data.CartRecord;
import ru.viise.lightsearch.data.CommandCloseSoftCheckDTO;
import ru.viise.lightsearch.data.CommandCloseSoftCheckDTOInit;
import ru.viise.lightsearch.data.CommandConfirmCartRecordsDTO;
import ru.viise.lightsearch.data.CommandConfirmCartRecordsDTOInit;
import ru.viise.lightsearch.data.CommandManagerAsyncTaskDTO;
import ru.viise.lightsearch.data.CommandManagerAsyncTaskDTOInit;
import ru.viise.lightsearch.data.DeliveryTypeEnum;
import ru.viise.lightsearch.data.SoftCheckRecord;
import ru.viise.lightsearch.data.UnitsEnum;
import ru.viise.lightsearch.dialog.alert.CancelSoftCheckFromCartAlertDialogCreator;
import ru.viise.lightsearch.dialog.alert.CancelSoftCheckFromCartAlertDialogCreatorInit;
import ru.viise.lightsearch.dialog.alert.InfoProductAlertDialogCreator;
import ru.viise.lightsearch.dialog.alert.InfoProductAlertDialogCreatorInit;
import ru.viise.lightsearch.dialog.alert.UnconfirmedRecordAlertDialogCreator;
import ru.viise.lightsearch.dialog.alert.UnconfirmedRecordAlertDialogCreatorInit;
import ru.viise.lightsearch.fragment.adapter.RecyclerViewAdapter;
import ru.viise.lightsearch.fragment.adapter.SwipeToDeleteCallback;
import ru.viise.lightsearch.fragment.adapter.SwipeToInfoCallback;
import ru.viise.lightsearch.fragment.snackbar.SnackbarSoftCheckCreator;
import ru.viise.lightsearch.fragment.snackbar.SnackbarSoftCheckCreatorInit;
import ru.viise.lightsearch.pref.PreferencesManager;
import ru.viise.lightsearch.pref.PreferencesManagerInit;
import ru.viise.lightsearch.pref.PreferencesManagerType;

public class CartFragment extends Fragment implements ICartFragment, OnBackPressedListener {

    private final String NO                   = DeliveryTypeEnum.NO.stringUIValue();
    private final String DOSTAVKA_SO_SKLADOV  = DeliveryTypeEnum.DOSTAVKA_SO_SKLADOV.stringUIValue();
    private final String SAMOVYVOZ_SO_SKLADOV = DeliveryTypeEnum.SAMOVYVOZ_SO_SKLADOV.stringUIValue();
    private final String SAMOVYVOZ_S_TK       = DeliveryTypeEnum.SAMOVYVOZ_S_TK.stringUIValue();

    private final String PREF = "pref";

    private ManagerActivityHandler managerActivityHandler;
    private CommandManager commandManager;

    private List<SoftCheckRecord> cartRecords;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private CoordinatorLayout coordinatorLayout;
    private TextView tvTotalAmount;
    private Spinner spinnerDeliveryType;
    private AlertDialog queryDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        Button closeSoftCheckButton = view.findViewById(R.id.buttonCloseSoftCheck);
        queryDialog = new SpotsDialog.Builder().setContext(this.getActivity()).setMessage("Выполнение").setCancelable(false).build();

        Animation animAlpha = AnimationUtils.loadAnimation(this.getActivity(), R.anim.alpha);

        recyclerView = view.findViewById(R.id.recyclerViewCart);
        coordinatorLayout = view.findViewById(R.id.coordinatorLayoutCart);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        TextView tvTotalCost = view.findViewById(R.id.textViewCartTotalCostDynamic);
        tvTotalAmount = view.findViewById(R.id.textViewCartTotalAmountDynamic);

        spinnerDeliveryType = view.findViewById(R.id.spinnerCartDeliveryType);
        fillSpinnerDeliveryType();

        initRecycleView(tvTotalCost);
        initSwipeToDeleteAndUndo();
        initSwipeToInfo();

        closeSoftCheckButton.setOnClickListener((view1) -> {
            view1.startAnimation(animAlpha);
            if(adapter.getItemCount() == 0) {
                Toast t = Toast.makeText(this.getActivity().getApplicationContext(), "Корзина пуста!", Toast.LENGTH_LONG);
                t.show();
            }
            else if(spinnerDeliveryType.getSelectedItem().toString().equals(NO)) {
                Toast t = Toast.makeText(this.getActivity().getApplicationContext(), "Выберите способ доставки!", Toast.LENGTH_LONG);
                t.show();
            }
            else {
                SharedPreferences sPref = this.getActivity().getSharedPreferences(PREF, Context.MODE_PRIVATE);
                PreferencesManager prefManager = PreferencesManagerInit.preferencesManager(sPref);
                CommandConfirmCartRecordsDTO cmdConCRecDTO =
                        CommandConfirmCartRecordsDTOInit.commandConfirmCartRecordsDTO(
                                prefManager.load(PreferencesManagerType.USER_IDENT_MANAGER),
                                prefManager.load(PreferencesManagerType.CARD_CODE_MANAGER),
                                adapter.getData());

                CommandManagerAsyncTaskDTO cmdManagerATDTO =
                        CommandManagerAsyncTaskDTOInit.commandManagerAsyncTaskDTO(commandManager,
                                CommandTypeEnum.CONFIRM_SOFT_CHECK_PRODUCTS, cmdConCRecDTO);
                CommandManagerAsyncTask cmdManagerAT = new CommandManagerAsyncTask(managerActivityHandler,
                        queryDialog);
                cmdManagerAT.execute(cmdManagerATDTO);
            }
        });

        checkUnconfirmedRecord();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ManagerActivityUI managerActivityUI = (ManagerActivityUI) this.getActivity();
        managerActivityHandler = (ManagerActivityHandler) this.getActivity();
        if (managerActivityUI != null)
            commandManager = managerActivityUI.commandManager();
    }

    private void fillSpinnerDeliveryType() {
        String[] data = new String[4];
        data[0] = NO;
        data[1] = DOSTAVKA_SO_SKLADOV;
        data[2] = SAMOVYVOZ_SO_SKLADOV;
        data[3] = SAMOVYVOZ_S_TK;

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(),
                R.layout.spinner_cart_delivery_type, data);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerDeliveryType.setAdapter(adapter);
    }

    public void init(List<SoftCheckRecord> cartRecords) {
        this.cartRecords = cartRecords;
    }

    @SuppressLint("SetTextI18n")
    private void initRecycleView(TextView tvTotalCost) {
        adapter = new RecyclerViewAdapter(this.getContext(), cartRecords, tvTotalCost,
                UnitsEnum.CURRENT_PRICE_UNIT.stringValue());
        recyclerView.setAdapter(adapter);
        tvTotalAmount.setText(adapter.getItemCount() + " " + UnitsEnum.CURRENT_AMOUNT_CART_UNIT.stringValue());
    }

    private void initSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(this.getContext()) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                final int position = viewHolder.getAdapterPosition();
                final SoftCheckRecord item = adapter.getData().get(position);
                adapter.removeItem(position);
                tvTotalAmount.setText(adapter.getItemCount() + " " + UnitsEnum.CURRENT_AMOUNT_CART_UNIT.stringValue());

                SnackbarSoftCheckCreator snackbarCr = SnackbarSoftCheckCreatorInit.snackbarSoftCheckCreator(
                        CartFragment.this, coordinatorLayout, "  Товар удален.");
                Snackbar snackbar = snackbarCr.createSnackbar().setAction("Отмена   ", view -> {
                    adapter.restoreItem(item, position);
                    recyclerView.scrollToPosition(position);
                    tvTotalAmount.setText(adapter.getItemCount() + " " + UnitsEnum.CURRENT_AMOUNT_CART_UNIT.stringValue());
                });
                snackbar.show();
            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(recyclerView);
    }

    private void initSwipeToInfo() {
        SwipeToInfoCallback swipeToInfoCallback = new SwipeToInfoCallback(this.getContext()) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                final int position = viewHolder.getAdapterPosition();
                adapter.notifyItemChanged(position);
                InfoProductAlertDialogCreator infoProdADCr =
                        InfoProductAlertDialogCreatorInit.infoProductAlertDialogCreator(getActivity(),
                                (CartRecord)adapter.getItem(position));
                infoProdADCr.createAlertDialog().show();
            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToInfoCallback);
        itemTouchhelper.attachToRecyclerView(recyclerView);
    }

    private void tryToCloseSoftCheck() {
        if(checkUnconfirmedRecord()) {
            DeliveryTypeEnum deliveryType = getDeliveryType();
            SharedPreferences sPref = this.getActivity().getSharedPreferences(PREF, Context.MODE_PRIVATE);
            PreferencesManager prefManager = PreferencesManagerInit.preferencesManager(sPref);
            CommandCloseSoftCheckDTO cmdCloseSCDTO =
                    CommandCloseSoftCheckDTOInit.commandCloseSoftCheckDTO(
                            prefManager.load(PreferencesManagerType.USER_IDENT_MANAGER),
                            prefManager.load(PreferencesManagerType.CARD_CODE_MANAGER),
                            deliveryType);

            CommandManagerAsyncTaskDTO cmdManagerATDTO =
                    CommandManagerAsyncTaskDTOInit.commandManagerAsyncTaskDTO(commandManager,
                            CommandTypeEnum.CLOSE_SOFT_CHECK, cmdCloseSCDTO);
            CommandManagerAsyncTask cmdManagerAT = new CommandManagerAsyncTask(managerActivityHandler,
                    queryDialog);
            cmdManagerAT.execute(cmdManagerATDTO);
        }
    }

    private boolean checkUnconfirmedRecord() {
        int position = 0;
        for(SoftCheckRecord record : cartRecords) {
            CartRecord cartRecord = (CartRecord) record;
            if(!cartRecord.isConfirmed()) {
                adapter.refreshItem(position, cartRecord);
                callDialogUnconfirmed();
                return false;
            }
            position++;
        }
        return true;
    }

    private void callDialogUnconfirmed() {
        UnconfirmedRecordAlertDialogCreator unconRecADCr =
                UnconfirmedRecordAlertDialogCreatorInit.unconfirmedRecordAlertDialogCreator(this.getActivity());
        unconRecADCr.createAlertDialog().show();
    }

    private DeliveryTypeEnum getDeliveryType() {
        String delivery = spinnerDeliveryType.getSelectedItem().toString();
        if(delivery.equals(DOSTAVKA_SO_SKLADOV))
            return DeliveryTypeEnum.DOSTAVKA_SO_SKLADOV;
        else if(delivery.equals(SAMOVYVOZ_SO_SKLADOV))
            return DeliveryTypeEnum.SAMOVYVOZ_SO_SKLADOV;
        else
            return DeliveryTypeEnum.SAMOVYVOZ_S_TK;
    }

    @Override
    public void refreshCartRecords(List<SoftCheckRecord> cartRecords) {
        this.cartRecords = cartRecords;
        adapter.notifyDataSetChanged();
        tryToCloseSoftCheck();
    }

    @Override
    public void onBackPressed() {
        CancelSoftCheckFromCartAlertDialogCreator cancelSCFCADCr =
                CancelSoftCheckFromCartAlertDialogCreatorInit.cancelSoftCheckFromCartAlertDialogCreator(
                        this, managerActivityHandler, commandManager, queryDialog);
        cancelSCFCADCr.createAlertDialog().show();
    }
}
