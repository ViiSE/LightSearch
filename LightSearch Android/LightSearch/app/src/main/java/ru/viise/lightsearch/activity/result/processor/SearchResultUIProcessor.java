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

package ru.viise.lightsearch.activity.result.processor;

import java.util.function.Function;

import ru.viise.lightsearch.activity.ManagerActivity;
import ru.viise.lightsearch.cmd.result.CommandResult;
import ru.viise.lightsearch.cmd.result.SearchCommandResult;
import ru.viise.lightsearch.fragment.util.SearchResultTitleCreator;
import ru.viise.lightsearch.fragment.util.SearchResultTitleCreatorInit;

public class SearchResultUIProcessor implements Function<CommandResult, Void> {

    private final ManagerActivity activity;

    public SearchResultUIProcessor(ManagerActivity activity) {
        this.activity = activity;
    }

    @Override
    public Void apply(CommandResult commandResult) {
        SearchCommandResult searchCmdRes = (SearchCommandResult) commandResult;
        if(searchCmdRes.isDone()) {
            if (searchCmdRes.records().size() != 0) {
                SearchResultTitleCreator searchResTitleCr =
                        SearchResultTitleCreatorInit.searchResultTitleCreator(searchCmdRes.subdivision(),
                                searchCmdRes.records().get(0).id());
                String title = searchResTitleCr.createTitle();
                if (searchCmdRes.records().size() == 1)
                    activity.callDialogOneResult(searchCmdRes.records().get(0));
                else
                    activity.doResultSearchFragmentTransaction(title, searchCmdRes.records());
            } else
                activity.callDialogNoResult();
        }
        else
            activity.callDialogError(searchCmdRes.message());

        return null;
    }
}