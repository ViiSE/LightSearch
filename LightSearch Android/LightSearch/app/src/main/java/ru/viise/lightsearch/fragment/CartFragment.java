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

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import dmax.dialog.SpotsDialog;
import ru.viise.lightsearch.R;
import ru.viise.lightsearch.activity.ManagerActivityHandler;
import ru.viise.lightsearch.activity.ManagerActivityUI;
import ru.viise.lightsearch.cmd.manager.CommandManager;
import ru.viise.lightsearch.data.CartRecord;
import ru.viise.lightsearch.data.UnitsEnum;
import ru.viise.lightsearch.data.SoftCheckRecord;
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

public class CartFragment extends Fragment {

    private final String PREF = "pref";

    private ManagerActivityHandler managerActivityHandler;
    private CommandManager commandManager;

    private List<SoftCheckRecord> cartRecords;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private CoordinatorLayout coordinatorLayout;
    private TextView tvTotalAmount;
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

        initRecycleView(tvTotalCost);
        initSwipeToDeleteAndUndo();
        initSwipeToInfo();

        closeSoftCheckButton.setOnClickListener((view1) -> {
            view1.startAnimation(animAlpha);
            if(adapter.getData().isEmpty()) {
                Toast t = Toast.makeText(this.getActivity().getApplicationContext(), "Корзина пуста!", Toast.LENGTH_LONG);
                t.show();
            }
//            else {
//
//            }
        });

        checkUnconfirmedRecord();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ManagerActivityUI managerActivityUI = (ManagerActivityUI) this.getActivity();
        managerActivityHandler = (ManagerActivityHandler) this.getActivity();
        commandManager = managerActivityUI.commandManager();
    }

    public void init(List<SoftCheckRecord> cartRecords) {
        this.cartRecords = cartRecords;
    }

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

    private void checkUnconfirmedRecord() {
        for(SoftCheckRecord record : cartRecords) {
            CartRecord cartRecord = (CartRecord) record;
            if(!cartRecord.isConfirmed()) {
                callDialogUnconfirmed();
                break;
            }
        }
    }

    private void callDialogUnconfirmed() {
        UnconfirmedRecordAlertDialogCreator unconRecADCr =
                UnconfirmedRecordAlertDialogCreatorInit.unconfirmedRecordAlertDialogCreator(this.getActivity());
        unconRecADCr.createAlertDialog().show();
    }

    @Override
    public void onResume() {
        super.onResume();

        if(getView() == null)
            return;

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){

                CancelSoftCheckFromCartAlertDialogCreator cancelSCFCADCr =
                        CancelSoftCheckFromCartAlertDialogCreatorInit.cancelSoftCheckFromCartAlertDialogCreator(
                                this, managerActivityHandler, commandManager, queryDialog);
                cancelSCFCADCr.createAlertDialog().show();
                return true;
            }
            return false;
        });
    }
}
