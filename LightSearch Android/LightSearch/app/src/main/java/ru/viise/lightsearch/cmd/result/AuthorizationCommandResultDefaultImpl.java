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

public class AuthorizationCommandResultDefaultImpl implements AuthorizationCommandResult {

    private final boolean isDone;
    private final boolean isReconnect;
    private final String message;
    private final String userIdent;
    private final String[] skladList;
    private final String[] TKList;

    public AuthorizationCommandResultDefaultImpl(boolean isDone, boolean isReconnect, String message,
             String userIdent, String[] skladList, String[] TKList) {
        this.isDone = isDone;
        this.isReconnect = isReconnect;
        this.message = message;
        this.userIdent = userIdent;
        this.skladList = skladList;
        this.TKList = TKList;
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

    @Override
    public String userIdent() {
        return userIdent;
    }

    @Override
    public String[] skladList() {
        return skladList;
    }

    @Override
    public String[] TKList() {
        return TKList;
    }
}
