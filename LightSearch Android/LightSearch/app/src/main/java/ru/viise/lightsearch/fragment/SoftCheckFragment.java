package ru.viise.lightsearch.fragment;

import android.app.AlertDialog;
import android.content.Context;
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

import dmax.dialog.SpotsDialog;
import ru.viise.lightsearch.R;
import ru.viise.lightsearch.activity.ManagerActivityHandler;
import ru.viise.lightsearch.activity.ManagerActivityUI;
import ru.viise.lightsearch.activity.scan.ScannerInit;
import ru.viise.lightsearch.cmd.CommandTypeEnum;
import ru.viise.lightsearch.cmd.manager.CommandManager;
import ru.viise.lightsearch.cmd.manager.task.CommandManagerAsyncTask;
import ru.viise.lightsearch.data.SoftCheckRecord;
import ru.viise.lightsearch.data.CommandManagerAsyncTaskDTO;
import ru.viise.lightsearch.data.CommandManagerAsyncTaskDTOInit;
import ru.viise.lightsearch.data.CommandSearchDTO;
import ru.viise.lightsearch.data.ScanType;
import ru.viise.lightsearch.data.creator.CommandSearchDTOCreator;
import ru.viise.lightsearch.data.creator.CommandSearchDTOCreatorInit;
import ru.viise.lightsearch.dialog.alert.InfoProductAlertDialogCreator;
import ru.viise.lightsearch.dialog.alert.InfoProductAlertDialogCreatorInit;
import ru.viise.lightsearch.fragment.adapter.RecyclerViewAdapter;
import ru.viise.lightsearch.fragment.adapter.SwipeToInfoCallback;
import ru.viise.lightsearch.fragment.adapter.SwipeToDeleteCallback;
import ru.viise.lightsearch.fragment.snackbar.SnackbarSoftCheckCreator;
import ru.viise.lightsearch.fragment.snackbar.SnackbarSoftCheckCreatorInit;

public class SoftCheckFragment extends Fragment implements ISoftCheckFragment {

    private ManagerActivityUI managerActivityUI;
    private ManagerActivityHandler managerActivityHandler;
    private CommandManager commandManager;

    private List<SoftCheckRecord> softCheckRecords;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private CoordinatorLayout coordinatorLayout;

    private AlertDialog queryDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_soft_check, container, false);

        AppCompatImageButton searchButton = view.findViewById(R.id.imageButtonSearch);
        AppCompatImageButton barcodeButton = view.findViewById(R.id.imageButtonBarcode);
        Button cartButton = view.findViewById(R.id.buttonCart);
        EditText editTextSearch = view.findViewById(R.id.editTextSearchSC);

        queryDialog = new SpotsDialog.Builder().setContext(this.getActivity()).setMessage("Выполнение").setCancelable(false).build();

        Animation animAlpha = AnimationUtils.loadAnimation(this.getActivity(), R.anim.alpha);

        searchButton.setOnClickListener(view1 -> {
            view1.startAnimation(animAlpha);
            String barcode = editTextSearch.getText().toString();

            if(barcode.length() < 5) {
                Toast t = Toast.makeText(this.getActivity().getApplicationContext(), "Введите не менее пяти символов!", Toast.LENGTH_LONG);
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

            editTextSearch.clearFocus();
            searchButton.requestFocus();
        });

        cartButton.setOnClickListener(view3 -> {
            Toast t = Toast.makeText(this.getActivity().getApplicationContext(), "cart", Toast.LENGTH_LONG);
            t.show();
        });

        recyclerView = view.findViewById(R.id.recyclerViewSoftCheck);
        coordinatorLayout = view.findViewById(R.id.coordinatorLayoutSoftCheck);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this.getContext());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(mLayoutManager);

        TextView tvTotalCost = view.findViewById(R.id.textViewSoftCheckTotalCost);

        initRecyclerView(tvTotalCost);
        initSwipeToDeleteAndUndo();
        initSwipeToInfo();

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
        adapter = new RecyclerViewAdapter(softCheckRecords, tvTotalCost, "руб.");
        recyclerView.setAdapter(adapter);
    }

    private void initSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(this.getContext()) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                final int position = viewHolder.getAdapterPosition();
                final SoftCheckRecord item = adapter.getData().get(position);
                adapter.removeItem(position);

                SnackbarSoftCheckCreator snackbarCr = SnackbarSoftCheckCreatorInit.snackbarSoftCheckCreator(
                        SoftCheckFragment.this, coordinatorLayout, "  Товар удален.");
                Snackbar snackbar = snackbarCr.createSnackbar().setAction("Отмена   ", view -> {
                    adapter.restoreItem(item, position);
                    recyclerView.scrollToPosition(position);
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

    @Override
    public void addSoftCheckRecord(SoftCheckRecord record) {
        adapter.addItem(record);
        SnackbarSoftCheckCreator snackbarCr = SnackbarSoftCheckCreatorInit.snackbarSoftCheckCreator(
                SoftCheckFragment.this, coordinatorLayout, "  Товар добавлен.");
        Snackbar snackbar = snackbarCr.createSnackbar();
        snackbar.show();
    }
}
