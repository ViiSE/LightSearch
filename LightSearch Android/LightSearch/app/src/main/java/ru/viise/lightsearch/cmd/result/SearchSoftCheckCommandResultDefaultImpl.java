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

package ru.viise.lightsearch.cmd.result;

import ru.viise.lightsearch.data.SoftCheckRecord;

public class SearchSoftCheckCommandResultDefaultImpl implements SearchSoftCheckCommandResult {

    private final boolean isDone;
    private final boolean isReconnect;
    private final String message;
    private final SoftCheckRecord record;

    public SearchSoftCheckCommandResultDefaultImpl(boolean isDone, boolean isReconnect, String message,
               SoftCheckRecord record) {
        this.isDone = isDone;
        this.isReconnect = isReconnect;
        this.message = message;
        this.record = record;
    }

    @Override
    public SoftCheckRecord record() {
        return record;
    }

    @Override
    public boolean isDone() {
        return isDone;
    }

    @Override
    public boolean isReconnect() {
        return isReconnect;
    }

    @Override
    public String message() {
        return message;
    }
}
