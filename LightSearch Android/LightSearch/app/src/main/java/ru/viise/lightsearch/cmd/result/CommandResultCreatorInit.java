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

import java.util.List;

import ru.viise.lightsearch.data.SoftCheckRecord;

public class CommandResultCreatorInit {

    public static CommandResultCreator commandResultAuthorizationCreator(String rawMessage, String IMEI) {
        return new CommandResultAuthorizationCreatorJSONDefaultImpl(rawMessage, IMEI);
    }

    public static CommandResultCreator commandResultAuthorizationCreator(boolean isDone, String message) {
        return new CommandResultAuthorizationCreatorErrorDefaultImpl(isDone, message);
    }

    public static CommandResultCreator commandResultSearchCreator(String rawMessage, String IMEI,
                      String subdivision) {
        return new CommandResultSearchCreatorJSONDefaultImpl(rawMessage, IMEI, subdivision);
    }

    public static CommandResultCreator commandResultSearchCreator(boolean isDone, String message) {
        return new CommandResultSearchCreatorErrorDefaultImpl(isDone, message);
    }

    public static CommandResultCreator commandResultSearchSoftCheckCreator(String rawMessage, String IMEI) {
        return new CommandResultSearchSoftCheckCreatorJSONDefaultImpl(rawMessage, IMEI);
    }

    public static CommandResultCreator commandResultSearchSoftCheckCreator(boolean isDone, String message) {
        return new CommandResultSearchSoftCheckCreatorErrorDefaultImpl(isDone, message);
    }

    public static CommandResultCreator commandResultOpenSoftCheckCreator(String rawMessage, String IMEI) {
        return new CommandResultOpenSoftCheckCreatorJSONDefaultImpl(rawMessage, IMEI);
    }

    public static CommandResultCreator commandResultOpenSoftCheckCreator(boolean isDone, String message) {
        return new CommandResultOpenSoftCheckCreatorErrorDefaultImpl(isDone, message);
    }

    public static CommandResultCreator commandResultCancelSoftCheckCreator(String rawMessage, String IMEI) {
        return new CommandResultCancelSoftCheckCreatorJSONDefaultImpl(rawMessage, IMEI);
    }

    public static CommandResultCreator commandResultCancelSoftCheckCreator(boolean isDone, String message) {
        return new CommandResultCancelSoftCheckCreatorErrorDefaultImpl(isDone, message);
    }

    public static CommandResultCreator commandResultConfirmSoftCheckProductsCreator(String rawMessage,
                        String IMEI, List<SoftCheckRecord> softCheckRecords) {
        return new CommandResultConfirmSoftCheckProductsCreatorJSONDefaultImpl(rawMessage, IMEI,
                softCheckRecords);
    }

    public static CommandResultCreator commandResultConfirmSoftCheckProductsCreator(boolean isDone, String message) {
        return new CommandResultConfirmSoftCheckProductsCreatorErrorDefaultImpl(isDone, message);
    }

    public static CommandResultCreator commandResultConfirmCartProductsCreator(String rawMessage,
                        String IMEI, List<SoftCheckRecord> softCheckRecords) {
        return new CommandResultConfirmCartProductsCreatorJSONDefaultImpl(rawMessage, IMEI,
                softCheckRecords);
    }

    public static CommandResultCreator commandResultConfirmCartProductsCreator(boolean isDone, String message) {
        return new CommandResultConfirmCartProductsCreatorErrorDefaultImpl(isDone, message);
    }
}
