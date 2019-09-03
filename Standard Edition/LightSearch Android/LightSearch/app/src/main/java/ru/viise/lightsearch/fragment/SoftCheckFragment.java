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
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ru.viise.lightsearch.R;
import ru.viise.lightsearch.activity.KeyboardHideToolInit;
import ru.viise.lightsearch.activity.ManagerActivityHandler;
import ru.viise.lightsearch.activity.ManagerActivityUI;
import ru.viise.lightsearch.activity.scan.ScannerInit;
import ru.viise.lightsearch.cmd.CommandTypeEnum;
import ru.viise.lightsearch.cmd.manager.CommandManager;
import ru.viise.lightsearch.cmd.manager.task.CommandManagerAsyncTask;
import ru.viise.lightsearch.data.CommandConfirmSoftCheckRecordsDTO;
import ru.viise.lightsearch.data.CommandConfirmSoftCheckRecordsDTOInit;
import ru.viise.lightsearch.data.CommandManagerAsyncTaskDTO;
import ru.viise.lightsearch.data.CommandManagerAsyncTaskDTOInit;
import ru.viise.lightsearch.data.CommandSearchDTO;
import ru.viise.lightsearch.data.ScanType;
import ru.viise.lightsearch.data.SoftCheckRecord;
import ru.viise.lightsearch.data.UnitsEnum;
import ru.viise.lightsearch.data.creator.CommandSearchDTOCreator;
import ru.viise.lightsearch.data.creator.CommandSearchDTOCreatorInit;
import ru.viise.lightsearch.dialog.alert.InfoProductAlertDialogCreator;
import ru.viise.lightsearch.dialog.alert.InfoProductAlertDialogCreatorInit;
import ru.viise.lightsearch.dialog.spots.SpotsDialogCreatorInit;
import ru.viise.lightsearch.fragment.adapter.RecyclerViewAdapter;
import ru.viise.lightsearch.fragment.adapter.SwipeToDeleteCallback;
import ru.viise.lightsearch.fragment.adapter.SwipeToInfoCallback;
import ru.viise.lightsearch.fragment.snackbar.SnackbarSoftCheckCreator;
import ru.viise.lightsearch.fragment.snackbar.SnackbarSoftCheckCreatorInit;
import ru.viise.lightsearch.pref.PreferencesManager;
import ru.viise.lightsearch.pref.PreferencesManagerInit;
import ru.viise.lightsearch.pref.PreferencesManagerType;

public class SoftCheckFragment extends Fragment implements ISoftCheckFragment {

    private final String PREF = "pref";

    private ManagerActivityUI managerActivityUI;
    private ManagerActivityHandler managerActivityHandler;
    private CommandManager commandManager;

    private List<SoftCheckRecord> softCheckRecords;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private CoordinatorLayout coordinatorLayout;
    private Button cartButton;
    private String toCart;

    private AlertDialog queryDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_soft_check, container, false);

        cartButton = view.findViewById(R.id.buttonCart);
        toCart = cartButton.getText().toString();
        queryDialog = SpotsDialogCreatorInit.spotsDialogCreator(this.getActivity(), R.string.spots_dialog_query_exec)
                .create();


        AppCompatImageButton searchButton = view.findViewById(R.id.imageButtonSearch);
        AppCompatImageButton barcodeButton = view.findViewById(R.id.imageButtonBarcode);
        EditText editTextSearch = view.findViewById(R.id.editTextSearchSC);

        Animation animAlpha = AnimationUtils.loadAnimation(this.getActivity(), R.anim.alpha);

        recyclerView = view.findViewById(R.id.recyclerViewSoftCheck);
        coordinatorLayout = view.findViewById(R.id.coordinatorLayoutSoftCheck);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        TextView tvTotalCost = view.findViewById(R.id.textViewSoftCheckTotalCost);

        initRecyclerView(tvTotalCost);
        initSwipeToDeleteAndUndo();
        initSwipeToInfo();

        searchButton.setOnClickListener(view1 -> {
            view1.startAnimation(animAlpha);
            String barcode = editTextSearch.getText().toString();

            if(barcode.length() < 5) {
                Toast t = Toast.makeText(this.getActivity().getApplicationContext(), R.string.toast_barcode_not_enough_symbols, Toast.LENGTH_LONG);
                t.show();
            }
            else
                setSoftCheckBarcode(barcode);

            editTextSearch.clearFocus();
            searchButton.requestFocus();
        });

        barcodeButton.setOnClickListener(view2 -> {
            view2.startAnimation(animAlpha);

            managerActivityUI.setScanType(ScanType.SEARCH_SOFT_CHECK);
            ScannerInit.scanner(this.getActivity()).scan();

            KeyboardHideToolInit.keyboardHideTool(this.getActivity()).hideKeyboard();

            editTextSearch.clearFocus();
            searchButton.requestFocus();
        });

        cartButton.setOnClickListener(view3 -> {
            view3.startAnimation(animAlpha);
            if(adapter.getItemCount() == 0) {
                Toast t = Toast.makeText(this.getActivity().getApplicationContext(), R.string.toast_empty_soft_check, Toast.LENGTH_LONG);
                t.show();
            }
            else {
                SharedPreferences sPref = this.getActivity().getSharedPreferences(PREF, Context.MODE_PRIVATE);
                PreferencesManager prefManager = PreferencesManagerInit.preferencesManager(sPref);
                CommandConfirmSoftCheckRecordsDTO cmdConSCResDTO =
                        CommandConfirmSoftCheckRecordsDTOInit.commandConfirmSoftCheckRecordsDTO(
                                prefManager.load(PreferencesManagerType.USER_IDENT_MANAGER),
                                prefManager.load(PreferencesManagerType.CARD_CODE_MANAGER),
                                adapter.getData());

                CommandManagerAsyncTaskDTO cmdManagerATDTO =
                        CommandManagerAsyncTaskDTOInit.commandManagerAsyncTaskDTO(commandManager,
                                CommandTypeEnum.CONFIRM_SOFT_CHECK_PRODUCTS, cmdConSCResDTO);
                CommandManagerAsyncTask cmdManagerAT = new CommandManagerAsyncTask(managerActivityHandler,
                        queryDialog);
                cmdManagerAT.execute(cmdManagerATDTO);
            }

        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        managerActivityUI = (ManagerActivityUI) this.getActivity();
        managerActivityHandler = (ManagerActivityHandler) this.getActivity();
        commandManager = managerActivityUI.commandManager();
    }

    public void init(List<SoftCheckRecord> softCheckRecords) {
        this.softCheckRecords = softCheckRecords;
    }

    private void initRecyclerView(TextView tvTotalCost) {
        adapter = new RecyclerViewAdapter(this.getContext(), softCheckRecords, tvTotalCost,
                UnitsEnum.CURRENT_PRICE_UNIT.stringValue());
        recyclerView.setAdapter(adapter);
        cartButton.setText(toCart +  " (" + adapter.getItemCount() + ")");
    }

    private void initSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(this.getContext()) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                final int position = viewHolder.getAdapterPosition();
                final SoftCheckRecord item = adapter.getData().get(position);
                adapter.removeItem(position);
                cartButton.setText(toCart +  " (" + adapter.getItemCount() + ")");

                SnackbarSoftCheckCreator snackbarCr = SnackbarSoftCheckCreatorInit.snackbarSoftCheckCreator(
                        SoftCheckFragment.this, coordinatorLayout, SoftCheckFragment.this.getString(R.string.snackbar_prod_deleted));
                Snackbar snackbar = snackbarCr.createSnackbar().setAction(SoftCheckFragment.this.getString(R.string.snackbar_cancel), view -> {
                    adapter.restoreItem(item, position);
                    recyclerView.scrollToPosition(position);
                    cartButton.setText(toCart +  " (" + adapter.getItemCount() + ")");
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
                                adapter.getItem(position));
                infoProdADCr.createAlertDialog().show();
            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToInfoCallback);
        itemTouchhelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void setSoftCheckBarcode(String barcode) {
        KeyboardHideToolInit.keyboardHideTool(this.getActivity()).hideKeyboard();

        CommandSearchDTOCreator cmdSearchDTOCr =
                CommandSearchDTOCreatorInit.commandSearchDTOCreator(barcode);
        CommandSearchDTO cmdSearchDTO = cmdSearchDTOCr.createCommandSearchDTO();
        CommandManagerAsyncTaskDTO cmdManagerATDTO =
                CommandManagerAsyncTaskDTOInit.commandManagerAsyncTaskDTO(commandManager,
                        CommandTypeEnum.SEARCH, cmdSearchDTO);
        CommandManagerAsyncTask cmdManagerAT = new CommandManagerAsyncTask(
                managerActivityHandler, queryDialog);
        cmdManagerAT.execute(cmdManagerATDTO);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void addSoftCheckRecord(SoftCheckRecord record) {
        adapter.addItem(record);
        recyclerView.scrollToPosition(adapter.getItemCount() - 1);
        cartButton.setText(toCart +  " (" + adapter.getItemCount() + ")");
        SnackbarSoftCheckCreator snackbarCr = SnackbarSoftCheckCreatorInit.snackbarSoftCheckCreator(
                SoftCheckFragment.this, coordinatorLayout, SoftCheckFragment.this.getString(R.string.snackbar_prod_added));
        Snackbar snackbar = snackbarCr.createSnackbar();
        snackbar.show();
    }
}
