
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

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Space;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Objects;

import ru.viise.lightsearch.R;
import ru.viise.lightsearch.activity.KeyboardHideToolInit;
import ru.viise.lightsearch.activity.ManagerActivityHandler;
import ru.viise.lightsearch.activity.ManagerActivityUI;
import ru.viise.lightsearch.activity.scan.ScannerInit;
import ru.viise.lightsearch.cmd.CommandTypeEnum;
import ru.viise.lightsearch.cmd.manager.CommandManager;
import ru.viise.lightsearch.cmd.manager.task.CommandManagerAsyncTask;
import ru.viise.lightsearch.data.CommandManagerAsyncTaskDTO;
import ru.viise.lightsearch.data.CommandManagerAsyncTaskDTOInit;
import ru.viise.lightsearch.data.CommandSearchDTO;
import ru.viise.lightsearch.data.ScanType;
import ru.viise.lightsearch.data.SearchFragmentContentEnum;
import ru.viise.lightsearch.data.creator.CommandSearchDTOCreator;
import ru.viise.lightsearch.data.creator.CommandSearchDTOCreatorInit;
import ru.viise.lightsearch.dialog.spots.SpotsDialogCreatorInit;


public class SearchFragment extends Fragment implements View.OnClickListener, ISearchFragment {

    private final String ALL_UI = SearchFragmentContentEnum.ALL_UI.stringValue();

    private final static String MENU_SELECTED = "selected";
    private int selected = 0; //0-sklad, 1-TK, 2 - ALL

    private AlertDialog queryDialog;
    private Spinner skladSpinner;
    private Spinner TKSpinner;
    private EditText searchEditText;
    private RadioButton skladRadioButton;
    private RadioButton TKRadioButton;
    private RadioButton AllRadioButton;

    private String[] skladArray;
    private String[] TKArray;

    private ManagerActivityHandler managerActivityHandler;
    private ManagerActivityUI managerActivityUI;
    private CommandManager commandManager;

    private Animation animAlpha;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null){
            selected = savedInstanceState.getInt(MENU_SELECTED);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        animAlpha = AnimationUtils.loadAnimation(this.getActivity(), R.anim.alpha);

        skladSpinner = view.findViewById(R.id.spinnerSklad);
        TKSpinner = view.findViewById(R.id.spinnerTK);
        searchEditText = view.findViewById(R.id.editTextSearch);
        Button searchButton = view.findViewById(R.id.buttonSearch);
        Button barcodeButton = view.findViewById(R.id.buttonBarcode);
        skladRadioButton = view.findViewById(R.id.radioButtonSklad);
        TKRadioButton = view.findViewById(R.id.radioButtonTK);
        AllRadioButton = view.findViewById(R.id.radioButtonAll);

        Space space1 = view.findViewById(R.id.spaceSearch1);
        Space space4 = view.findViewById(R.id.spaceSearch4);
        Display display = Objects.requireNonNull(this.getActivity()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int height = size.y;

        if(height == 854) {
            space1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0));
            space4.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0));
        }

        searchButton.setOnClickListener(this);
        barcodeButton.setOnClickListener(this);

        switch(selected) {
            case 0:
                skladRadioButton.setChecked(true);
                TKSpinner.setEnabled(false);
                skladSpinner.setEnabled(true);
                break;
            case 1:
                TKRadioButton.setChecked(true);
                TKSpinner.setEnabled(true);
                skladSpinner.setEnabled(false);
                break;
            case 2:
                AllRadioButton.setChecked(true);
                TKSpinner.setEnabled(false);
                skladSpinner.setEnabled(false);
                break;
        }

        fillSpinner(skladSpinner, skladArray);
        fillSpinner(TKSpinner, TKArray);

        skladRadioButton.setOnClickListener(view1 -> {
            skladSpinner.setEnabled(true);
            TKSpinner.setEnabled(false);
            selected = 0;
        });
        TKRadioButton.setOnClickListener(view2 -> {
            skladSpinner.setEnabled(false);
            TKSpinner.setEnabled(true);
            selected = 1;
        });

        AllRadioButton.setOnClickListener(view3 -> {
            skladSpinner.setEnabled(false);
            TKSpinner.setEnabled(false);
            selected = 2;
        });

        queryDialog = SpotsDialogCreatorInit.spotsDialogCreator(this.getActivity(), R.string.spots_dialog_query_exec)
                .create();
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(MENU_SELECTED, selected);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        managerActivityHandler = (ManagerActivityHandler) this.getActivity();
        managerActivityUI = (ManagerActivityUI) this.getActivity();
        if (managerActivityUI != null)
            commandManager = managerActivityUI.commandManager();
    }

    public void init(String[] skladArray, String[] TKArray) {
        this.skladArray = skladArray;
        this.TKArray = TKArray;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.buttonSearch:
                v.startAnimation(animAlpha);
                String barcode = searchEditText.getText().toString();

                if(barcode.length() < 2) {
                    Toast t = Toast.makeText(Objects.requireNonNull(this.getActivity()).getApplicationContext(),
                            "Введите не менее двух символов!", Toast.LENGTH_LONG);
                    t.show();
                } else {
                    KeyboardHideToolInit.keyboardHideTool(this.getActivity()).hideKeyboard();
                    CommandSearchDTOCreator cmdSearchDTOCr =
                            CommandSearchDTOCreatorInit.commandSearchDTOCreator(
                                    barcode, getSubdivision(), getSelectedSklad(), getSelectedTK());
                    CommandSearchDTO cmdSearchDTO = cmdSearchDTOCr.createCommandSearchDTO();
                    CommandManagerAsyncTaskDTO cmdManagerATDTO =
                            CommandManagerAsyncTaskDTOInit.commandManagerAsyncTaskDTO(commandManager,
                                    CommandTypeEnum.SEARCH, cmdSearchDTO);
                    CommandManagerAsyncTask cmdManagerAT = new CommandManagerAsyncTask(
                            managerActivityHandler, queryDialog);
                    cmdManagerAT.execute(cmdManagerATDTO);
                }
                searchEditText.clearFocus();
                v.requestFocus();
                break;
            case R.id.buttonBarcode:
                KeyboardHideToolInit.keyboardHideTool(this.getActivity()).hideKeyboard();

                searchEditText.clearFocus();
                v.requestFocus();

                v.startAnimation(animAlpha);
                managerActivityUI.setScanType(ScanType.SEARCH);
                ScannerInit.scanner(this.getActivity()).scan();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void fillSpinner(Spinner spinner, String[] dataArray) {
        String[] data = new String[dataArray.length + 1];
        data[0] = ALL_UI;
        System.arraycopy(dataArray, 0, data, 1, dataArray.length);
        ArrayAdapter<String> adapter;
        if (this.getParentFragment() != null) {
            adapter = new ArrayAdapter<>(Objects.requireNonNull(this.getParentFragment().getContext()),
                    R.layout.support_simple_spinner_dropdown_item, data);
            adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
        }
    }

    private SearchFragmentContentEnum getSubdivision() {
        if(skladRadioButton.isChecked())
            return SearchFragmentContentEnum.SKLAD;
        else if(TKRadioButton.isChecked())
            return SearchFragmentContentEnum.TK;
        else if(AllRadioButton.isChecked())
            return SearchFragmentContentEnum.ALL;

        return null;
    }

    private String getSelectedSklad() {
        return skladSpinner.getSelectedItem().toString();
    }

    private String getSelectedTK() {
        return TKSpinner.getSelectedItem().toString();
    }

    @Override
    public void setSearchBarcode(String barcode) {
        searchEditText.setText(barcode);
    }
}
