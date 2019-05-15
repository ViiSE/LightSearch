
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
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dmax.dialog.SpotsDialog;
import ru.viise.lightsearch.R;
import ru.viise.lightsearch.activity.ManagerActivityHandler;
import ru.viise.lightsearch.activity.ManagerActivityUI;
import ru.viise.lightsearch.activity.OnBackPressedListener;
import ru.viise.lightsearch.activity.OnBackPressedListenerType;
import ru.viise.lightsearch.cmd.manager.CommandManager;
import ru.viise.lightsearch.dialog.alert.CancelSoftCheckAlertDialogCreator;
import ru.viise.lightsearch.dialog.alert.CancelSoftCheckAlertDialogCreatorInit;
import ru.viise.lightsearch.dialog.alert.ExitToAuthorizationAlertDialogCreator;
import ru.viise.lightsearch.dialog.alert.ExitToAuthorizationAlertDialogCreatorInit;
import ru.viise.lightsearch.find.IOpenSoftCheckFragmentImplFinder;
import ru.viise.lightsearch.find.IOpenSoftCheckFragmentImplFinderInit;
import ru.viise.lightsearch.find.ISearchFragmentImplFinder;
import ru.viise.lightsearch.find.ISearchFragmentImplFinderInit;
import ru.viise.lightsearch.find.ISoftCheckContainerFragmentImplFinder;
import ru.viise.lightsearch.find.ISoftCheckContainerFragmentImplFinderInit;


public class ContainerFragment extends Fragment implements OnBackPressedListener, IContainerFragment {

    private static final String TAG = "ContainerFragment";

    private String[] skladArray;
    private String[] TKArray;

    private AlertDialog queryDialog;

    private CommandManager commandManager;
    private ManagerActivityHandler managerActivityHandler;
    private OnBackPressedListenerType onBackPressedListenerType;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_container, container, false);
        ViewPager viewPager = view.findViewById(R.id.ViewPagerCon);

        setupViewPager(viewPager);

        TabLayout tabLayout = view.findViewById(R.id.TabLayoutCon);
        tabLayout.setupWithViewPager(viewPager);

        queryDialog = new SpotsDialog.Builder().setContext(this.getActivity()).setMessage("Выполнение").setCancelable(false).build();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        managerActivityHandler = (ManagerActivityHandler) this.getActivity();
        ManagerActivityUI managerActivityUI = (ManagerActivityUI) this.getActivity();
        commandManager = managerActivityUI.commandManager();
    }

    private void setupViewPager(ViewPager viewPager) {
        FragmentPageAdapter fragmentPageAdapter = new FragmentPageAdapter(getChildFragmentManager());
        SearchFragment searchFragment = new SearchFragment();
        SoftCheckContainerFragment rootFragment = new SoftCheckContainerFragment();
        fragmentPageAdapter.addFragment(searchFragment, getString(R.string.fragment_search));
        fragmentPageAdapter.addFragment(rootFragment, getString(R.string.fragment_soft_check));

        searchFragment.init(skladArray, TKArray);

        viewPager.setAdapter(fragmentPageAdapter);
        onBackPressedListenerType = OnBackPressedListenerType.CONTAINER_FRAGMENT;
    }

    private ISoftCheckContainerFragment getSoftCheckContainerFragment() {
        ISoftCheckContainerFragmentImplFinder softCheckContainerFragmentImplFinder =
                ISoftCheckContainerFragmentImplFinderInit.softISoftCheckContainerFragmentImplFinder(this);
        return softCheckContainerFragmentImplFinder.findImpl();
    }

    @Override
    public void onBackPressed() {
        if(onBackPressedListenerType == OnBackPressedListenerType.CONTAINER_FRAGMENT) {
            ExitToAuthorizationAlertDialogCreator exitTAADCr =
                    ExitToAuthorizationAlertDialogCreatorInit.exitToAuthorizationAlertDialogCreator(
                            this.getActivity(), commandManager);
            exitTAADCr.createAlertDialog().show();
        }
        else if(onBackPressedListenerType == OnBackPressedListenerType.SOFT_CHECK_FRAGMENT) {
            CancelSoftCheckAlertDialogCreator cancelSCADCr =
                    CancelSoftCheckAlertDialogCreatorInit.cancelSoftCheckAlertDialogCreator(
                            this, managerActivityHandler, queryDialog, commandManager);
            cancelSCADCr.createAlertDialog().show();
        }
    }

    @Override
    public void setSearchBarcode(String barcode) {
        ISearchFragmentImplFinder searchFragmentImplFinder =
                ISearchFragmentImplFinderInit.searchFragmentImplFinder(this);
        ISearchFragment searchFragment = searchFragmentImplFinder.findImpl();

        if(searchFragment != null)
            searchFragment.setSearchBarcode(barcode);
    }

    @Override
    public void setCardCode(String cardCode) {
        IOpenSoftCheckFragmentImplFinder openSoftCheckFragmentImplFinder =
        IOpenSoftCheckFragmentImplFinderInit.openSoftCheckFragmentImplFinder(this);
        IOpenSoftCheckFragment openSoftCheckFragment = openSoftCheckFragmentImplFinder.findImpl();
        if(openSoftCheckFragment != null)
            openSoftCheckFragment.setCardCode(cardCode);
    }

    @Override
    public void switchToSoftCheckFragment() {
        ISoftCheckContainerFragment softCheckContainerFragment = getSoftCheckContainerFragment();
        if(softCheckContainerFragment != null)
            softCheckContainerFragment.switchToSoftCheckFragment();
        onBackPressedListenerType = OnBackPressedListenerType.SOFT_CHECK_FRAGMENT;
    }

    @Override
    public void switchToOpenSoftCheckFragment() {
        ISoftCheckContainerFragment softCheckContainerFragment = getSoftCheckContainerFragment();
        if(softCheckContainerFragment != null)
            softCheckContainerFragment.switchToOpenSoftCheckFragment();
        onBackPressedListenerType = OnBackPressedListenerType.CONTAINER_FRAGMENT;
    }

    public void setupSearchFragment(String[] skladArray, String[] TKArray) {
        this.skladArray = skladArray;
        this.TKArray = TKArray;
    }
}
