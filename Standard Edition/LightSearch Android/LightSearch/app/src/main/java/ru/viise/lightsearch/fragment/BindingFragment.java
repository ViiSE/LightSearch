
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

import android.animation.Animator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import ru.viise.lightsearch.data.CommandCheckBindDTO;
import ru.viise.lightsearch.data.CommandManagerAsyncTaskDTO;
import ru.viise.lightsearch.data.CommandManagerAsyncTaskDTOInit;
import ru.viise.lightsearch.data.ScanType;
import ru.viise.lightsearch.data.creator.CommandCheckBindDTOCreator;
import ru.viise.lightsearch.data.creator.CommandCheckBindDTOCreatorInit;
import ru.viise.lightsearch.dialog.spots.SpotsDialogCreatorInit;
import ru.viise.lightsearch.pref.PreferencesManager;
import ru.viise.lightsearch.pref.PreferencesManagerInit;
import ru.viise.lightsearch.pref.PreferencesManagerType;


public class BindingFragment extends Fragment implements IBindingFragment {

    private final static String MODE = "mode";
    private final static String FACTORY_BARCODE = "factoryBarcode";

    private String factoryBarcode = "";
    private int selected = 0; // 0 - CheckBindingMode, 1 - BindingMode, 2 - BindingDone

    private AlertDialog queryDialog;
    private EditText searchEditText;
    private TextView textViewFactoryBarcode;

    private ManagerActivityHandler managerActivityHandler;
    private ManagerActivityUI managerActivityUI;
    private CommandManager commandManager;

    private Animation animAlpha;

    private Button bindButton;

    private LinearLayout linearLayoutBindFactoryBarcode;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null){
            selected = savedInstanceState.getInt(MODE);
            factoryBarcode = savedInstanceState.getString(FACTORY_BARCODE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_binding, container, false);

        animAlpha = AnimationUtils.loadAnimation(this.getActivity(), R.anim.alpha);


        searchEditText = view.findViewById(R.id.editTextSearchBinding);
        textViewFactoryBarcode = view.findViewById(R.id.textViewBindBarcode);
        bindButton = view.findViewById(R.id.buttonBind);
        linearLayoutBindFactoryBarcode = view.findViewById(R.id.linearLayoutBindFactoryBarcode);
        Button barcodeButton = view.findViewById(R.id.buttonBarcodeBinding);

        if(selected == 0) {
            bindButton.setText(R.string.button_bind_check);

            linearLayoutBindFactoryBarcode.setVisibility(View.INVISIBLE);
            searchEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        } else if(selected == 1) {
            bindButton.setText(R.string.button_bind);

            linearLayoutBindFactoryBarcode.setVisibility(View.VISIBLE);
            textViewFactoryBarcode.setText(factoryBarcode);
            searchEditText.setInputType(InputType.TYPE_CLASS_TEXT);
        } else if(selected == 2) {
            switchToCheckBind();
        }

        queryDialog = SpotsDialogCreatorInit.spotsDialogCreator(this.getActivity(), R.string.spots_dialog_query_exec)
                .create();

        bindButton.setOnClickListener(view1 -> {
            view1.startAnimation(animAlpha);
            String input = searchEditText.getText().toString();

            if(input.length() < 2) {
                Toast t = Toast.makeText(Objects.requireNonNull(this.getActivity()).getApplicationContext(),
                        "Введите не менее двух символов!", Toast.LENGTH_LONG);
                t.show();
            } else {
                if(selected == 0) {
                    factoryBarcode = input;
                    textViewFactoryBarcode.setText("");
                }

                KeyboardHideToolInit.keyboardHideTool(this.getActivity()).hideKeyboard();
                CommandCheckBindDTOCreator cmdCheckBindingDTOCr =
                        CommandCheckBindDTOCreatorInit
                                .commandCheckBindDTOCreator(input, selected, factoryBarcode);
                CommandCheckBindDTO cmdCheckBindingDTO = cmdCheckBindingDTOCr.create();
                CommandManagerAsyncTaskDTO cmdManagerATDTO = CommandManagerAsyncTaskDTOInit
                        .commandManagerAsyncTaskDTO(
                                commandManager,
                                CommandTypeEnum.BIND_CHECK,
                                cmdCheckBindingDTO);
                CommandManagerAsyncTask cmdManagerAT = new CommandManagerAsyncTask(
                        managerActivityHandler, queryDialog);

                cmdManagerAT.execute(cmdManagerATDTO);
            }
            searchEditText.clearFocus();
            view1.requestFocus();
        });

        barcodeButton.setOnClickListener(view2 -> {
            KeyboardHideToolInit.keyboardHideTool(this.getActivity()).hideKeyboard();

            searchEditText.clearFocus();
            view2.requestFocus();

            view2.startAnimation(animAlpha);
            managerActivityUI.setScanType(ScanType.SEARCH_BIND);
            ScannerInit.scanner(this.getActivity()).scan();
        });

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(MODE, selected);
        outState.putString(FACTORY_BARCODE, factoryBarcode);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        managerActivityHandler = (ManagerActivityHandler) this.getActivity();
        managerActivityUI = (ManagerActivityUI) this.getActivity();
        if (managerActivityUI != null)
            commandManager = managerActivityUI.commandManager();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void setSearchBarcode(String barcode) {
        searchEditText.setText(barcode);
    }

    @Override
    public void switchToBind() {
        // TODO: 31.01.20 CIRCULAR ANIMATION (USE FOR TASK)
        // ==================== ANIMATION  START ==================== //
//        int x = linearLayoutBindFactoryBarcode.getRight();
//        int y = linearLayoutBindFactoryBarcode.getBottom();
//
//        int startRadius = 0;
//        int endRadius = (int) Math.hypot(linearLayoutBindFactoryBarcode.getWidth(), linearLayoutBindFactoryBarcode.getHeight());

//            Animation out = AnimationUtils.loadAnimation(this.getActivity(), android.R.anim.fade_out);
//        Animator animator = ViewAnimationUtils.createCircularReveal(linearLayoutBindFactoryBarcode, x, y, startRadius, endRadius);
//        animator.start();
        // ==================== ANIMATION  END ==================== //

        selected = 1;
        bindButton.setText(R.string.button_bind);
        Animation in = AnimationUtils.loadAnimation(this.getActivity(), android.R.anim.fade_in);
        linearLayoutBindFactoryBarcode.startAnimation(in);

        linearLayoutBindFactoryBarcode.setVisibility(View.VISIBLE);

        textViewFactoryBarcode.setText(factoryBarcode);
        searchEditText.setText("");
        searchEditText.setInputType(InputType.TYPE_CLASS_TEXT);
    }

    @Override
    public void switchToCheckBind() {
        // TODO: 31.01.20 CIRCULAR ANIMATION (USE FOR TASK)
        // ==================== ANIMATION  START ==================== //
//        int x = linearLayoutBindFactoryBarcode.getRight();
//        int y = linearLayoutBindFactoryBarcode.getBottom();
//
//        int startRadius = Math.max(linearLayoutBindFactoryBarcode.getWidth(), linearLayoutBindFactoryBarcode.getHeight());
//        int endRadius = 0;
//        Animator animator = ViewAnimationUtils.createCircularReveal(linearLayoutBindFactoryBarcode, x, y, startRadius, endRadius);
//        animator.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animator) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animator) {
//                linearLayoutBindFactoryBarcode.setVisibility(View.INVISIBLE);
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animator) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animator) {
//
//            }
//        });

//        animator.start();
        // ==================== ANIMATION  END ==================== //

        selected = 0;
        bindButton.setText(R.string.button_bind_check);

        Animation out = AnimationUtils.loadAnimation(this.getActivity(), android.R.anim.fade_out);
        linearLayoutBindFactoryBarcode.startAnimation(out);

        linearLayoutBindFactoryBarcode.setVisibility(View.INVISIBLE);

        factoryBarcode = "";
        searchEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        searchEditText.setText("");
    }
}
