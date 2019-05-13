package ru.viise.lightsearch.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import ru.viise.lightsearch.R;


public class SoftCheckFragment extends Fragment {

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

        return view;
    }
}
