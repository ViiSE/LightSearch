package ru.viise.lightsearch.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ru.viise.lightsearch.R;
import ru.viise.lightsearch.data.CartRecord;
import ru.viise.lightsearch.data.CartRecordInit;
import ru.viise.lightsearch.fragment.adapter.RecyclerViewAdapter;
import ru.viise.lightsearch.fragment.adapter.SwipeToChangeCallback;
import ru.viise.lightsearch.fragment.adapter.SwipeToDeleteCallback;


public class SoftCheckFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    List<CartRecord> stringArrayList = new ArrayList<>();
    CoordinatorLayout coordinatorLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_soft_check, container, false);

        AppCompatImageButton searchButton = view.findViewById(R.id.imageButtonSearch);
        AppCompatImageButton barcodeButton = view.findViewById(R.id.imageButtonBarcode);
        Button cartButton = view.findViewById(R.id.buttonCart);

        searchButton.setOnClickListener(view1 -> {
            Toast t = Toast.makeText(this.getActivity().getApplicationContext(), "search", Toast.LENGTH_LONG);
            t.show();
        });

        barcodeButton.setOnClickListener(view2 -> {
            Toast t = Toast.makeText(this.getActivity().getApplicationContext(), "barcode", Toast.LENGTH_LONG);
            t.show();
        });

        cartButton.setOnClickListener(view3 -> {
            Toast t = Toast.makeText(this.getActivity().getApplicationContext(), "cart", Toast.LENGTH_LONG);
            t.show();
        });

        recyclerView = view.findViewById(R.id.recyclerViewSoftCheck);
        coordinatorLayout = view.findViewById(R.id.coordinatorLayoutSoftCheck);

        TextView tvTotalCost = view.findViewById(R.id.textViewSoftCheckTotalCost);

        initRecyclerView(tvTotalCost);
        initSwipeToDeleteAndUndo();
        initSwipeToChange();

        return view;
    }

    private void initRecyclerView(TextView tvTotalCost) {
        stringArrayList.add(CartRecordInit.cartRecord(
                "Пена монтажная МОМЕНТ быт. 500мл",
                "2200000738592",
                276,
                1000,
                "шт."));
        stringArrayList.add(CartRecordInit.cartRecord(
                "Герметик силиконовый ГЕРМЕНТ Санитарный белый 280мл",
                "609878",
                116,
                2000,
                "шт."));
        stringArrayList.add(CartRecordInit.cartRecord(
                "Кран шаровый ш/ш баб. 1/2\" л/н",
                "725646",
                250,
                15,
                "шт."));
        stringArrayList.add(CartRecordInit.cartRecord(
                "Товар 1",
                "1307469",
                250,
                150,
                "м^2."));
        stringArrayList.add(CartRecordInit.cartRecord(
                "Товар 2",
                "1307469",
                13654,
                2000,
                "шт."));
        stringArrayList.add(CartRecordInit.cartRecord(
                "Товар 3",
                "1307469",
                136,
                2,
                "шт."));
        stringArrayList.add(CartRecordInit.cartRecord(
                "Товар 4",
                "1307469",
                654,
                20,
                "шт."));
        stringArrayList.add(CartRecordInit.cartRecord(
                "Товар 5",
                "1307469",
                32,
                70,
                "шт."));
        stringArrayList.add(CartRecordInit.cartRecord(
                "Товар 6",
                "1307469",
                74,
                41,
                "шт."));
        stringArrayList.add(CartRecordInit.cartRecord(
                "Товар 7",
                "1307469",
                47568,
                1000,
                "шт."));

        adapter = new RecyclerViewAdapter(stringArrayList, tvTotalCost, "руб.");
        recyclerView.setAdapter(adapter);
    }

    private void initSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(this.getContext()) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                final int position = viewHolder.getAdapterPosition();
                final CartRecord item = adapter.getData().get(position);
                adapter.removeItem(position);

                Snackbar snackbar = Snackbar.make(
                        coordinatorLayout, "  Товар удален.", Snackbar.LENGTH_LONG);
                snackbar.setAction("Отмена   ", view -> {
                    adapter.restoreItem(item, position);
                    recyclerView.scrollToPosition(position);
                });
                snackbar.setActionTextColor(ContextCompat.getColor(getContext(), R.color.colorUndo));
                snackbar.show();
            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(recyclerView);
    }

    private void initSwipeToChange() {
        SwipeToChangeCallback swipeToChangeCallback = new SwipeToChangeCallback(this.getContext()) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                final int position = viewHolder.getAdapterPosition();
                adapter.notifyItemChanged(position);
                Toast t = Toast.makeText(getContext().getApplicationContext(), "change", Toast.LENGTH_LONG);
                t.show();
            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToChangeCallback);
        itemTouchhelper.attachToRecyclerView(recyclerView);
    }
}
