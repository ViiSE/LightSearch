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

package ru.viise.lightsearch.fragment.transaction;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import java.util.List;

import ru.viise.lightsearch.R;
import ru.viise.lightsearch.data.SearchRecordDTO;
import ru.viise.lightsearch.data.SoftCheckRecord;
import ru.viise.lightsearch.fragment.AuthorizationFragment;
import ru.viise.lightsearch.fragment.CartFragment;
import ru.viise.lightsearch.fragment.ContainerFragment;
import ru.viise.lightsearch.fragment.ResultSearchFragment;
import ru.viise.lightsearch.fragment.StackFragmentTitle;

public class FragmentTransactionManagerDefaultImpl implements FragmentTransactionManager {

    private final FragmentActivity activity;

    public FragmentTransactionManagerDefaultImpl(FragmentActivity activity) {
        this.activity = activity;
    }

    @Override
    public void doAuthorizationFragmentTransaction() {
        AuthorizationFragment authorizationFragment = new AuthorizationFragment();
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.FrameLayoutManager, authorizationFragment, activity.getString(R.string.fragment_authorization));
        transaction.commit();
        activity.setTitle(activity.getString(R.string.fragment_authorization));
    }

    @Override
    public void doContainerFragmentTransaction(String[] skladArray, String[] TKArray) {
        ContainerFragment containerFragment = new ContainerFragment();
        containerFragment.setupSearchFragment(skladArray, TKArray);
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        transaction.replace(R.id.FrameLayoutManager, containerFragment, activity.getString(R.string.fragment_container));
        transaction.addToBackStack(activity.getString(R.string.fragment_container));
        transaction.commit();
        activity.setTitle(activity.getString(R.string.fragment_container));
        StackFragmentTitle.push(activity.getString(R.string.fragment_authorization));
    }

    @Override
    public void doResultSearchFragmentTransaction(String title, List<SearchRecordDTO> searchRecords) {
        ResultSearchFragment resultSearchFragment = new ResultSearchFragment();
        resultSearchFragment.init(searchRecords);
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_down, R.anim.exit_to_up, R.anim.enter_from_up, R.anim.exit_to_down);
        transaction.replace(R.id.FrameLayoutManager, resultSearchFragment, activity.getString(R.string.fragment_result_search));
        transaction.addToBackStack(activity.getString(R.string.fragment_result_search));
        transaction.commit();
        activity.setTitle(title);
        StackFragmentTitle.push(activity.getString(R.string.fragment_container));
    }

    @Override
    public void doCartFragmentTransaction(List<SoftCheckRecord> cartRecords) {
        CartFragment cartFragment = new CartFragment();
        cartFragment.init(cartRecords);
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        transaction.replace(R.id.FrameLayoutManager, cartFragment, activity.getString(R.string.fragment_cart));
        transaction.addToBackStack(activity.getString(R.string.fragment_cart));
        transaction.commit();
        activity.setTitle(activity.getString(R.string.fragment_cart));
        StackFragmentTitle.push(activity.getString(R.string.fragment_container));
    }

    @Override
    public void doContainerFragmentTransactionFromCart() {
        activity.getSupportFragmentManager().popBackStack();
        activity.setTitle(activity.getString(R.string.fragment_container));
        StackFragmentTitle.pop();
    }
}
