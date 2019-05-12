
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

import ru.viise.lightsearch.R;
import ru.viise.lightsearch.activity.ManagerActivityUI;
import ru.viise.lightsearch.activity.OnBackPressedListener;
import ru.viise.lightsearch.cmd.manager.CommandManager;
import ru.viise.lightsearch.dialog.alert.ExitToAuthorizationAlertDialogCreator;
import ru.viise.lightsearch.dialog.alert.ExitToAuthorizationAlertDialogCreatorInit;
import ru.viise.lightsearch.find.ISearchFragmentImplFinder;
import ru.viise.lightsearch.find.ISearchFragmentImplFinderInit;


public class ContainerFragment extends Fragment implements OnBackPressedListener, IContainerFragment {

    private static final String TAG = "ContainerFragment";

    private FragmentPageAdapter fragmentPageAdapter;
    private ViewPager viewPager;

    private ManagerActivityUI managerActivityUI;

    private SearchFragment searchFragment;
    private SoftCheckFragment softCheckFragment;

    private CommandManager commandManager;

    private String[] skladArray;
    private String[] TKArray;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_container, container, false);
        viewPager = view.findViewById(R.id.ViewPagerCon);

        setupViewPager(viewPager);

        TabLayout tabLayout = view.findViewById(R.id.TabLayoutCon);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        managerActivityUI = (ManagerActivityUI) this.getActivity();
    }

    private void setupViewPager(ViewPager viewPager) {
        fragmentPageAdapter = new FragmentPageAdapter(getChildFragmentManager());
        searchFragment = new SearchFragment();
        softCheckFragment = new SoftCheckFragment();
        fragmentPageAdapter.addFragment(searchFragment, getString(R.string.fragment_search));
        fragmentPageAdapter.addFragment(softCheckFragment, getString(R.string.fragment_soft_check));

        searchFragment.init(skladArray, TKArray);
        commandManager = managerActivityUI.commandManager();

        viewPager.setAdapter(fragmentPageAdapter);
    }

    @Override
    public void onBackPressed() {
        ExitToAuthorizationAlertDialogCreator exitTAADCr =
                ExitToAuthorizationAlertDialogCreatorInit.exitToAuthorizationAlertDialogCreator(
                        this.getActivity(), commandManager);
        exitTAADCr.createAlertDialog().show();
    }

    @Override
    public void setSearchBarcode(String barcode) {
        ISearchFragmentImplFinder searchFragmentImplFinder =
                ISearchFragmentImplFinderInit.iSearchFragmentImplFinder(this);
        ISearchFragment searchFragment = searchFragmentImplFinder.findImpl();

        if(searchFragment != null)
            searchFragment.setSearchBarcode(barcode);
    }

    public void setupSearchFragment(String[] skladArray, String[] TKArray) {
        this.skladArray = skladArray;
        this.TKArray = TKArray;
    }
}
