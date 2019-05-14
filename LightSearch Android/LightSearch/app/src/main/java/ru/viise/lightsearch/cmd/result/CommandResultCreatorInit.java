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

public class CommandResultCreatorInit {

    public static CommandResultCreator commandResultAuthorizationCreator(String rawMessage, String IMEI) {
        return new CommandResultAuthorizationCreatorJSONDefaultImpl(rawMessage, IMEI);
    }

    public static CommandResultCreator commandResultAuthorizationCreator(boolean isDone, String message) {
        return new CommandResultAuthorizationCreatorErrorDefaultImpl(isDone, message);
    }

    public static CommandResultCreator commandResultSearchCreator(String rawMessage, String IMEI,
                      String podrazdelenie) {
        return new CommandResultSearchJSONDefaultImpl(rawMessage, IMEI, podrazdelenie);
    }

    public static CommandResultCreator commandResultSearchCreator(boolean isDone, String message) {
        return new CommandResultSearchCreatorErrorDefaultImpl(isDone, message);
    }

    public static CommandResultCreator commandResultOpenSoftCheckCreator(String rawMessage, String IMEI) {
        return new CommandResultOpenSoftCheckCreatorDefaultImpl(rawMessage, IMEI);
    }

    public static CommandResultCreator commandResultOpenSoftCheckCreator(boolean isDone, String message) {
        return new CommandResultOpenSoftCheckCreatorErrorDefaultImpl(isDone, message);
    }

    public static CommandResultCreator commandResultCancelSoftCheckCreator(String rawMessage, String IMEI) {
        return new CommandResultCancelSoftCheckCreatorDefaultImpl(rawMessage, IMEI);
    }

    public static CommandResultCreator commandResultCancelSoftCheckCreator(boolean isDone, String message) {
        return new CommandResultCancelSoftCheckCreatorErrorDefaultImpl(isDone, message);
    }
}
