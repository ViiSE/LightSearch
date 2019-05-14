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

package ru.viise.lightsearch.find;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import ru.viise.lightsearch.fragment.ISoftCheckContainerFragment;

public class ISoftCheckContainerFragmentImplFinderDefaultImpl implements ISoftCheckContainerFragmentImplFinder {

    private final Fragment fragment;

    public ISoftCheckContainerFragmentImplFinderDefaultImpl(Fragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public ISoftCheckContainerFragment findImpl() {
        FragmentManager fragmentManager = fragment.getChildFragmentManager();
        for(Fragment fragment :fragmentManager.getFragments()) {
            if(fragment instanceof ISoftCheckContainerFragment)
                return (ISoftCheckContainerFragment) fragment;
        }
        return null;
    }
}
